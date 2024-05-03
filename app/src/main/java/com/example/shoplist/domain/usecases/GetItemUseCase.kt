package com.example.shoplist.domain.usecases

import com.example.shoplist.domain.ListItem
import com.example.shoplist.domain.ListRepository

class GetItemUseCase (private val listRepository: ListRepository){
    suspend operator fun invoke(itemId: Int): ListItem {
        return listRepository.getItem(itemId)
    }
}