package com.example.shoplist.data.utils

import com.example.shoplist.data.database.entity.ItemDBModel
import com.example.shoplist.data.database.entity.StepsDBModel
import com.example.shoplist.domain.ListItem
import com.example.shoplist.domain.StepItem

class StepsMapper {
    fun mapEntityToDBModel(step: StepItem): StepsDBModel {
        return StepsDBModel(
            id = step.id,
            name = step.name,
            enabled = step.enabled
        )
    }

    fun mapDBModelToEntity(stepItemDbModel: StepsDBModel): StepItem {
        return StepItem(
            id = stepItemDbModel.id,
            name = stepItemDbModel.name,
            enabled = stepItemDbModel.enabled
        )
    }

    fun mapListDbModelToEntity(list: List<StepsDBModel>) = list.map {
        mapDBModelToEntity(it)
    }
}