package com.example.shoplist.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.shoplist.data.database.AppDatabase
import com.example.shoplist.data.repository.ListRepositoryImpl
import com.example.shoplist.domain.usecases.GetItemUseCase
import com.example.shoplist.domain.usecases.GetListUseCase

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val appDB = AppDatabase.getInstance(application)
    private val repository = ListRepositoryImpl(appDB.listDao())
    private val getListUseCase = GetListUseCase(repository)
    private val getItemUseCase = GetItemUseCase(repository)

    val listLD = getListUseCase.getList() //автообновление LD

}