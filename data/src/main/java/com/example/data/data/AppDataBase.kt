package com.example.data.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.domain.domain.entity.dBCollection.MovieCollection
import com.example.domain.domain.entity.dBCollection.MovieId
import com.example.domain.domain.entity.dBCollection.CollectionMovieCrossRef
import com.example.domain.domain.entity.dBCollection.Collection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = [
        MovieCollection::class,
        Collection::class,
        MovieId::class,
        CollectionMovieCrossRef::class
    ],
    version = 10,
    exportSchema = false
)
@TypeConverters(MovieIdListConverter::class)

abstract class AppDataBase : RoomDatabase() {
    abstract fun collectionDao(): MovieCollectionDao

    private class DBCallBack(
        private val scope: CoroutineScope
    ) : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE.let {
                scope.launch {
                    val dao = it?.collectionDao()

                    val viewed = Collection(collectionName = "Viewed")
                    val like = Collection(collectionName = "Любимые")
                    val wantToWatch = Collection(collectionName = "Хочу посмотреть")
                    val interesting = Collection(collectionName = "interesting")

                    dao?.insertCollection(viewed)
                    dao?.insertCollection(like)
                    dao?.insertCollection(wantToWatch)
                    dao?.insertCollection(interesting)
                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): AppDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "db"
                )
                    .addCallback(DBCallBack(scope))
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}