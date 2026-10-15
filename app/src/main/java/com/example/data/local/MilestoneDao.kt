package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.MilestoneItem
import kotlinx.coroutines.flow.Flow

@Dao
interface MilestoneDao {
    @Query("SELECT * FROM milestones WHERE goalId = :goalId ORDER BY orderIndex ASC, dueDateMillis ASC")
    fun getMilestonesForGoal(goalId: Long): Flow<List<MilestoneItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMilestone(milestone: MilestoneItem): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMilestones(milestones: List<MilestoneItem>)

    @Update
    suspend fun updateMilestone(milestone: MilestoneItem)

    @Delete
    suspend fun deleteMilestone(milestone: MilestoneItem)

    @Query("DELETE FROM milestones WHERE id = :id")
    suspend fun deleteMilestoneById(id: Long)

    @Query("UPDATE milestones SET isDone = :isDone, completedDateMillis = :completedDate WHERE id = :id")
    suspend fun setMilestoneStatus(id: Long, isDone: Boolean, completedDate: Long?)
}
