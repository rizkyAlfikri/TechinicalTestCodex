package com.dicoding.picodiploma.techinicaltestcodex.data.source.local.room

import android.content.Context
import com.dicoding.picodiploma.techinicaltestcodex.data.source.local.dao.UserStoryDao

//@Database(entities = [UserStoryEntity::class], version = 1, exportSchema = false)
abstract class UserStoryDatabase  {

    abstract fun userStoryDao(): UserStoryDao

    companion object {
        private var INSTANCE: UserStoryDatabase? = null

        fun getInstance(context: Context): UserStoryDatabase? {
            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    UserStoryDatabase::class.java,
//                    BuildConfig.DB_NAME
//                ).build()
//
//                INSTANCE = instance
                return INSTANCE
            }
        }
    }
}