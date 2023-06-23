package com.muhammedkursatgokgun.cards.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Word(
    @ColumnInfo var englishW : String,
    @ColumnInfo var turkW : String
    ){
    @PrimaryKey(autoGenerate = true) var id : Int = 0
}