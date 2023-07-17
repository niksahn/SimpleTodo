package com.example.simpleToDo.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.simpleToDo.data.database.entities.TagEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class TagDao {
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
	
	@Insert(onConflict = OnConflictStrategy.IGNORE)
	abstract suspend fun addTag(tagEntity: TagEntity): Long
	
	@Query(
		"""
            DELETE FROM Tag WHERE id == :id
        """
	)
	abstract suspend fun deleteTag(id: Long)
	
	@Update
	abstract suspend fun changeTag(tagEntity: TagEntity)
}