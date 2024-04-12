package com.example.shoplist.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.shoplist.data.utils.ListMapper
import com.example.shoplist.data.database.AppDatabase
import com.example.shoplist.data.utils.StepsMapper
import com.example.shoplist.domain.ListItem
import com.example.shoplist.domain.StepItem
import com.example.shoplist.domain.StepsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class StepsRepositoryImpl(
    application: Application
) : StepsRepository {

    private val stepsDao = AppDatabase.getInstance(application).stepsDao()
    private val mapper = StepsMapper()

    override fun getList(): LiveData<List<StepItem>> =
        MediatorLiveData<List<StepItem>>().apply {
            addSource(stepsDao.fetchAllSteps()) {
                value = mapper.mapListDbModelToEntity(it)
            }
        }

    override suspend fun addItem(item: StepItem) {
        stepsDao.addItem(mapper.mapEntityToDBModel(item))
    }

    override suspend fun editItem(item: StepItem) {
        withContext(Dispatchers.IO) {
            stepsDao.addItem(mapper.mapEntityToDBModel(item))
        }
    }

    override suspend fun getItem(itemId: Int): StepItem {
        return mapper.mapDBModelToEntity(stepsDao.getItem(itemId))
    }

    override suspend fun updateItem(item: StepItem) {
        withContext(Dispatchers.IO) {
            stepsDao.updateItem(mapper.mapEntityToDBModel(item))
        }
    }
}