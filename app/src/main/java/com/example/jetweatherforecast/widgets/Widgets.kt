package com.example.jetweatherforecast.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.jetweatherforecast.R
import com.example.jetweatherforecast.model.Weather
import com.example.jetweatherforecast.model.WeatherItem
import com.example.jetweatherforecast.utils.formatDateDay
import com.example.jetweatherforecast.utils.formatDateTime
import com.example.jetweatherforecast.utils.kelvinToCelsius

@Composable
fun HumidityWindPressureRow(weather: WeatherItem) {
    Row(modifier = Modifier
        .padding(12.dp)
        .fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(4.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.humidity),
                contentDescription = "Humidity Icon",
                modifier = Modifier
                    .padding(20.dp)
                    .size(30.dp)
            )
            Text(
                text = "${weather.humidity}%",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Row(modifier = Modifier.padding(4.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.pressure),
                contentDescription = "Pressure Icon",
                modifier = Modifier
                    .padding(20.dp)
                    .size(30.dp)
            )
            Text(
                text = "${weather.pressure} psi",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Row(modifier = Modifier.padding(4.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.wind),
                contentDescription = "wind Icon",
                modifier = Modifier
                    .padding(20.dp)
                    .size(20.dp)
            )
            Text(
                text = "${weather.gust} mph",
                style = MaterialTheme.typography.bodyMedium
            )
        }

    }
}

@Composable
fun SunsetSunriseRow(weather: WeatherItem) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(end = 20.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.sunrise),
                contentDescription = "Sunrise Icon",
                modifier = Modifier
                    .padding(20.dp)
                    .size(20.dp)
            )
            Text(text = formatDateTime(weather.sunrise),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.sunset),
                contentDescription = "Sunset Icon",
                modifier = Modifier
                    .padding(20.dp)
                    .size(20.dp)
            )
            Text(text = formatDateTime(weather.sunset),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun SevenDayForecast(data: Weather) {
    Column(
        modifier = Modifier
            .verticalScroll(
                state = rememberScrollState(),
                reverseScrolling = true
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "This Week",
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center
        )
        data.list.forEachIndexed { index, weatherItem ->
            DayForecast(data = data, day = index)
        }
    }
}

@Composable
fun DayForecast(data: Weather, day: Int) {
    val imageUrl = "https://openweathermap.org/img/wn/${data.list[day].weather[0].icon}.png"
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .fillMaxWidth()
            .height(70.dp)
            .clip(
                RoundedCornerShape(
                    topStartPercent = 100, topEndPercent = 10,
                    bottomEndPercent = 100, bottomStartPercent = 100
                )
            )
            .background(
                color = Color.White,
                shape = RoundedCornerShape(0)
            )

    ) {
        Text(text = formatDateDay(data.list[day].sunset))
        AsyncImage(
            model = imageUrl,
            contentDescription = "Weather of ${formatDateDay(data.list[day].sunset)}",
            modifier = Modifier.size(width = 60.dp, height = 80.dp)
        )
        Surface(
            modifier = Modifier.padding(0.dp),
            shape = CircleShape,
            color = Color(0xFFFFC400)

        ) {
            Text(text = data.list[day].weather[0].description,
                modifier = Modifier.padding(4.dp),
                style = MaterialTheme.typography.bodySmall)
        }
        val annotatedString = buildAnnotatedString {
            withStyle(style = ParagraphStyle()) {
                withStyle(style = SpanStyle(
                    color = Color.Blue.copy(alpha = 0.7f),
                    fontWeight = FontWeight.SemiBold
                )
                ) {
                    append("${kelvinToCelsius(data.list[day].temp.max)} \u2103")
                }
                withStyle(
                    SpanStyle(
                        color = Color.LightGray,
                        fontWeight = FontWeight.SemiBold
                    )
                ) {
                    append("\n ${kelvinToCelsius(data.list[day].temp.min)} \u2103")
                }
            }
        }
        //Max Min Temp of present day
        Text(text = annotatedString)
    }
}



@Composable
fun WeatherStateImage(imageUrl: String) {
    AsyncImage(
        model = imageUrl,
        contentDescription = "Image",
        modifier = Modifier.size(80.dp)
    )
}
