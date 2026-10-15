package com.example.data.repository

import com.example.data.local.GoalDao
import com.example.data.local.MilestoneDao
import com.example.data.local.MilestoneDatabase
import com.example.data.model.Goal
import com.example.data.model.GoalWithMilestones
import com.example.data.model.MilestoneItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class GoalRepository(
    private val database: MilestoneDatabase,
    private val goalDao: GoalDao = database.goalDao(),
    private val milestoneDao: MilestoneDao = database.milestoneDao()
) {
    val activeGoals: Flow<List<GoalWithMilestones>> = goalDao.getGoalsWithMilestones(isCompleted = false)
    val completedGoals: Flow<List<GoalWithMilestones>> = goalDao.getGoalsWithMilestones(isCompleted = true)
    val allGoals: Flow<List<GoalWithMilestones>> = goalDao.getAllGoalsWithMilestones()

    fun getGoalWithMilestones(goalId: Long): Flow<GoalWithMilestones?> {
        return goalDao.getGoalWithMilestonesById(goalId)
    }

    suspend fun saveGoal(goal: Goal, initialMilestoneTitles: List<String> = emptyList()): Long {
        val goalId = if (goal.id == 0L) {
            val newId = goalDao.insertGoal(goal)
            initialMilestoneTitles.filter { it.isNotBlank() }.forEachIndexed { index, title ->
                milestoneDao.insertMilestone(
                    MilestoneItem(
                        goalId = newId,
                        title = title.trim(),
                        dueDateMillis = goal.targetDateMillis,
                        isDone = false,
                        orderIndex = index
                    )
                )
            }
            newId
        } else {
            goalDao.updateGoal(goal)
            goal.id
        }
        return goalId
    }

    suspend fun addMilestone(goalId: Long, title: String, dueDateMillis: Long): Long {
        return milestoneDao.insertMilestone(
            MilestoneItem(
                goalId = goalId,
                title = title.trim(),
                dueDateMillis = dueDateMillis,
                isDone = false
            )
        )
    }

    suspend fun toggleMilestone(milestone: MilestoneItem, goalId: Long): Boolean {
        val newDoneStatus = !milestone.isDone
        val completedDate = if (newDoneStatus) System.currentTimeMillis() else null
        milestoneDao.setMilestoneStatus(milestone.id, newDoneStatus, completedDate)

        // Check if all milestones are now done for this goal
        val updatedGoal = goalDao.getGoalWithMilestonesById(goalId).firstOrNull()
        if (updatedGoal != null && updatedGoal.milestones.isNotEmpty()) {
            val allDone = updatedGoal.milestones.all { if (it.id == milestone.id) newDoneStatus else it.isDone }
            if (allDone && !updatedGoal.goal.isCompleted) {
                // Auto mark complete or prepare celebration!
                goalDao.setGoalCompleted(goalId, true, System.currentTimeMillis())
                return true // Trigger celebration
            } else if (!allDone && updatedGoal.goal.isCompleted) {
                goalDao.setGoalCompleted(goalId, false, null)
            }
        }
        return false
    }

    suspend fun setGoalCompleted(goalId: Long, isCompleted: Boolean) {
        val completedDate = if (isCompleted) System.currentTimeMillis() else null
        goalDao.setGoalCompleted(goalId, isCompleted, completedDate)
    }

    suspend fun deleteMilestone(milestoneId: Long) {
        milestoneDao.deleteMilestoneById(milestoneId)
    }

    suspend fun updateGoalNotes(goalId: Long, notes: String) {
        goalDao.updateGoalNotes(goalId, notes)
    }

    suspend fun deleteGoal(goalId: Long) {
        goalDao.deleteGoalById(goalId)
    }

    suspend fun resetDemoData() {
        database.clearAllTables()
        MilestoneDatabase.populateDemoData(database)
    }
}
