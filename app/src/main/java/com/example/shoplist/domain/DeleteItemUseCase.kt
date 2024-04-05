package com.example.shoplist.domain

class DeleteItemUseCase (private val listRepository: ListRepository){
    suspend fun deleteItem(item: ListItem){
        listRepository.deleteItem(item)
    }
}