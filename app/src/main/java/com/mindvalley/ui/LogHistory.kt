package com.mindvalley.ui

import android.os.Parcel
import android.os.Parcelable

class LogHistory : Parcelable {

    // Creates an empty log.
    constructor() {}

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
    }


    fun apiCallTest() {
        println("API Call start")
    }

    fun dataBinding() {
        println("Data Set for adapter")
    }


    companion object {
        val CREATOR: Parcelable.Creator<LogHistory?> = object : Parcelable.Creator<LogHistory?> {
            override fun createFromParcel(`in`: Parcel): LogHistory? {
                return LogHistory()
            }

            override fun newArray(p0: Int): Array<LogHistory?> {
                TODO("Not yet implemented")
            }


        }
    }
}