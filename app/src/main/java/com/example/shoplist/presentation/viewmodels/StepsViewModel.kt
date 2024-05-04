package com.example.shoplist.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.shoplist.data.database.AppDatabase
import com.example.shoplist.data.repository.StepsRepositoryImpl
import com.example.shoplist.domain.usecases.GetStepsUseCase
import com.example.shoplist.domain.StepItem
import com.example.shoplist.domain.usecases.ResetAllStepsToEnabledUseCase
import com.example.shoplist.domain.usecases.UpdateStepsUseCase
import kotlinx.coroutines.launch

class StepsViewModel(application: Application) : AndroidViewModel(application) {

    private val appDB = AppDatabase.getInstance(application)
    private val repository = StepsRepositoryImpl(appDB.stepsDao())
    private val getStepsUseCase = GetStepsUseCase(repository)
    private val updateStepsUseCase = UpdateStepsUseCase(repository)
    private val resetAllStepsToEnabledUseCase = ResetAllStepsToEnabledUseCase(repository)

    val stepsListLD = getStepsUseCase.getList() //автообновление LD

    fun changeEnableState(item: StepItem) {
        viewModelScope.launch {
            val newItem = item.copy(enabled = !item.enabled)
            updateStepsUseCase.updateSteps(newItem)
        }
    }

    fun resetToEnabledItems() {
        viewModelScope.launch {
            resetAllStepsToEnabledUseCase.resetSteps()
        }
    }
}