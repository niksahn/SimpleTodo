package com.example.simpleToDo.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.simpleToDo.domain.models.Deal
import java.time.LocalDate

@Entity("Deal")
data class DealEntity(
	@PrimaryKey(autoGenerate = true)
	@ColumnInfo("id")
	val id: Long,
	@ColumnInfo("tagId")
	val tagId: Long?,
	@ColumnInfo("description")
	val description: String,
	@ColumnInfo("date")
	val date: LocalDate,
	@ColumnInfo("priority")
	val priority: Long,
	@ColumnInfo("done")
	val done: Boolean
)

fun DealEntity.toDeal() = Deal(
	id = id,
	tagId = tagId,
	description = description,
	date = date,
	priority = priority,
	done = done
)