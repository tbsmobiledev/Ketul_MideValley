package com.mindvalley.api

import android.content.Context
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject
import ui.Config

import java.util.HashMap
import com.google.gson.Gson
import com.mindvalley.bean.ChannelsBean
import com.stocktakeonline.backend.AppSingleton
import com.stocktakeonline.backend.ResponseListener
import com.stocktakeonline.ui.Log


class GetNewEpisodesSectionAPI(context: Context, responseListener: ResponseListener) {

    internal var tag = this.javaClass.simpleName
    private lateinit var mJsonObjectRequest: JsonObjectRequest
    private lateinit  var MContext:Context
    internal  var mJsonObject:JSONObject?=null
    private lateinit var mResponseListener: ResponseListener
    internal var mParams =  ""

    private var mChannelsBean : ChannelsBean = ChannelsBean()

    /**
     * Variable initialization
     */
    init {
        try {
            this.MContext = context
            this.mResponseListener = responseListener
            this.mJsonObject = JSONObject()


            Log.print(tag, "Params::: $mParams")

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * API call execute
     */
    fun execute() {

        mJsonObjectRequest = object : JsonObjectRequest(
            Method.GET,Config.API_NEW_EPISODES_SECTION + mParams
           // AppSettings.getAPIwithBASEURL(context , Config.API_NEW_EPISODES_SECTION + params)
            , mJsonObject ,
            Response.Listener { response ->

                    parse(response)

            }, Response.ErrorListener { error ->
                error.printStackTrace()
                VolleyLog.d(tag, "Error: $error")

                Log.print(tag, " Error: " + error.message)
                doCallBack(Config.API_FAIL, error.message.toString(),  "")
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
               // headers = AppSettings.getHeader_Hashmap(context)
                return headers
            }

        }
        mJsonObjectRequest.retryPolicy = DefaultRetryPolicy(Config.TIMEOUT_SOCKET,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)


        // Adding request to request queue
        val policy = DefaultRetryPolicy(Config.TIMEOUT_SOCKET,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        mJsonObjectRequest.retryPolicy = policy

        AppSingleton.getInstance(MContext).addToRequestQueue(mJsonObjectRequest, Config.TAG_NEW_EPISODES_SECTION)
    }

    /**
     * Response parsing
     */
    private fun parse(response: JSONObject?) {
        var response = response
        var success = 1
        var mesg = ""

        try {
            Log.print(tag, "response" + " > " + response!!.toString())

            val gson = Gson()
            val channelsBean1 = gson.fromJson(response.toString(), ChannelsBean::class.java)

            mChannelsBean = channelsBean1
            success = Config.API_SUCCESS
                } catch (e: Exception) {
            success = -1
            mesg = ("Exception :: " + this.javaClass + " :: parse() :: " + e.localizedMessage)
            Log.error(tag+ " :: Exception :: ", e.toString())
        }

        doCallBack(success, mesg, mChannelsBean)

    }

    /**
     * CallBack according to response
     */
    private fun doCallBack(code: Int, mesg: String, objects: Any) {
        try {
            if (code == Config.API_SUCCESS) {
                mResponseListener.onResponse(Config.TAG_NEW_EPISODES_SECTION, Config.API_SUCCESS, objects)
            } else if (code > Config.API_SUCCESS) {
                mResponseListener.onResponse(Config.TAG_NEW_EPISODES_SECTION, Config.API_FAIL, mesg)
            } else if (code < Config.API_SUCCESS) {
                mResponseListener.onResponse(Config.TAG_NEW_EPISODES_SECTION, Config.API_FAIL, mesg)
            }
        } catch (e: Exception) {
            Log.error(this.javaClass.toString() + " :: Exception :: ", e.toString())
        }

    }


}
