package com.example.shoplist.presentation

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.shoplist.data.ShopListRepositoryImpl
import com.example.shoplist.domain.AddShopItemUseCase
import com.example.shoplist.domain.EditShopItemUseCase
import com.example.shoplist.domain.GetShopItemUseCase
import com.example.shoplist.domain.ShopItem
import kotlinx.coroutines.launch

class ShopItemViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ShopListRepositoryImpl(application)

    private val _errorInputName = MutableLiveData<Boolean>()// работаем с этой из VM
    val errorInputName: LiveData<Boolean>  // работаем с этой из Activity
        get() = _errorInputName

    private val _shopItemLD = MutableLiveData<ShopItem>()
    val shopItemLD: LiveData<ShopItem>
        get() = _shopItemLD

    private val _finishCurrentScreenLD = MutableLiveData<Unit>()
    val finishCurrentScreenLD: LiveData<Unit>
        get() = _finishCurrentScreenLD

    private val editShopItemUseCase = EditShopItemUseCase(repository)
    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)

    fun getShopItem(shopItemID: Int) {
        viewModelScope.launch {
            val item = getShopItemUseCase.getShopItem(shopItemID)
            _shopItemLD.value = item
            Log.d(
                TAG,
                "getShopItem ShopItemVM from VMScope.launch() ${viewModelScope.coroutineContext} "
            )
        }
    }

    fun addShopItem(inputName: String?) {
        val name = parseString(inputName)
        val validationResult = validateInputTypes(name)
        if (validationResult) {
            viewModelScope.launch {
                val shopItem = ShopItem(name, true)
                addShopItemUseCase.addShopItem(shopItem)
                _finishCurrentScreenLD.value = Unit
                Log.d(
                    TAG,
                    "addShopItem ShopItemVM from VMScope.launch() ${viewModelScope.coroutineContext} "
                )
            }
        }
    }

    fun editShopItem(inputName: String?) {
        val name = parseString(inputName)
        val validationResult = validateInputTypes(name)
        if (validationResult) {
            _shopItemLD.value?.let {
                viewModelScope.launch {
                    val item = it.copy(name = name)
                    editShopItemUseCase.editShopItem(item)
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

    private fun parseCount(shopItemCount: String?): Int {
        return try {
            shopItemCount?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
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