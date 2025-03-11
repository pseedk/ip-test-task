package ru.pvkovalev.ip_test_task.data.local.repository

import ru.pvkovalev.ip_test_task.data.local.ItemsDao
import ru.pvkovalev.ip_test_task.data.local.mapper.Mapper
import ru.pvkovalev.ip_test_task.domain.model.Item
import ru.pvkovalev.ip_test_task.domain.repository.DbRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DbRepositoryImpl(
    private val itemsDao: ItemsDao,
    private val mapper: Mapper
) : DbRepository {

    override fun getItems(): Flow<List<Item>> =
        itemsDao.getItems().map {
            mapper.mapListItemToListItemEntity(it)
        }

    override suspend fun editItem(item: Item) {
        itemsDao.editItem(mapper.mapItemEntityToItem(item))
    }

    override suspend fun deleteItem(item: Item) {
        itemsDao.deleteItem(mapper.mapItemEntityToItem(item))
    }

    override fun searchItems(query: String): Flow<List<Item>> =
        itemsDao.searchItems(query = query).map {
            mapper.mapListItemToListItemEntity(it)
        }

}