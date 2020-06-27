package com.mindvalley.bean


import com.google.gson.annotations.SerializedName

data class ChannelsBean(
    @SerializedName("data")
    var `data`: Data = Data()
) {
    data class Data(
        @SerializedName("media")
        var media: ArrayList<Media> = ArrayList()
    ) {
        data class Media(
            @SerializedName("channel")
            var channel: Channel = Channel(),
            @SerializedName("coverAsset")
            var coverAsset: CoverAsset = CoverAsset(),
            @SerializedName("title")
            var title: String = "",
            @SerializedName("type")
            var type: String = ""
        ) {
            data class Channel(
                @SerializedName("title")
                var title: String = ""
            )

            data class CoverAsset(
                @SerializedName("url")
                var url: String = ""
            )
        }
    }
}