package com.example.shoplist.domain.usecases

import androidx.lifecycle.LiveData
import com.example.shoplist.domain.ListItem
import com.example.shoplist.domain.ListRepository

class GetListUseCase (private val listRepository: ListRepository){
    fun getList():LiveData<List<ListItem>>{
        return listRepository.getList()
    }
}