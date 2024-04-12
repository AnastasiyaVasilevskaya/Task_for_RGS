package com.example.shoplist.domain

import androidx.lifecycle.LiveData

class GetStepsUseCase (private val stepsRepository: StepsRepository) {
    fun getList(): LiveData<List<StepItem>> {
        return stepsRepository.getList()
    }
}