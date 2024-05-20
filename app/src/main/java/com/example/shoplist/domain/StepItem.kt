package com.example.shoplist.domain

data class StepItem(
    var id: String,
    val name: String,
    var enabled: Boolean
){

    constructor() : this("0", "", false)
}