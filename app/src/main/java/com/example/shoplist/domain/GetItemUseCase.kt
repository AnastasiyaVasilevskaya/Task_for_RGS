package com.example.shoplist.domain

class GetItemUseCase (private val listRepository: ListRepository){
    suspend fun getItem(itemId: Int): ListItem{
        return listRepository.getItem(itemId)
    }
}