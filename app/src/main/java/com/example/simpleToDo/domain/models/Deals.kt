package com.example.simpleToDo.domain.models

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate

data class Deal(
    val id: Long,
    val tag: Tag,
    val description: String,
    val date: LocalDate,
    val priority: Long,
    val done: Boolean
)