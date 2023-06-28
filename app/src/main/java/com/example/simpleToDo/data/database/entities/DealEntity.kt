package com.example.simpleToDo.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf


@Entity("Deal")
data class DayScheduleEntity (
	@PrimaryKey(autoGenerate = true)
	@ColumnInfo("id")
	val id: Long,
	@ColumnInfo("title")
	val title: String,
	@ColumnInfo("tags")
	val tags: ImmutableList<String> = persistentListOf(),
	@ColumnInfo("description")
	val description: String,
	@ColumnInfo("date")
	val date: Float,
)
