package com.example.simpleToDo.data.database.converters

import androidx.room.TypeConverter
import java.time.LocalDate
import java.util.Date

class DateConverter {
	@TypeConverter
	fun toDate(date: String): LocalDate =
		LocalDate.parse(date)
	
	@TypeConverter
	fun toString(date: LocalDate): String =
		date.toString()
}