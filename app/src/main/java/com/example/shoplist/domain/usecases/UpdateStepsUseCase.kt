package com.example.shoplist.domain.usecases

import com.example.shoplist.domain.StepItem
import com.example.shoplist.domain.StepsRepository

class UpdateStepsUseCase (private val repository: StepsRepository){
    suspend fun updateSteps(stepItem: StepItem) {
        repository.updateItem(stepItem)
    }
}