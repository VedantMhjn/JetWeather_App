package com.example.jetweatherforecast.screens.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetweatherforecast.repository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val repository: WeatherDbRepository): ViewModel() {
    private val _unitList = MutableStateFlow<List<com.example.jetweatherforecast.data.Unit>>(emptyList())
    val unitList = _unitList.asStateFlow()


    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUnit().distinctUntilChanged()
                .collect { listOfUnits ->
                    if (listOfUnits.isNullOrEmpty()) {
                        Log.d("TAG", ": Empty favs")
                    } else {
                        _unitList.value = listOfUnits
                        Log.d("UNITS", ": ${unitList.value}")
                    }
                }
        }
    }

    fun insertUnit(unit: com.example.jetweatherforecast.data.Unit):Job = viewModelScope.launch {
        repository.insertUnit(unit)
    }

    fun updateUnit(unit: com.example.jetweatherforecast.data.Unit) = viewModelScope.launch {
        repository.updateUnit(unit)
    }

    fun deleteUnit(unit: com.example.jetweatherforecast.data.Unit) = viewModelScope.launch {
        repository.deleteUnit(unit)
    }

    fun deleteAllUnit() = viewModelScope.launch {
        repository.deleteAllUnit()
    }


}