package com.example.shoplist.domain;

class EditStepsUseCase(private val stepsRepository: StepsRepository) {
    suspend fun editItem(item: StepItem) {
        stepsRepository.editItem(item)
    }
}
