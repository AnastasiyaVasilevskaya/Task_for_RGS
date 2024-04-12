package com.example.shoplist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.shoplist.data.repository.ListRepositoryImpl
import com.example.shoplist.domain.GetListUseCase

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ListRepositoryImpl(application)
    private val getListUseCase = GetListUseCase(repository)

    val listLD = getListUseCase.getList() //автообновление LD

}