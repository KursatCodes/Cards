package com.muhammedkursatgokgun.cards.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.muhammedkursatgokgun.cards.model.Word

@Database(entities = [Word::class], version = 1)
abstract class WordDb: RoomDatabase() {
    abstract fun wordDao() : WordDao
}