package com.example.shoplist.domain

class EditItemUseCase(private val listRepository: ListRepository) {
    suspend fun editItem(item: ListItem){
        listRepository.editItem(item)
    }
}