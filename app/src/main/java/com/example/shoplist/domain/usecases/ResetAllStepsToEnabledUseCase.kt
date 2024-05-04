package com.example.shoplist.domain.usecases

import com.example.shoplist.domain.StepItem
import com.example.shoplist.domain.StepsRepository

class ResetAllStepsToEnabledUseCase (private val repository: StepsRepository){
    suspend fun resetSteps() {
        repository.resetSteps()
    }
}