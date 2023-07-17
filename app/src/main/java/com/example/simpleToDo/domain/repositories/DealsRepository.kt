package com.example.simpleToDo.domain.repositories

import com.example.simpleToDo.domain.models.Deal
import com.example.simpleToDo.domain.models.Tag
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface DealsRepository {
	suspend fun loadDeals(date: LocalDate): Flow<List<Deal>>
	suspend fun loadTags(): Flow<List<Tag>>
	suspend fun addTag(tag: String): Long
	suspend fun addDeal(tag: String?, description: String, date: LocalDate)
	suspend fun deleteDeal(deal: Deal)
	suspend fun deleteTag(id: Long)
	suspend fun changeDeal(deal: Deal)
	suspend fun changeTag(tag: Tag)
}