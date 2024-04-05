package com.example.shoplist.domain

data class ShopItem(
    val name: String,
    val enabled: Boolean,
    var id: Int = UNDEFINED_ID
){
    companion object{
        const val UNDEFINED_ID = 0
    }
}