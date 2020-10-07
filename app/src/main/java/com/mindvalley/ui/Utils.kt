package com.mindvalley.ui

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.net.ConnectivityManager
import android.widget.TextView
import android.widget.Toast
import com.mindvalley.customui.MyProgressDialog

@Suppress("DEPRECATION")
class Utils {
    companion object {

        private var progressDialog: MyProgressDialog? = null

        /**
         * Progress bar show method
         */
        fun showProgress(context1: Context) {
            if (progressDialog == null) {
                progressDialog = MyProgressDialog(context1)
                progressDialog!!.setCancelable(false)
                progressDialog!!.show()
            }
        }

        /**
         * Progress bar hide method
         */
        fun hideProgress() {
            if (progressDialog != null && progressDialog!!.isShowing) {
                progressDialog!!.dismiss()
                progressDialog!!.cancel()
                progressDialog = null
            }
        }

        /**
         * Check for device online or not
         */
        fun isOnline(context1: Context): Boolean {
            return try {
                val conMgr = context1
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val info = conMgr.activeNetworkInfo
                info != null && info.isConnected
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }

        }

        /**
         * Show toastt message
         */
        fun showToast(context: Context, obj: Any?) {
            try {
                if (obj != null) {
                    val message = getString((obj as String).toString())
                    if (message.isNotEmpty()) {

                        val toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
                        val toastMessage = toast.view?.findViewById<TextView>(android.R.id.message)
                        toastMessage?.setTextColor(Color.WHITE)
                        toastMessage?.setBackgroundColor(Color.TRANSPARENT)
                        val view = toast.view
                        view?.background?.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN)
                        toast.show()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        private fun getString(text: String): String {
            return if (text.equals("null", ignoreCase = true)) {
                ""
            } else text
        }

    }

}