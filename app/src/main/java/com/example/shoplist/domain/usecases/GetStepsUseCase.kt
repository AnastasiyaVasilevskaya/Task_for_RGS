package com.example.shoplist.domain.usecases

import androidx.lifecycle.LiveData
import com.example.shoplist.domain.StepItem
import com.example.shoplist.domain.StepsRepository

class GetStepsUseCase (private val stepsRepository: StepsRepository) {
    fun getList(): LiveData<List<StepItem>> {
        return stepsRepository.getList()
    }
}