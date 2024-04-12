package com.example.shoplist.data.utils

import com.example.shoplist.data.database.entity.ItemDBModel
import com.example.shoplist.domain.ListItem

class ListMapper {
    fun mapEntityToDBModel(item: ListItem): ItemDBModel {
        return ItemDBModel(
            id = item.id,
            name = item.name
        )
    }

    fun mapDBModelToEntity(shopItemDbModel: ItemDBModel): ListItem {
        return ListItem(
            id = shopItemDbModel.id,
            name = shopItemDbModel.name
        )
    }

    fun mapListDbModelToEntity(list: List<ItemDBModel>) = list.map {
        mapDBModelToEntity(it)
    }
}