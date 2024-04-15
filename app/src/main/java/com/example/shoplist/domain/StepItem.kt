package com.example.shoplist.domain

data class StepItem(
    var id: Int = UNDEFINED_ID,
    val name: String,
    var enabled: Boolean
) {
    companion object {
        const val UNDEFINED_ID = 0
    }
}
