package ru.pvkovalev.ip_test_task.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item")
data class ItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val time: Int,
    val tags: String,
    val amount: Int
)
