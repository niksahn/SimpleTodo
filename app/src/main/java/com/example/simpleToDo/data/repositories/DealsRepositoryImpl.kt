package com.example.simpleToDo.data.repositories

import androidx.room.withTransaction
import com.example.simpleToDo.data.database.AppDatabase
import com.example.simpleToDo.data.database.dao.DealDao
import com.example.simpleToDo.data.database.dao.TagDao
import com.example.simpleToDo.data.database.entities.DealEntity
import com.example.simpleToDo.data.database.entities.TagEntity
import com.example.simpleToDo.data.database.entities.toDeal
import com.example.simpleToDo.domain.models.Deal
import com.example.simpleToDo.domain.models.Tag
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
	
	suspend fun changePriorityDeal(
		targetPriority: Long? = null,
		curPriority: Long,
		date: LocalDate,
		done: Boolean,
		id: Long
	) {
		val maxPriority =
			dealDao.getCurrentDeals(date).filter { it.done == done }
				.maxByOrNull { it.priority }?.priority ?: 1
		var tPriority = targetPriority ?: maxPriority + 1
		if (curPriority <= maxPriority) {
			if (curPriority > tPriority) dealDao.updateSmallerPriority(
				dealId = id,
				dealDate = date,
				priority = curPriority,
				targetPriority = tPriority
			)
			else if (curPriority < tPriority) dealDao.updateBiggerPriority(
				dealId = id,
				dealDate = date,
				priority = curPriority,
				targetPriority = tPriority
			)
		}
	}
	
	override suspend fun changeDeal(
		id: Long,
		tagId: Long?,
		description: String?,
		date: LocalDate?,
		priority: Long?,
		done: Boolean?
	) {
		db.withTransaction {
			val curDeal = dealDao.getCurDeal(id)
			priority.takeIf { date == null }?.let {
				changePriorityDeal(
					targetPriority = it,
					curPriority = curDeal.priority,
					done = curDeal.done,
					date = curDeal.date,
					id = curDeal.id
				)
			}
			done?.let {
				if (it) {
					if (curDeal.tagId?.let { dealDao.getDealByTagId(it).size <= 1 } == true) {
						deleteTag(id)
					}
				}
			}
			tagId?.let {
				if (it != curDeal.tagId) {
					if (curDeal.tagId?.let { dealDao.getDealByTagId(it).size <= 1 } == true) {
						deleteTag(id)
					}
				}
			}
			date?.let {
				changePriorityDeal(
					curPriority = curDeal.priority,
					done = curDeal.done,
					date = curDeal.date,
					id = curDeal.id
				)
				val maxPriority =
					dealDao.getCurrentDeals(it).filter { it.done == done }
						.maxByOrNull { it.priority }?.priority ?: 0
				curDeal.priority = maxPriority + 1
			}
			dealDao.changeDeal(
				DealEntity(
					id = id,
					tagId = tagId ?: curDeal.tagId,
					description = description ?: curDeal.description,
					date = date ?: curDeal.date,
					priority = priority.takeIf { date == null } ?: curDeal.priority,
					done = done.takeIf { ((priority == null) and (date == null)) } ?: curDeal.done
				)
			)
		}
	}
}
