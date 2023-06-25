package com.muhammedkursatgokgun.cards.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.muhammedkursatgokgun.cards.model.Word
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

@Dao
interface WordDao {
    @Query("SELECT * FROM Word")// Word ---> Entity sınıfı
    fun getAll(): Flowable<List<Word>>
    @Insert
    fun insert(word: Word):Completable
    @Delete
    fun delete(word: Word): Completable
}