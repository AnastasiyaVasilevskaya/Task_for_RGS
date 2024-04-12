package com.example.shoplist.presentation

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoplist.data.repository.ListRepositoryImpl
import com.example.shoplist.data.repository.StepsRepositoryImpl
import com.example.shoplist.domain.EditStepsUseCase
import com.example.shoplist.domain.GetStepsUseCase

import com.example.shoplist.domain.StepItem
import com.example.shoplist.domain.UpdateStepsUseCase
import kotlinx.coroutines.launch

class StepsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = StepsRepositoryImpl(application)
    private val getStepsUseCase = GetStepsUseCase(repository)
    private val updateStepsUseCase = UpdateStepsUseCase(repository)


    val stepsListLD = getStepsUseCase.getList() //автообновление LD

    fun changeEnableState(item: StepItem) {
        viewModelScope.launch {
            val newItem = item.copy(enabled = !item.enabled)
            updateStepsUseCase.updateSteps(newItem)
            Log.d(
                ContentValues.TAG,
                "changeEnableState StepsVM from VMScope.launch() ${viewModelScope.coroutineContext} "
            )
        }
    }
}