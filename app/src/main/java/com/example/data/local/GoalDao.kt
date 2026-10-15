package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.data.model.Goal
import com.example.data.model.GoalWithMilestones
import kotlinx.coroutines.flow.Flow

@Dao
interface GoalDao {
    @Transaction
    @Query("SELECT * FROM goals WHERE isCompleted = :isCompleted ORDER BY targetDateMillis ASC")
    fun getGoalsWithMilestones(isCompleted: Boolean): Flow<List<GoalWithMilestones>>

    @Transaction
    @Query("SELECT * FROM goals WHERE id = :goalId")
    fun getGoalWithMilestonesById(goalId: Long): Flow<GoalWithMilestones?>

    @Transaction
    @Query("SELECT * FROM goals ORDER BY targetDateMillis ASC")
    fun getAllGoalsWithMilestones(): Flow<List<GoalWithMilestones>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoal(goal: Goal): Long

    @Update
    suspend fun updateGoal(goal: Goal)

    @Delete
    suspend fun deleteGoal(goal: Goal)

    @Query("DELETE FROM goals WHERE id = :goalId")
    suspend fun deleteGoalById(goalId: Long)

    @Query("UPDATE goals SET isCompleted = :isCompleted, completedDateMillis = :completedDate WHERE id = :goalId")
    suspend fun setGoalCompleted(goalId: Long, isCompleted: Boolean, completedDate: Long?)

    @Query("UPDATE goals SET notes = :notes WHERE id = :goalId")
    suspend fun updateGoalNotes(goalId: Long, notes: String)

    @Query("SELECT COUNT(*) FROM goals WHERE isCompleted = 0")
    fun countActiveGoals(): Flow<Int>

    @Query("SELECT COUNT(*) FROM goals WHERE isCompleted = 1")
    fun countCompletedGoals(): Flow<Int>
}
