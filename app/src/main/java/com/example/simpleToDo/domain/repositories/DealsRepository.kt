package com.example.simpleToDo.domain.repositories

import com.example.simpleToDo.domain.models.Deal
import com.example.simpleToDo.domain.models.Tag
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface DealsRepository {
	fun loadListOfCurrentDeals(date: LocalDate): Flow<ImmutableList<Deal>>
	suspend fun addTag(tag: Tag): Long
	suspend fun addDeal(deal: Deal)
	suspend fun deleteDeal(id: Long)
	suspend fun deleteTag(id: Long)
	suspend fun changeDeal(deal: Deal)
}