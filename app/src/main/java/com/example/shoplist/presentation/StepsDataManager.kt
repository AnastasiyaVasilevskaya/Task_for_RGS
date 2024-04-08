package com.example.shoplist.presentation

import com.example.shoplist.domain.ListItem

object StepsDataManager {
    val steps = listOf<ListItem>(
        ListItem("1. Получить от приемщика заказов извещение об инциденте, аварии.", true),
        ListItem("2. Инструктаж  бригады, доведение информации по объекту и характеру.", true),
        ListItem("3. Установка автомобиля, обеспечение освещения, и проезда спецтранспорта.", true),
        ListItem("4. Проверка подвала на наличие запаха газа, приборным методом.", true),
        ListItem("5. Доклад в АДС о проделанной работе.", true)
    )

    fun getStepsList() : List<ListItem> {
        return steps
    }
}
