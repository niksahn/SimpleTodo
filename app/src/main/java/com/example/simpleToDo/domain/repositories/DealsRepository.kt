package com.example.simpleToDo.domain.repositories

import com.example.simpleToDo.domain.models.Deal
import com.example.simpleToDo.domain.models.Tag
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface DealsRepository {
	fun loadListOfCurrentDeals(date: LocalDate): Flow<ImmutableList<Deal>>
	suspend fun addTag(tag: String): Long
	suspend fun addDeal(tag: String?, description: String, date: LocalDate)
	suspend fun deleteDeal(id: Long)
	suspend fun deleteTag(id: Long)
	suspend fun changeDeal(deal: Deal)
}