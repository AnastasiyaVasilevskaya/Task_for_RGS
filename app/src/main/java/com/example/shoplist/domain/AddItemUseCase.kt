package com.example.shoplist.domain

class AddItemUseCase(private val listRepository: ListRepository) {
    suspend fun addItem(item: ListItem){
        listRepository.addItem(item)
    }
}