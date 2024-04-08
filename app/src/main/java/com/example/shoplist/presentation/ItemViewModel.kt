package com.example.shoplist.presentation

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.shoplist.data.repository.ListRepositoryImpl
import com.example.shoplist.domain.AddItemUseCase
import com.example.shoplist.domain.EditItemUseCase
import com.example.shoplist.domain.GetItemUseCase
import com.example.shoplist.domain.ListItem
import kotlinx.coroutines.launch

class ItemViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ListRepositoryImpl(application)

    private val _errorInputName = MutableLiveData<Boolean>()// работаем с этой из VM
    val errorInputName: LiveData<Boolean>  // работаем с этой из Activity
        get() = _errorInputName

    private val _itemLD = MutableLiveData<ListItem>()
    val itemLD: LiveData<ListItem>
        get() = _itemLD

    private val _finishCurrentScreenLD = MutableLiveData<Unit>()
    val finishCurrentScreenLD: LiveData<Unit>
        get() = _finishCurrentScreenLD

    private val editItemUseCase = EditItemUseCase(repository)
    private val getItemUseCase = GetItemUseCase(repository)
    private val addShopItemUseCase = AddItemUseCase(repository)

    fun getItem(itemID: Int) {
        viewModelScope.launch {
            val item = getItemUseCase.getItem(itemID)
            _itemLD.value = item
            Log.d(
                TAG,
                "getShopItem ShopItemVM from VMScope.launch() ${viewModelScope.coroutineContext} "
            )
        }
    }

    fun addItem(inputName: String?) {
        val name = parseString(inputName)
        val validationResult = validateInputTypes(name)
        if (validationResult) {
            viewModelScope.launch {
                val item = ListItem(name, true)
                addShopItemUseCase.addItem(item)
                _finishCurrentScreenLD.value = Unit
                Log.d(
                    TAG,
                    "addShopItem ShopItemVM from VMScope.launch() ${viewModelScope.coroutineContext} "
                )
            }
        }
    }

    fun editItem(inputName: String?) {
        val name = parseString(inputName)
        val validationResult = validateInputTypes(name)
        if (validationResult) {
            _itemLD.value?.let {
                viewModelScope.launch {
                    val item = it.copy(name = name)
                    editItemUseCase.editItem(item)
                    _finishCurrentScreenLD.value = Unit
                    Log.d(
                        TAG,
                        "editShopItem ShopItemVM from VMScope.launch() ${viewModelScope.coroutineContext} "
                    )
                }
            }
        }
    }

    private fun parseString(shopItemName: String?): String {
        return shopItemName?.trim() ?: ""
    }

    private fun validateInputTypes(inputString: String): Boolean {
        var result = true
        if (inputString.isBlank()) {
            _errorInputName.value = true
            result = false
        }
        return result
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

}