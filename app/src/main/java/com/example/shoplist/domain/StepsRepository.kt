package com.example.shoplist.domain

import androidx.lifecycle.LiveData


interface StepsRepository {
    fun getList(): LiveData<List<StepItem>>
    suspend fun addItem(item: StepItem)
    suspend fun editItem(item: StepItem)
    suspend fun updateItem(item: StepItem)
    suspend fun resetSteps(stepsList: List<StepItem>)
    suspend fun getItem(itemId: Int): StepItem

}