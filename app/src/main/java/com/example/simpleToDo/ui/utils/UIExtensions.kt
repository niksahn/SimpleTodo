package com.example.simpleToDo.ui.utils

import com.example.simpleToDo.R
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.Locale


fun LocalDate.getWeek(): ArrayList<LocalDate> {
	val fieldISO = WeekFields.of(Locale.ROOT).dayOfWeek()
	val fistWeekday = this.with(fieldISO, 1)
	val week: ArrayList<LocalDate> = arrayListOf()
	for (i in 0 until 6) {
		week.add(fistWeekday.plusDays(i.toLong()))
	}
	return week
}

fun LocalDate.toShortDayString(): Int =
	when (this.dayOfWeek) {
		DayOfWeek.MONDAY -> R.string.shortMonday
		DayOfWeek.TUESDAY -> R.string.shortTuesday
		DayOfWeek.WEDNESDAY -> R.string.shortWednesday
		DayOfWeek.THURSDAY -> R.string.shortThursday
		DayOfWeek.FRIDAY -> R.string.shortFriday
		DayOfWeek.SATURDAY -> R.string.shortSaturday
		DayOfWeek.SUNDAY -> R.string.shortSunday
	}

fun LocalDate.toDayNumber(): String = this.dayOfMonth.toString()