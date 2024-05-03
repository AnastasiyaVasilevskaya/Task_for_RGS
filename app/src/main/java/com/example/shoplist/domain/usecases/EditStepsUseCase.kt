package com.example.shoplist.domain.usecases;

import com.example.shoplist.domain.StepItem
import com.example.shoplist.domain.StepsRepository

class EditStepsUseCase(private val stepsRepository: StepsRepository) {
    suspend fun editItem(item: StepItem) {
        stepsRepository.editItem(item)
    }
}
