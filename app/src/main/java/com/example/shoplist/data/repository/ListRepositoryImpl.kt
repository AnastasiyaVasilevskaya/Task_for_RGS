package com.example.shoplist.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.shoplist.data.database.dao.ListDao
import com.example.shoplist.domain.ListItem
import com.example.shoplist.domain.ListRepository


class ListRepositoryImpl(
    private val listDao: ListDao
) : ListRepository {

    private val mapper = ListMapper()

    override fun getList(): LiveData<List<ListItem>> =
        listDao.getList().map { db ->
            mapper.mapListDbModelToEntity(db)
        }

    override suspend fun addItem(item: ListItem) {
        listDao.addItem(mapper.mapEntityToDBModel(item))
    }


    override suspend fun getItem(itemId: Int): ListItem {
        return mapper.mapDBModelToEntity(listDao.getItem(itemId))
    }

}