package com.example.shoplist.domain

class GetItemUseCase (private val listRepository: ListRepository){
    suspend operator fun invoke(itemId: Int): ListItem{
        return listRepository.getItem(itemId)
    }
}