package com.example.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class GoalWithMilestones(
    @Embedded val goal: Goal,
    @Relation(
        parentColumn = "id",
        entityColumn = "goalId"
    )
    val milestones: List<MilestoneItem>
) {
    val totalMilestones: Int
        get() = milestones.size

    val completedMilestones: Int
        get() = milestones.count { it.isDone }

    val progressFraction: Float
        get() {
            if (goal.isCompleted) return 1.0f
            if (milestones.isEmpty()) return 0.0f
            return completedMilestones.toFloat() / totalMilestones.toFloat()
        }

    val progressPercentage: Int
        get() = (progressFraction * 100).toInt()

    fun daysLeft(): Long {
        val now = System.currentTimeMillis()
        val diff = goal.targetDateMillis - now
        return (diff / (1000 * 60 * 60 * 24))
    }
}
