package ru.pvkovalev.ip_test_task.domain.model

data class Item(
    val id: Int,
    val name: String,
    val time: Int,
    val tags: String,
    val amount: Int
)
