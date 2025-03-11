package ru.pvkovalev.ip_test_task.domain.usecase

import ru.pvkovalev.ip_test_task.domain.repository.DbRepository
import javax.inject.Inject

class SearchItemsUseCase @Inject constructor(private val dbRepository: DbRepository) {

    fun invoke(query: String) = dbRepository.searchItems(query = query)
}