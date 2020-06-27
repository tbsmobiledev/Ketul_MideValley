package com.stocktakeonline.backend

interface ResponseListener {
    // API Response Listener
    fun onResponse(tag: String, result: Int, obj: Any)
}
