package com.example.shoplist.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.shoplist.domain.ListItem
import com.example.shoplist.domain.ListRepository


class ListRepositoryImpl(
    application: Application
) : ListRepository {

    private val listDao = AppDatabase.getInstance(application).listDao()
    private val mapper = ListMapper()

    override fun getList(): LiveData<List<ListItem>> =
        MediatorLiveData<List<ListItem>>().apply {
            addSource(listDao.getList()) {
                value = mapper.mapListDbModelToEntity(it)
            }
        }

    override suspend fun addItem(item: ListItem) {
        listDao.addItem(mapper.mapEntityToDBModel(item))
    }

    override suspend fun editItem(item: ListItem) {
        listDao.addItem(mapper.mapEntityToDBModel(item))
    }

    override suspend fun getItem(itemId: Int): ListItem {
        return mapper.mapDBModelToEntity(listDao.getItem(itemId))
    }

    override suspend fun deleteItem(item: ListItem) {
        listDao.deleteItem(item.id)
    }
}