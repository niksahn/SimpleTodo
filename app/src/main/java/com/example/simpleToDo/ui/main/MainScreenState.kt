package com.example.simpleToDo.ui.main

import com.example.simpleToDo.ui.models.DealUi
import com.example.simpleToDo.utils.base.State
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.util.Date

data class ListScreenState(
	val listOfDeals: ImmutableList<DealUi> = persistentListOf(),
	val listOfDays: ImmutableList<Date> = persistentListOf(),
	val isLoading: Boolean = true
) : State()


