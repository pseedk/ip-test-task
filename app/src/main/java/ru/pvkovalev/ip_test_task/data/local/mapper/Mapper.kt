package ru.pvkovalev.ip_test_task.data.local.mapper

import ru.pvkovalev.ip_test_task.data.local.model.ItemEntity
import ru.pvkovalev.ip_test_task.domain.model.Item
import javax.inject.Inject

class Mapper @Inject constructor() {

    fun mapItemEntityToItem(item: Item) =
        ItemEntity(
            id = item.id,
            name = item.name,
            time = item.time,
            tags = item.tags,
            amount = item.amount
        )

    private fun mapItemToItemEntity(itemEntity: ItemEntity) =
        Item(
            id = itemEntity.id,
            name = itemEntity.name,
            time = itemEntity.time,
            tags = itemEntity.tags,
            amount = itemEntity.amount
        )

    fun mapListItemToListItemEntity(list: List<ItemEntity>) =
        list.map { mapItemToItemEntity(it) }

}