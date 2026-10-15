package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.model.Goal
import com.example.data.model.MilestoneItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [Goal::class, MilestoneItem::class],
    version = 1,
    exportSchema = false
)
abstract class MilestoneDatabase : RoomDatabase() {
    abstract fun goalDao(): GoalDao
    abstract fun milestoneDao(): MilestoneDao

    companion object {
        @Volatile
        private var INSTANCE: MilestoneDatabase? = null

        fun getInstance(context: Context): MilestoneDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MilestoneDatabase::class.java,
                    "milestone_tracker.db"
                )
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // Populate demo starter data on fresh install
                            CoroutineScope(Dispatchers.IO).launch {
                                populateDemoData(getInstance(context))
                            }
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }

        suspend fun populateDemoData(db: MilestoneDatabase) {
            val now = System.currentTimeMillis()
            val day = 24L * 60 * 60 * 1000

            // Goal 1: Half Marathon
            val goal1Id = db.goalDao().insertGoal(
                Goal(
                    title = "Run My First Half Marathon",
                    description = "Train consistently, build endurance, and finish the 21.1 km course strong.",
                    category = "Health & Fitness",
                    targetDateMillis = now + 42 * day,
                    emoji = "🏃‍♂️",
                    notes = "Started pacing with zone 2 heart rate training. Feeling more energetic each week!",
                    isCompleted = false
                )
            )
            db.milestoneDao().insertMilestones(
                listOf(
                    MilestoneItem(
                        goalId = goal1Id,
                        title = "Get proper running shoes & gear",
                        dueDateMillis = now - 15 * day,
                        isDone = true,
                        completedDateMillis = now - 14 * day,
                        orderIndex = 0
                    ),
                    MilestoneItem(
                        goalId = goal1Id,
                        title = "Complete continuous 5 km run",
                        dueDateMillis = now - 7 * day,
                        isDone = true,
                        completedDateMillis = now - 6 * day,
                        orderIndex = 1
                    ),
                    MilestoneItem(
                        goalId = goal1Id,
                        title = "Run 10 km weekend long run",
                        dueDateMillis = now + 8 * day,
                        isDone = false,
                        orderIndex = 2
                    ),
                    MilestoneItem(
                        goalId = goal1Id,
                        title = "Complete 16 km distance simulation",
                        dueDateMillis = now + 24 * day,
                        isDone = false,
                        orderIndex = 3
                    ),
                    MilestoneItem(
                        goalId = goal1Id,
                        title = "Race Day: Complete Half Marathon 21.1 km",
                        dueDateMillis = now + 42 * day,
                        isDone = false,
                        orderIndex = 4
                    )
                )
            )

            // Goal 2: Launch App
            val goal2Id = db.goalDao().insertGoal(
                Goal(
                    title = "Launch Mobile App MVP",
                    description = "Design, develop and publish version 1.0 of the personal milestone app.",
                    category = "Career & Tech",
                    targetDateMillis = now + 24 * day,
                    emoji = "🚀",
                    notes = "Focus on crisp visual polish, snappy animations, and intuitive milestone checklists.",
                    isCompleted = false
                )
            )
            db.milestoneDao().insertMilestones(
                listOf(
                    MilestoneItem(
                        goalId = goal2Id,
                        title = "Complete UI wireframes and user flow",
                        dueDateMillis = now - 10 * day,
                        isDone = true,
                        completedDateMillis = now - 9 * day,
                        orderIndex = 0
                    ),
                    MilestoneItem(
                        goalId = goal2Id,
                        title = "Set up database and reactive state management",
                        dueDateMillis = now - 2 * day,
                        isDone = true,
                        completedDateMillis = now - 1 * day,
                        orderIndex = 1
                    ),
                    MilestoneItem(
                        goalId = goal2Id,
                        title = "Build goal detail progress ring & checklist",
                        dueDateMillis = now + 6 * day,
                        isDone = false,
                        orderIndex = 2
                    ),
                    MilestoneItem(
                        goalId = goal2Id,
                        title = "Beta test with 5 close friends",
                        dueDateMillis = now + 15 * day,
                        isDone = false,
                        orderIndex = 3
                    ),
                    MilestoneItem(
                        goalId = goal2Id,
                        title = "Publish to Google Play Store",
                        dueDateMillis = now + 24 * day,
                        isDone = false,
                        orderIndex = 4
                    )
                )
            )

            // Goal 3: Read Books
            val goal3Id = db.goalDao().insertGoal(
                Goal(
                    title = "Read 12 Inspiring Books",
                    description = "Expand mind and perspectives through reading 1 book every month.",
                    category = "Personal Growth",
                    targetDateMillis = now + 75 * day,
                    emoji = "📚",
                    notes = "Highlighting key quotes in every chapter. 20 pages every morning with coffee.",
                    isCompleted = false
                )
            )
            db.milestoneDao().insertMilestones(
                listOf(
                    MilestoneItem(
                        goalId = goal3Id,
                        title = "Finish 'Atomic Habits' by James Clear",
                        dueDateMillis = now - 30 * day,
                        isDone = true,
                        completedDateMillis = now - 28 * day,
                        orderIndex = 0
                    ),
                    MilestoneItem(
                        goalId = goal3Id,
                        title = "Finish 'Deep Work' by Cal Newport",
                        dueDateMillis = now - 5 * day,
                        isDone = true,
                        completedDateMillis = now - 3 * day,
                        orderIndex = 1
                    ),
                    MilestoneItem(
                        goalId = goal3Id,
                        title = "Finish 'Psychology of Money'",
                        dueDateMillis = now + 20 * day,
                        isDone = false,
                        orderIndex = 2
                    ),
                    MilestoneItem(
                        goalId = goal3Id,
                        title = "Finish 'Thinking in Systems'",
                        dueDateMillis = now + 50 * day,
                        isDone = false,
                        orderIndex = 3
                    )
                )
            )

            // Goal 4: Completed Goal in Archive
            val goal4Id = db.goalDao().insertGoal(
                Goal(
                    title = "Learn Conversational Spanish",
                    description = "Achieve B1 conversational fluency for travels in Latin America.",
                    category = "Learning",
                    targetDateMillis = now - 12 * day,
                    emoji = "🇪🇸",
                    notes = "Completed 180 consecutive days on audio lessons and held a 30-min conversation with a native tutor!",
                    isCompleted = true,
                    completedDateMillis = now - 10 * day
                )
            )
            db.milestoneDao().insertMilestones(
                listOf(
                    MilestoneItem(
                        goalId = goal4Id,
                        title = "Complete 50 core vocabulary modules",
                        dueDateMillis = now - 60 * day,
                        isDone = true,
                        completedDateMillis = now - 58 * day,
                        orderIndex = 0
                    ),
                    MilestoneItem(
                        goalId = goal4Id,
                        title = "Listen to 20 Spanish learning podcasts",
                        dueDateMillis = now - 30 * day,
                        isDone = true,
                        completedDateMillis = now - 28 * day,
                        orderIndex = 1
                    ),
                    MilestoneItem(
                        goalId = goal4Id,
                        title = "Complete 30-min live conversational tutoring session",
                        dueDateMillis = now - 12 * day,
                        isDone = true,
                        completedDateMillis = now - 10 * day,
                        orderIndex = 2
                    )
                )
            )
        }
    }
}
