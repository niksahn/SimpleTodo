package com.example.simpleToDo.data.repositories

import androidx.room.withTransaction
import com.example.simpleToDo.data.database.AppDatabase
import com.example.simpleToDo.data.database.dao.DealDao
import com.example.simpleToDo.data.database.dao.TagDao
import com.example.simpleToDo.data.database.entities.TagEntity
import com.example.simpleToDo.data.database.entities.toDeal
import com.example.simpleToDo.domain.models.Deal
import com.example.simpleToDo.domain.models.Tag
import com.example.simpleToDo.domain.models.toDealEntity
import com.example.simpleToDo.domain.repositories.DealsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class DealsRepositoryImpl @Inject constructor(
	private val dealDao: DealDao,
	private val tagDao: TagDao,
	private val db: AppDatabase
) : DealsRepository {
	override suspend fun loadDeals(date: LocalDate): Flow<List<Deal>> =
		dealDao.getCurrentDealsFlow(date).map { it.map { it.toDeal() } }
	
	override suspend fun loadTags(): Flow<List<Tag>> =
		tagDao.getTagsFlow().map { tag -> tag.map { Tag(it.id, it.name) } }
	
	
	override suspend fun addTag(tag: String) =
		tagDao.addTag(TagEntity(id = 0, name = tag))
	
	override suspend fun addDeal(tag: String?, description: String, date: LocalDate) {
		db.withTransaction {
			val tagId =
				tag?.let { tagId -> addTag(tagId).takeIf { it != -1L } }
					?: tagDao.getTagByName(tag ?: "").last().id
			dealDao.addDeal(
				tagId = tagId,
				description = description,
				date = date,
				priority = dealDao.getCurrentDeals(date).maxOfOrNull { it.priority }?.plus(1) ?: 1,
				done = false
			)
		}
	}
	
	override suspend fun deleteDeal(deal: Deal) {
		db.withTransaction {
			dealDao.deleteDeal(deal.id)
			if (deal.tagId?.let { dealDao.getDealByTagId(it).isEmpty() } == false) {
				tagDao.deleteTag(deal.tagId)
			}
		}
	}
	
	override suspend fun deleteTag(id: Long) {
		db.withTransaction {
			tagDao.deleteTag(id)
			dealDao.deleteTagId(id)
		}
	}
	
	override suspend fun changeTag(tag: Tag) {
		tagDao.changeTag(TagEntity(id = tag.id, name = tag.name))
	}
	
	override suspend fun changeDeal(deal: Deal) {
		db.withTransaction {
			val maxPriority =
				dealDao.getCurrentDeals(deal.date).filter { it.done == deal.done }
					.maxByOrNull { it.priority }?.priority ?: 1
			val curPriority = dealDao.getCurPriority(deal.id)
			if (deal.priority <= maxPriority) {
				if (curPriority > deal.priority) dealDao.updateSmallerPriority(
					dealId = deal.id,
					dealDate = deal.date,
					priority = curPriority,
					targetPriority = deal.priority
				)
				else if (curPriority < deal.priority) dealDao.updateBiggerPriority(
					dealId = deal.id,
					dealDate = deal.date,
					priority = curPriority,
					targetPriority = deal.priority
				)
				if (deal.done) {
					if (deal.tagId?.let { dealDao.getDealByTagId(it).size <= 1 } == true) {
						deleteTag(deal.id)
					}
				}
				dealDao.changeDeal(deal.toDealEntity())
			}
		}
	}
}
