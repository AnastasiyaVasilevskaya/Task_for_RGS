package com.example.shoplist.domain

import androidx.lifecycle.LiveData

class GetListUseCase (private val listRepository: ListRepository){
    fun getList():LiveData<List<ListItem>>{
        return listRepository.getList()
    }
}