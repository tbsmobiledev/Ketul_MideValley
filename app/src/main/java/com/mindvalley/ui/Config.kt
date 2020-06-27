package ui

import android.content.Context
import android.os.Environment

class Config {

    companion object {

        lateinit var context:Context

        val TAG = "StockTakeOnline"
        val APP_HOME: String? = Environment.getExternalStorageDirectory().path + "/" + TAG
        val  DIR_LOG = APP_HOME + "/log"

        val  API_SUCCESS = 0
        val  API_FAIL = 1

        // SOCKET TIMEOUT IS SET TO SECONDS
        val  TIMEOUT_SOCKET = 100000

        /**
         * API List
         */
        val API_NEW_EPISODES_SECTION = "https://pastebin.com/raw/z5AExTtw" //GET
        val API_CHANNELS_SECTION =  "https://pastebin.com/raw/Xt12uVhM"  //GET
        val API_CATEGORIES_SECTION =  "https://pastebin.com/raw/A0CgArX3"

        /**
         * API Tags
         */
        val TAG_NEW_EPISODES_SECTION = "TAG_NEW_EPISODES_SECTION"
        val TAG_CHANNELS_SECTION = "TAG_CHANNELS_SECTION"
        val TAG_CATEGORIES_SECTION = "TAG_CATEGORIES_SECTION"

    }

}