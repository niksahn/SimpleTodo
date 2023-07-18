package com.example.simpleToDo.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.simpleToDo.data.database.converters.DateConverter
import com.example.simpleToDo.data.database.dao.DealDao
import com.example.simpleToDo.data.database.dao.TagDao
import com.example.simpleToDo.data.database.entities.DealEntity
import com.example.simpleToDo.data.database.entities.TagEntity


@Database(
	entities = [
		DealEntity::class,
		TagEntity::class
	], version = 1, exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
	abstract fun getDealDao(): DealDao
	abstract fun getTagDao(): TagDao
}

