package com.example.shoplist.presentation

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoplist.data.ListRepositoryImpl
import com.example.shoplist.domain.DeleteItemUseCase
import com.example.shoplist.domain.EditItemUseCase
import com.example.shoplist.domain.GetListUseCase
import com.example.shoplist.domain.ListItem
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ListRepositoryImpl(application)

    private val editItemUseCase = EditItemUseCase(repository)
    private val deleteItemUseCase = DeleteItemUseCase(repository)
    private val getListUseCase = GetListUseCase(repository)

    val shopListLD = getListUseCase.getList() //автообновление LD
    fun deleteItem(item: ListItem) {
        viewModelScope.launch {
            deleteItemUseCase.deleteItem(item)
            Log.d(
                ContentValues.TAG,
                "deleteItem MainVM from VMScope.launch() ${viewModelScope.coroutineContext} "
            )
        }
    }

    fun changeEnableState(item: ListItem) {
        viewModelScope.launch {
            val newItem = item.copy(enabled = !item.enabled)
            editItemUseCase.editItem(newItem)
            Log.d(
                ContentValues.TAG,
                "changeEnableState MainVM from VMScope.launch() ${viewModelScope.coroutineContext} "
            )
        }
    }
}