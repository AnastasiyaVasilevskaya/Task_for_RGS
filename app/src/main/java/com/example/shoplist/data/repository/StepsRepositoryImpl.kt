package com.example.shoplist.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.shoplist.data.database.dao.StepsDao
import com.example.shoplist.domain.StepItem
import com.example.shoplist.domain.StepsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class StepsRepositoryImpl(
    private val stepsDao: StepsDao
) : StepsRepository {

    private val mapper = StepsMapper()

    override fun getList(): LiveData<List<StepItem>> =
        stepsDao.fetchAllSteps().map { db ->
            mapper.mapListDbModelToEntity(db)
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

    override suspend fun resetSteps() {
        withContext(Dispatchers.IO) {
            stepsDao.resetSteps()
        }
    }
}