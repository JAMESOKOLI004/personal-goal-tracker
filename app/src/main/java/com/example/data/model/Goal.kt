package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "goals")
data class Goal(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val title: String,
    val description: String = "",
    val category: String = "Personal Growth",
    val targetDateMillis: Long,
    val emoji: String = "🎯",
    val notes: String = "",
    val isCompleted: Boolean = false,
    val completedDateMillis: Long? = null,
    val createdAtMillis: Long = System.currentTimeMillis()
)
