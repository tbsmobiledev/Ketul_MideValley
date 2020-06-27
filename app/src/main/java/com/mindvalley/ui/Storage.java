package com.mindvalley.ui;

import android.annotation.SuppressLint;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ui.Config;

public class Storage {

    public static void verifyLogPath() {
        File dir = new File(Config.Companion.getDIR_LOG());
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    @SuppressLint("SimpleDateFormat")
    public static File verifyLogFile() throws IOException {
        File logFile = new File(Config.Companion.getDIR_LOG() + "/"+Config.Companion.getTAG()+"Log"
                + new SimpleDateFormat("yyyy_MM_dd").format(new Date())
                + ".html");
        FileOutputStream fos = null;

        Storage.verifyLogPath();

        if (!logFile.exists()) {
            logFile.createNewFile();

            fos = new FileOutputStream(logFile);

            String str = "<TABLE style=\"width:100%;border=1px\" cellpadding=2 cellspacing=2 border=1><TR>"
                    + "<TD style=\"width:30%\"><B>Date n Time</B></TD>"
                    + "<TD style=\"width:20%\"><B>Title</B></TD>"
                    + "<TD style=\"width:50%\"><B>Description</B></TD></TR>";

            fos.write(str.getBytes());
        }

        if (fos != null) {
            fos.close();
        }

        return logFile;
    }

}