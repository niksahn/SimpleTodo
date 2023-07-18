package com.example.simpleToDo.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.example.simpleToDo.data.database.entities.DealEntity
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
	abstract suspend fun getCurrentDeals(date: LocalDate): List<DealEntity>
	
	@Query(
		"""
			Select * From Deal Where tagId == :tagId
		"""
	)
	abstract suspend fun getDealByTagId(tagId: Long): List<DealEntity>
	
	
	@Query(
		"""
             INSERT INTO Deal (tagId,description,date,priority,done) VALUES (:tagId,:description,:date,:priority,:done)
        """
	)
	abstract suspend fun addDeal(
		tagId: Long?, description: String, date: LocalDate, priority: Long, done: Boolean
	)
	
	@Query(
		"""
            Update Deal Set tagId = null WHERE tagId == :tagId
        """
	)
	abstract suspend fun deleteTagId(tagId: Long)
	
	@Query(
		"""
            DELETE FROM Deal WHERE id == :id
        """
	)
	abstract suspend fun deleteDeal(id: Long)
	
	@Update
	abstract suspend fun changeDeal(dealEntity: DealEntity)
	
	@Query(
		"""
			Select priority  From Deal where id=:dealId
		"""
	)
	abstract suspend fun getCurPriority(dealId: Long): Long
	
	@Query(
		"""
			Update Deal set priority = priority+1   where date==:dealDate and priority<=:priority and priority>=:targetPriority and id!=:dealId
		"""
	)
	abstract suspend fun updateSmallerPriority(
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
	abstract suspend fun updateBiggerPriority(
		dealId: Long,
		dealDate: LocalDate,
		priority: Long,
		targetPriority: Long
	)
}