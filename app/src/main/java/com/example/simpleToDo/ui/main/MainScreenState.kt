package com.example.simpleToDo.ui.main

import com.example.simpleToDo.ui.models.DealUi
import com.example.simpleToDo.utils.base.State
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate

data class ListScreenState(
	val listOfDeals: ImmutableList<DealUi> = persistentListOf(),
	val selectedDay: LocalDate = LocalDate.now(),
	val listOfDays: ImmutableList<LocalDate> = persistentListOf(),
	val isLoading: Boolean = true
) : State()


