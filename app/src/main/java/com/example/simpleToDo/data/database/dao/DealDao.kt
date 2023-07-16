package com.example.simpleToDo.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.simpleToDo.data.database.entities.DealEntity
import com.example.simpleToDo.data.database.entities.TagEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
abstract class DealDao {
	
	@Query(
		"""
			Select * From Deal Where date == :date
		"""
	)
	abstract fun getCurrentDealsFlow(date: LocalDate): Flow<List<DealEntity>>
	
	@Query(
		"""
			Select * From Deal Where date == :date
		"""
	)
	abstract fun getCurrentDeals(date: LocalDate): List<DealEntity>
	
	@Query(
		"""
		Select * From Tag
	"""
	)
	abstract fun getTagsFlow(): Flow<List<TagEntity>>
	
	@Query(
		"""
		Select * From Tag
	"""
	)
	abstract fun getTags(): List<TagEntity>
	
	@Query(
		"""
		Select * From Tag where name=:name
	"""
	)
	abstract fun getTagByName(name: String): List<TagEntity>
	
	@Transaction
	open suspend fun addDealTag(tag: String?, description: String, date: LocalDate) {
		val tagId =
			tag?.let { TagEntity(id = 0, name = it) }?.let { addTag(it) }.takeIf { it != -1L }
				?: getTagByName(tag ?: "").last().id
		addDeal(
			tagId = tagId,
			description = description,
			date = date,
			priority = getCurrentDeals(date).maxOfOrNull { it.priority }?.plus(1) ?: 1,
			done = false
		)
	}
	
	@Query(
		"""
             INSERT INTO Deal (tagId,description,date,priority,done) VALUES (:tagId,:description,:date,:priority,:done)
        """
	)
	protected abstract suspend fun addDeal(
		tagId: Long?, description: String, date: LocalDate, priority: Long, done: Boolean
	)
	
	
	@Insert(onConflict = IGNORE)
	abstract suspend fun addTag(tagEntity: TagEntity): Long
	
	@Query(
		"""
            DELETE FROM Deal WHERE id == :id
        """
	)
	abstract suspend fun deleteDeal(id: Long)
	
	@Query(
		"""
            DELETE FROM Tag WHERE id == :id
        """
	)
	abstract suspend fun deleteTag(id: Long)
	
	@Update
	abstract suspend fun changeTag(tagEntity: TagEntity)
	
	@Transaction
	open suspend fun updateDeal(dealEntity: DealEntity) {
		val maxPriority =
			getCurrentDeals(dealEntity.date).map { it.takeIf { it.done == dealEntity.done } }
				.maxByOrNull { it?.priority ?: 0 }?.priority ?: 0
		val curPriority = getCurPriority(dealEntity.id)
		if (dealEntity.priority < maxPriority) {
			if (curPriority > dealEntity.priority) updateSmallerPriority(
				dealId = dealEntity.id,
				dealDate = dealEntity.date,
				priority = curPriority,
				targetPriority = dealEntity.priority
			)
			else if (curPriority < dealEntity.priority) updateBiggerPriority(
				dealId = dealEntity.id,
				dealDate = dealEntity.date,
				priority = curPriority,
				targetPriority = dealEntity.priority
			)
			changeDeal(dealEntity = dealEntity)
		}
	} // Должен обновлять заметку и приоритеты для остальных
	
	@Update
	protected abstract suspend fun changeDeal(dealEntity: DealEntity)
	
	@Query(
		"""
			Select priority  From Deal where id=:dealId
		"""
	)
	protected abstract suspend fun getCurPriority(dealId: Long): Long
	
	@Query(
		"""
			Update Deal set priority = priority+1   where date==:dealDate and priority<=:priority and priority>=:targetPriority and id!=:dealId
		"""
	)
	protected abstract suspend fun updateSmallerPriority(
		dealId: Long,
		dealDate: LocalDate,
		priority: Long,
		targetPriority: Long
	)
	
	@Query(
		"""
			Update Deal set priority = priority-1   where date==:dealDate and priority>=:priority and priority<=:targetPriority and id!=:dealId
		"""
	)
	protected abstract suspend fun updateBiggerPriority(
		dealId: Long,
		dealDate: LocalDate,
		priority: Long,
		targetPriority: Long
	)
}