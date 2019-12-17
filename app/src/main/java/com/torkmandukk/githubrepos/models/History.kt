package com.torkmandukk.githubrepos.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SearchHistory")
data class History(
        @PrimaryKey val search: String,
        val history: Long
)
