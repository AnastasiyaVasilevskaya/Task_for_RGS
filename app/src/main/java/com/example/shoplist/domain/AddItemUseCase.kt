package com.example.shoplist.domain

class AddItemUseCase(private val stepsRepository: StepsRepository) {
    suspend operator fun invoke(item: StepItem){
        stepsRepository.addItem(item)
    }
}