package ru.pvkovalev.ip_test_task.domain.repository

import ru.pvkovalev.ip_test_task.domain.model.Item
import kotlinx.coroutines.flow.Flow

interface DbRepository {

    fun getItems(): Flow<List<Item>>

    suspend fun editItem(item: Item)

    suspend fun deleteItem(item: Item)

    fun searchItems(query: String): Flow<List<Item>>
}