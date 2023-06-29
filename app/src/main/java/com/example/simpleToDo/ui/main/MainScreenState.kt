package com.example.simpleToDo.ui.main

import com.example.simpleToDo.domain.models.Deal
import com.example.simpleToDo.domain.models.Tag
import com.example.simpleToDo.utils.base.State
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate
import java.util.Date

data class ListScreenState(
	val listOfDeals: ImmutableList<Deal> = persistentListOf(),
	val listOfDays: ImmutableList<Date> = persistentListOf(),
	val isLoading: Boolean = true
) : State()


