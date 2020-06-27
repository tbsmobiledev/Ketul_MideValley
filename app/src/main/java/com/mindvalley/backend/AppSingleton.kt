package com.stocktakeonline.backend

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class AppSingleton private constructor(context: Context) {
    private var mRequestQueue: RequestQueue? = null

    val requestQueue: RequestQueue?
        get() {
            if (mRequestQueue == null) {
                mRequestQueue = Volley.newRequestQueue(mContext.applicationContext)
            }
            return mRequestQueue
        }

    init {
        mContext = context
        mRequestQueue = requestQueue
    }

    fun <T> addToRequestQueue(req: Request<T>, tag: String) {
        req.tag = tag
        requestQueue?.add(req)
    }

    companion object {
        private var mAppSingletonInstance: AppSingleton? = null
        private lateinit var mContext: Context

        @Synchronized
        fun getInstance(context: Context): AppSingleton {
            if (mAppSingletonInstance == null) {
                mAppSingletonInstance = AppSingleton(context)
            }
            return mAppSingletonInstance as AppSingleton


        }
    }
}