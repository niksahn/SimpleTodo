package com.example.simpleToDo.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity("Tag", indices = [Index(value = arrayOf("name"), unique = true)])
data class TagEntity(
	@PrimaryKey(autoGenerate = true)
	@ColumnInfo("id")
	val id: Long,
	@ColumnInfo("name")
	val name: String
)