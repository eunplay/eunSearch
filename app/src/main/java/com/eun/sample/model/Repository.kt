package com.eun.sample.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Repository : Serializable {
    @SerializedName("id")
    var id = 0

    @SerializedName("node_id")
    var nodeId: String? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("full_name")
    var fullName: String? = null

    @SerializedName("html_url")
    var htmlUrl: String? = null

    @SerializedName("description")
    var description: String? = null

    @SerializedName("fork")
    var isFork = false

    @SerializedName("url")
    var url: String? = null

    @SerializedName("forks_url")
    var forksUrl: String? = null

    @SerializedName("downloads_url")
    var downloadsUrl: String? = null

}