package com.mindvalley.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.mindvalley.R
import com.mindvalley.bean.CategoryBean
import com.mindvalley.bean.ChannelSectionBean
import com.mindvalley.bean.ChannelsBean


class MV_DB_Hepler(context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    /**
     * Variable initialization
     */
    companion object {
        private val DB_VERSION = 5
        private val DB_NAME = "MindValley.DB"

        private val ID = "ID"

        // ========== TABLE DB NEW EPISODE===============
        private val TABLE_NEW_EPISODE = "TABLE_NEW_EPISODE"
        private val NE_title = "NE_title"
        private val NE_type = "NE_type"
        private val NE_channel_title = "NE_channel_title"
        private val NE_coverAsset_url = "NE_coverAsset_url"


        // ========== TABLE DB CHANNELS ===============
        private val TABLE_CHANNELS = "TABLE_CHANNELS"
        private val CH_ID = "CH_ID"
        private val CH_title = "CH_title"
        private val CH_COUNT = "CH_COUNT"
        private val CH_TH_URL = "CH_TH_URL"
        private val CH_URL = "CH_URL"
        private val CH_TYPE = "CH_TYPE"

        // ========== TABLE DB SERIES ===============
        private val TABLE_SERIES = "TABLE_SERIES"
        private val SE_ID = "SE_ID"
        private val SE_title = "SE_title"
        private val SE_URL = "SE_URL"

        // ========== TABLE DB LATESTMEDIA ===============
        private val TABLE_LATESTMEDIA = "TABLE_LATESTMEDIA"
        private val LM_ID = "LM_ID"
        private val LM_title = "LM_title"
        private val LM_URL = "LM_URL"
        private val LM_TYPE = "LM_TYPE"

        // ========== TABLE DB CATEGORIES ===============
        private val TABLE_CATEGORIES = "TABLE_CATEGORIES"
        private val CAT_title = "CAT_title"

    }

    /**
     * Create in database
     */
    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE_DB_STOCK_STORE =
            "CREATE TABLE $TABLE_NEW_EPISODE ($ID INTEGER PRIMARY KEY AUTOINCREMENT, $NE_title TEXT, $NE_type TEXT, $NE_channel_title TEXT, $NE_coverAsset_url TEXT);"

        val CREATE_TABLE_CHANNELS =
            "CREATE TABLE $TABLE_CHANNELS ($ID INTEGER PRIMARY KEY AUTOINCREMENT, $CH_ID TEXT, $CH_title TEXT, $CH_COUNT TEXT, $CH_TH_URL TEXT, $CH_URL TEXT ,$CH_TYPE TEXT);"

        val CREATE_TABLE_SERIES =
            "CREATE TABLE $TABLE_SERIES ($ID INTEGER PRIMARY KEY AUTOINCREMENT, $SE_ID TEXT, $SE_title TEXT, $SE_URL TEXT);"

        val CREATE_TABLE_LATESTMEDIA =
            "CREATE TABLE $TABLE_LATESTMEDIA ($ID INTEGER PRIMARY KEY AUTOINCREMENT, $LM_ID TEXT, $LM_title TEXT, $LM_URL TEXT, $LM_TYPE TEXT);"

        val CREATE_TABLE_CATEGORIES =
            "CREATE TABLE $TABLE_CATEGORIES ($ID INTEGER PRIMARY KEY AUTOINCREMENT, $CAT_title TEXT);"

        db.execSQL(CREATE_TABLE_DB_STOCK_STORE)
        db.execSQL(CREATE_TABLE_CHANNELS)
        db.execSQL(CREATE_TABLE_SERIES)
        db.execSQL(CREATE_TABLE_LATESTMEDIA)
        db.execSQL(CREATE_TABLE_CATEGORIES)

    }

    /**
     * Update in database
     */
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val DROP_NEW_EPISODE = "DROP TABLE IF EXISTS $TABLE_NEW_EPISODE"
        val DROP_CHANNELS = "DROP TABLE IF EXISTS $TABLE_CHANNELS"
        val DROP_SERIES = "DROP TABLE IF EXISTS $TABLE_SERIES"
        val DROP_LATESTMEDIA = "DROP TABLE IF EXISTS $TABLE_LATESTMEDIA"
        val DROP_CATEGORIES = "DROP TABLE IF EXISTS $TABLE_CATEGORIES"

        db.execSQL(DROP_NEW_EPISODE)
        db.execSQL(DROP_CHANNELS)
        db.execSQL(DROP_SERIES)
        db.execSQL(DROP_LATESTMEDIA)
        db.execSQL(DROP_CATEGORIES)

        onCreate(db)
    }

    /**
     * Insert data for New Episode table
     */
    fun addNewEpisode(channelsBean: ChannelsBean) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        for (i in 0 until channelsBean.data.media.size) {

            contentValues.put(NE_title, channelsBean.data.media[i].title)
            contentValues.put(NE_type, channelsBean.data.media[i].type)
            contentValues.put(NE_channel_title, channelsBean.data.media[i].channel.title)
            contentValues.put(NE_coverAsset_url, channelsBean.data.media[i].coverAsset.url)

           db.insert(TABLE_NEW_EPISODE, null, contentValues)
        }

    }

    /**
     * Get data for New Episode table
     */
    fun getNewEpisode(): ArrayList<ChannelsBean.Data.Media> {
        val db = writableDatabase
        val selectQuery = "Select * from $TABLE_NEW_EPISODE"
        val cursor = db.rawQuery(selectQuery, null)

        val storeList = java.util.ArrayList<ChannelsBean.Data.Media>()

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val tempBean: ChannelsBean.Data.Media = ChannelsBean.Data.Media()

                    tempBean.title = cursor.getString(cursor.getColumnIndex(NE_title))
                    tempBean.type = cursor.getString(cursor.getColumnIndex(NE_type))
                    tempBean.channel.title =
                        cursor.getString(cursor.getColumnIndex(NE_channel_title))
                    tempBean.coverAsset.url =
                        cursor.getString(cursor.getColumnIndex(NE_coverAsset_url))

                    storeList.add(tempBean)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        return storeList
    }

    /**
     * Delete data for New Episode table
     */
    fun deleteNewEpisode() {
        val db = writableDatabase
        db.delete(TABLE_NEW_EPISODE, null, null)
    }

    /**
     * Insert data for Channels table
     */
    fun addChannel(context: Context, channelSectionBean: ChannelSectionBean) {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        var count = 1
        for (i in 0 until channelSectionBean.data.channels.size) {

            contentValues.put(CH_ID, count)
            contentValues.put(CH_title, channelSectionBean.data.channels[i].title)
            contentValues.put(CH_COUNT, channelSectionBean.data.channels[i].mediaCount)
            if(channelSectionBean.data.channels[i].iconAsset != null){
            contentValues.put(CH_TH_URL, channelSectionBean.data.channels[i].iconAsset.thumbnailUrl)
                }
            contentValues.put(CH_URL, channelSectionBean.data.channels[i].coverAsset.url)

            val sizeArray = channelSectionBean.data.channels[i].series.size
            if (sizeArray == 0) {
                contentValues.put(CH_TYPE, context.resources.getString(R.string.episode))

            } else {
                contentValues.put(CH_TYPE, context.resources.getString(R.string.series))
            }
            for (j in 0 until channelSectionBean.data.channels[i].series.size) {
                val contentValuesSeries = ContentValues()
                contentValuesSeries.put(SE_ID, count)
                contentValuesSeries.put(SE_title, channelSectionBean.data.channels[i].series[j].title)
                contentValuesSeries.put(SE_URL, channelSectionBean.data.channels[i].series[j].coverAsset.url)
                db.insert(TABLE_SERIES, null, contentValuesSeries)

            }
            for (j in 0 until channelSectionBean.data.channels[i].latestMedia.size) {
                val contentValuesEpisode = ContentValues()
                contentValuesEpisode.put(LM_ID, count)
                contentValuesEpisode.put(LM_title, channelSectionBean.data.channels[i].latestMedia[j].title)
                contentValuesEpisode.put(LM_TYPE, channelSectionBean.data.channels[i].latestMedia[j].type )
                contentValuesEpisode.put(LM_URL, channelSectionBean.data.channels[i].latestMedia[j].coverAsset.url)
                db.insert(TABLE_LATESTMEDIA, null, contentValuesEpisode)
            }

            db.insert(TABLE_CHANNELS, null, contentValues)
            count++
        }

    }

    /**
     * Get data for Channels table
     */
    @SuppressLint("Recycle")
    fun getChannel(): ArrayList<ChannelSectionBean.Data.Channel> {

        val db = writableDatabase
        val selectQuery = "Select * from $TABLE_CHANNELS"
        val cursor = db.rawQuery(selectQuery, null)
        val storeList = java.util.ArrayList<ChannelSectionBean.Data.Channel>()

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {

                    val arrayListSeries = ArrayList<ChannelSectionBean.Data.Channel.Sery>()
                    val arrayListEpisode = ArrayList<ChannelSectionBean.Data.Channel.LatestMedia>()
                    val tempBean: ChannelSectionBean.Data.Channel =
                        ChannelSectionBean.Data.Channel()

                    tempBean.title = cursor.getString(cursor.getColumnIndex(CH_title))
                    tempBean.mediaCount =
                        Integer.parseInt(cursor.getString(cursor.getColumnIndex(CH_COUNT)))
                    tempBean.iconAsset.url = cursor.getString(cursor.getColumnIndex(CH_TH_URL))
                    tempBean.coverAsset.url = cursor.getString(cursor.getColumnIndex(CH_URL))

                    val type = cursor.getString(cursor.getColumnIndex(CH_TYPE))

                    if (type.equals("series", true)) {
                        val id = cursor.getString(cursor.getColumnIndex(CH_ID))
                        val selectQuerySeries = "Select * from $TABLE_SERIES where $SE_ID = '$id'"
                        val cursorSeries = db.rawQuery(selectQuerySeries, null)

                        if (cursorSeries != null) {
                            if (cursorSeries.moveToFirst()) {
                                do {
                                    val bean: ChannelSectionBean.Data.Channel.Sery =
                                        ChannelSectionBean.Data.Channel.Sery()
                                    bean.title = cursorSeries.getString(cursorSeries.getColumnIndex(SE_title))
                                    bean.coverAsset.url = cursorSeries.getString(cursorSeries.getColumnIndex(SE_URL))
                                    arrayListSeries.add(bean)
                                } while (cursorSeries.moveToNext())
                            }
                        }

                    }

                    tempBean.series = arrayListSeries

                    val id = cursor.getString(cursor.getColumnIndex(CH_ID))
                    val selectQueryEpisode = "Select * from $TABLE_LATESTMEDIA where $LM_ID = '$id'"
                    val cursorEpisode = db.rawQuery(selectQueryEpisode, null)

                    if (cursorEpisode != null) {
                        if (cursorEpisode.moveToFirst()) {
                            do {
                                val bean: ChannelSectionBean.Data.Channel.LatestMedia =
                                    ChannelSectionBean.Data.Channel.LatestMedia()

                                bean.title = cursorEpisode.getString(cursorEpisode.getColumnIndex(LM_title))
                                bean.coverAsset.url = cursorEpisode.getString(cursorEpisode.getColumnIndex(LM_URL))
                                bean.type = cursorEpisode.getString(cursorEpisode.getColumnIndex(LM_TYPE))
                                arrayListEpisode.add(bean)
                            } while (cursorEpisode.moveToNext())
                        }
                    }

                    tempBean.latestMedia = arrayListEpisode
                    storeList.add(tempBean)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        //db.close()
        return storeList
    }

    /**
     * Delete data for Channels table
     */
    fun deleteChannel() {

        val db = writableDatabase
        db.delete(TABLE_SERIES, null, null)
        db.delete(TABLE_LATESTMEDIA, null, null)
        db.delete(TABLE_CHANNELS, null, null)

    }

    /**
     * Insert data for Categories table
     */
    fun addCategories(categoryBean: CategoryBean) {
        val db = this.writableDatabase

        val contentValues = ContentValues()

        for (i in 0 until categoryBean.data.categories.size) {
            contentValues.put(CAT_title, categoryBean.data.categories[i].name)
            db.insert(TABLE_CATEGORIES, null, contentValues)
        }

    }

    /**
     * Get data for Categories table
     */
    fun getCategories(): ArrayList<CategoryBean.Data.Category> {
        val db = writableDatabase
        val selectQuery = "Select * from $TABLE_CATEGORIES"
        val cursor = db.rawQuery(selectQuery, null)

        val storeList = java.util.ArrayList<CategoryBean.Data.Category>()

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val tempBean: CategoryBean.Data.Category = CategoryBean.Data.Category()
                    tempBean.name = cursor.getString(cursor.getColumnIndex(CAT_title))
                    storeList.add(tempBean)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
              return storeList
    }

    /**
     * Delete data for Categories table
     */
    fun deleteCategory() {
        val db = writableDatabase
        db.delete(TABLE_CATEGORIES, null, null)
    }


}