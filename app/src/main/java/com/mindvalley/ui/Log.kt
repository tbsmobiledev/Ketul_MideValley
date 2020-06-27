package com.stocktakeonline.ui

import com.mindvalley.ui.Storage
import java.io.File
import java.io.RandomAccessFile
import java.util.Date

object Log {

    var DO_LOGGING = true
    var DO_SOP = true

    fun error(title: String, mesg: String) {
        var logFile: File? = null
        var raf: RandomAccessFile? = null

        try {
            if (DO_LOGGING) {
                logFile = Storage.verifyLogFile()
                raf = RandomAccessFile(logFile, "rw")
                raf.seek(logFile!!.length())
                raf.writeUTF(
                    "<TR>" + "<TD style=\"color:#FF0000\">"
                            + Date() + "</TD>" + "<TD style=\"color:#FF0000\">"
                            + title + "</TD>" + "<TD style=\"color:#FF0000\">"
                            + mesg + "</TD></TR>"
                )
            }
        } catch (exception: Exception) {
        } finally {
            if (raf != null) {
                try {
                    raf.close()
                } catch (e: Exception) {
                }
            }
        }
    }

    fun print(title: String, mesg: String) {
        if (DO_SOP) {
            println("$title :: $mesg")
        }
    }

}