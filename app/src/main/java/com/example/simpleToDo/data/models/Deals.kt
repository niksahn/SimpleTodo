package com.example.simpleToDo.data.models

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class Deal(
    val title: String,
    val tags: ImmutableList<String> = persistentListOf(),
    val description: String,
    val date: Float,
)