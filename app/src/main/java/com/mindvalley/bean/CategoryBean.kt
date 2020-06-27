package com.mindvalley.bean


import com.google.gson.annotations.SerializedName

data class CategoryBean(
    @SerializedName("data")
    var `data`: Data = Data()
) {
    data class Data(
        @SerializedName("categories")
        var categories: ArrayList<Category> = ArrayList()
    ) {
        data class Category(
            @SerializedName("name")
            var name: String = ""
        )
    }
}