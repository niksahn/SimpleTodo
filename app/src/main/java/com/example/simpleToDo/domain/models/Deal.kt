package com.example.simpleToDo.domain.models

import com.example.simpleToDo.data.database.entities.DealEntity
import com.example.simpleToDo.ui.models.DealUi
import java.time.LocalDate

data class Deal(
	val id: Long,
	val tagId: Long?,
	val description: String,
	val date: LocalDate,
	val priority: Long,
	val done: Boolean
)

fun Deal.toDealUi(tag: Tag?) = DealUi(
	id = id,
	date = date,
	description = description,
	priority = priority,
	done = done,
	tag = tag
)

fun Deal.toDealEntity() = DealEntity(
	id = id,
	tagId = tagId,
	description = description,
	date = date,
	priority = priority,
	done = done
)
