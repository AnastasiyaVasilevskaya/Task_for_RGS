package com.example.shoplist.presentation

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoplist.data.repository.ListRepositoryImpl
import com.example.shoplist.domain.EditItemUseCase
import com.example.shoplist.domain.GetListUseCase
import com.example.shoplist.domain.ListItem
import kotlinx.coroutines.launch

class StepsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ListRepositoryImpl(application)
    private val getListUseCase = GetListUseCase(repository)
    private val editItemUseCase = EditItemUseCase(repository)


    val stepsListLD = getListUseCase.getList() //автообновление LD

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