package com.example.shoplist.domain

class UpdateStepsUseCase (private val repository: StepsRepository){
    suspend fun updateSteps(stepItem: StepItem) {
        repository.updateItem(stepItem)
    }
}