package com.mindvalley.bean


import com.google.gson.annotations.SerializedName

data class ChannelSectionBean(
    @SerializedName("data")
    var `data`: Data = Data()
) {
    data class Data(
        @SerializedName("channels")
        var channels: ArrayList<Channel> = ArrayList()
    ) {
        data class Channel(
            @SerializedName("coverAsset")
            var coverAsset: CoverAsset = CoverAsset(),
            @SerializedName("iconAsset")
            var iconAsset: IconAsset = IconAsset(),
            @SerializedName("id")
            var id: String = "",
            @SerializedName("latestMedia")
            var latestMedia: ArrayList<LatestMedia> = ArrayList(),
            @SerializedName("mediaCount")
            var mediaCount: Int = 0,
            @SerializedName("series")
            var series: ArrayList<Sery> = ArrayList(),
            @SerializedName("slug")
            var slug: String = "",
            @SerializedName("title")
            var title: String = ""
        ) {
            data class CoverAsset(
                @SerializedName("url")
                var url: String = ""
            )

            data class IconAsset(
                @SerializedName("thumbnailUrl")
                var thumbnailUrl: String = "",
                @SerializedName("url")
                var url: String = ""
            )

            data class LatestMedia(
                @SerializedName("coverAsset")
                var coverAsset: CoverAsset = CoverAsset(),
                @SerializedName("title")
                var title: String = "",
                @SerializedName("type")
                var type: String = ""
            ) {
                data class CoverAsset(
                    @SerializedName("url")
                    var url: String = ""
                )
            }

            data class Sery(
                @SerializedName("coverAsset")
                var coverAsset: CoverAsset = CoverAsset(),
                @SerializedName("id")
                var id: String = "",
                @SerializedName("title")
                var title: String = ""
            ) {
                data class CoverAsset(
                    @SerializedName("url")
                    var url: String = ""
                )
            }
        }
    }
}