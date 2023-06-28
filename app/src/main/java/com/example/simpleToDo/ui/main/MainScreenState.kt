package com.example.simpleToDo.ui.main

import com.example.simpleToDo.data.models.Deal
import com.example.simpleToDo.utils.base.State
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.util.*

data class ListScreenState(
    val listOfDeals: ImmutableList<DealsState> = persistentListOf(),
    val listOfDays: ImmutableList<Date> = persistentListOf(),
    val isLoading: Boolean = true
): State()

data class DealsState(
    val title: String,
    val tags: ImmutableList<String> = persistentListOf(),
    val description: String,
    val date: Float,
)

fun Deal.mapToItemState() = DealsState(
    title = title,
    tags = tags,
    description = description,
    date = date
)