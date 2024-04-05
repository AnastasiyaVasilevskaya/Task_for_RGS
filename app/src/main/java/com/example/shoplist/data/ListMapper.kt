package com.example.shoplist.data

import com.example.shoplist.domain.ListItem

class ListMapper {
    fun mapEntityToDBModel(item: ListItem): ItemDBModel{
        return ItemDBModel(
            id = item.id,
            name = item.name,
            enabled = item.enabled
        )
    }
    fun mapDBModelToEntity(shopItemDbModel: ItemDBModel): ListItem{
        return ListItem(
            id = shopItemDbModel.id,
            name = shopItemDbModel.name,
            enabled = shopItemDbModel.enabled
        )
    }
    fun mapListDbModelToEntity(list: List<ItemDBModel>) = list.map {
        mapDBModelToEntity(it)
    }
}