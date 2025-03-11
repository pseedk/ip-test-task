package ru.pvkovalev.ip_test_task.domain.usecase

import ru.pvkovalev.ip_test_task.domain.model.Item
import ru.pvkovalev.ip_test_task.domain.repository.DbRepository
import javax.inject.Inject

class DeleteItemUseCase @Inject constructor(private val dbRepository: DbRepository) {

    suspend fun invoke(item: Item) = dbRepository.deleteItem(item = item)
}