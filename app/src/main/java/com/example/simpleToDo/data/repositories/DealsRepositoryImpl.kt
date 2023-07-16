package com.example.simpleToDo.data.repositories

import com.example.simpleToDo.data.database.dao.DealDao
import com.example.simpleToDo.data.database.entities.TagEntity
import com.example.simpleToDo.data.database.entities.toDeal
import com.example.simpleToDo.data.database.entities.toDealEntity
import com.example.simpleToDo.domain.models.Deal
import com.example.simpleToDo.domain.models.Tag
import com.example.simpleToDo.domain.repositories.DealsRepository
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.time.LocalDate
import javax.inject.Inject

class DealsRepositoryImpl @Inject constructor(
	private val dealDao: DealDao
) : DealsRepository {
	override fun loadListOfCurrentDeals(date: LocalDate): Flow<ImmutableList<Deal>> =
		combine(
			dealDao.getCurrentDealsFlow(date),
			dealDao.getTagsFlow()
		) { dealEntities, tagEntities ->
			dealEntities.map { dealEntity ->
				dealEntity.toDeal(
					tag = tagEntities.find { it.id == dealEntity.tagId }
						?.let { Tag(name = it.name, id = it.id) }
				)
			}.sortedBy { it.priority }.toImmutableList()
		}
	
	override suspend fun addTag(tag: String) =
		dealDao.addTag(TagEntity(id = 0, name = tag))
	
	override suspend fun addDeal(tag: String?, description: String, date: LocalDate) {
		dealDao.addDealTag(
			tag = tag,
			description = description,
			date = date,
		)
	}
	
	override suspend fun deleteDeal(deal: Deal) {
		dealDao.deleteDealTag(
			deal.toDealEntity()
		)
	}
	
	override suspend fun deleteTag(id: Long) {
		dealDao.deleteTag(id)
	}
	
	override suspend fun changeDeal(deal: Deal) {
		dealDao.updateDeal(
			deal.toDealEntity()
		)
	}
}
