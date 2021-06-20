package com.eun.sample.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Item : Serializable {
    @SerializedName("name")
    var name: String? = null

    @SerializedName("path")
    var path: String? = null

    @SerializedName("sha")
    var sha: String? = null

    @SerializedName("url")
    var url: String? = null

    @SerializedName("git_url")
    var gitUrl: String? = null

    @SerializedName("html_url")
    var htmlUrl: String? = null

    @SerializedName("repository")
    var repository: Repository? = null

    @SerializedName("score")
    var score = 0.0

}