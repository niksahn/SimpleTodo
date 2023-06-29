package com.example.simpleToDo.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.example.simpleToDo.data.database.entities.DealEntity
import com.example.simpleToDo.data.database.entities.TagEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface DealDao {
	@Query(
		"""
		Select * From Deal
	"""
	)
	fun getDeals(): Flow<List<DealEntity>>
	
	@Query(
		"""
		Select * From Tag
	"""
	)
	fun getTags(): Flow<List<TagEntity>>
	
	@Query(
		"""
             INSERT INTO Deal (tagId,description,date,priority) VALUES (:tagId,:description,:date,:priority)
        """
	)
	suspend fun addPeriod(
		tagId: Long, description: String, date: LocalDate, priority: Long
	)
	
	@Query(
		"""
             INSERT INTO Tag (name) VALUES (:name)
        """
	)
	suspend fun addTag(name: String)
	
	@Query(
		"""
            DELETE FROM Deal WHERE id == :id
        """
	)
	suspend fun deleteDeal(id: Long)
	
	@Query(
		"""
            DELETE FROM Tag WHERE id == :id
        """
	)
	suspend fun deleteTag(id: Long)
	
	@Update
	suspend fun changeDeal(dealEntity: DealEntity)
	
}