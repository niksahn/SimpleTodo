package com.example.simpleToDo.ui.models

import com.example.simpleToDo.domain.models.Tag
import java.time.LocalDate

data class DealUi(
	val id: Long,
	val tag: Tag?,
	val description: String,
	val date: LocalDate,
	val priority: Long,
	val done: Boolean
)


