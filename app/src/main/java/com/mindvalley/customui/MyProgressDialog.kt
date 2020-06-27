package com.mindvalley.customui

import android.app.AlertDialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import com.mindvalley.R

class MyProgressDialog(context: Context) : AlertDialog(context) {
    init {
        window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
    }

    override fun show() {
        super.show()
        setContentView(R.layout.progress_dialog)
    }
}