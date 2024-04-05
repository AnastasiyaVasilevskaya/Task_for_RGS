package com.example.shoplist.data

import com.example.shoplist.domain.ShopItem

class ShopListMapper {
    fun mapEntityToDBModel(shopItem: ShopItem): ShopItemDBModel{
        return ShopItemDBModel(
            id = shopItem.id,
            name = shopItem.name,
            enabled = shopItem.enabled
        )
    }
    fun mapDBModelToEntity(shopItemDbModel: ShopItemDBModel): ShopItem{
        return ShopItem(
            id = shopItemDbModel.id,
            name = shopItemDbModel.name,
            enabled = shopItemDbModel.enabled
        )
    }
    fun mapListDbModelToEntity(list: List<ShopItemDBModel>) = list.map {
        mapDBModelToEntity(it)
    }
}