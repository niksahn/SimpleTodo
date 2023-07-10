package com.example.simpleToDo.data.repositories

import com.example.simpleToDo.data.database.dao.DealDao
import com.example.simpleToDo.data.database.entities.DealEntity
import com.example.simpleToDo.data.database.entities.TagEntity
import com.example.simpleToDo.data.database.entities.toDeal
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
			dealDao.getCurrentDeals(date),
			dealDao.getTagsFlow()
		) { dealEntities, tagEntities ->
			dealEntities.map { dealEntity ->
				dealEntity.toDeal(
					tag = tagEntities.find { it.id == dealEntity.tagId }
						?.let { Tag(name = it.name, id = it.id) }
				)
			}.sortedBy { it.priority }.toImmutableList()
		}
	
	override suspend fun addTag(tag: Tag) =
		dealDao.addTag(TagEntity(id = 0, name = tag.name))
	
	override suspend fun addDeal(deal: Deal) {
		val tagId = deal.tag?.name?.let { TagEntity(id = 0, name = it) }?.let { dealDao.addTag(it) }
		dealDao.addDeal(
			tagId = tagId,
			description = deal.description,
			date = deal.date,
			priority = deal.priority
		)
	}
	
	override suspend fun deleteDeal(id: Long) {
		dealDao.deleteDeal(id)
	}
	
	override suspend fun deleteTag(id: Long) {
		dealDao.deleteTag(id)
	}
	
	override suspend fun changeDeal(deal: Deal) {
		dealDao.updateDeal(dealEntity = DealEntity(
			id = deal.id,
			tagId = deal.tag?.id,
			description = deal.description,
			date = deal.date,
			priority = deal.priority,
			done = deal.done
		))
	}
}
