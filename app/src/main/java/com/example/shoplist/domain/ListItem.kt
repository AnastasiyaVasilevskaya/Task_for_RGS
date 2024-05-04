package com.example.shoplist.domain

data class ListItem(
    var id: Int = UNDEFINED_ID,
    val name: String
){
    companion object{
        const val UNDEFINED_ID = 0
    }
}