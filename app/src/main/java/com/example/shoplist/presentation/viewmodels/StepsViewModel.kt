package com.example.shoplist.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoplist.dataFB.StepsRepositoryImpl
import com.example.shoplist.domain.usecases.GetStepsUseCase
import com.example.shoplist.domain.StepItem
import com.example.shoplist.domain.usecases.ResetAllStepsToEnabledUseCase
import com.example.shoplist.domain.usecases.UpdateStepsUseCase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch

class StepsViewModel(application: Application) : AndroidViewModel(application) {

    private val firebaseRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("1_1_steps")
    private val firebaseRepository = StepsRepositoryImpl(firebaseRef)
    private val getStepsUseCase = GetStepsUseCase(firebaseRepository)
    private val updateStepsUseCase = UpdateStepsUseCase(firebaseRepository)
    private val resetAllStepsToEnabledUseCase = ResetAllStepsToEnabledUseCase(firebaseRepository)

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