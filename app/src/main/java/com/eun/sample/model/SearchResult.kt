package com.eun.sample.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

class SearchResult : Serializable {
    @SerializedName("total_count")
    var totalCount = 0

    @SerializedName("incomplete_results")
    var isIncompleteResults = false

    @SerializedName("items")
    var items: ArrayList<Item>? = null

}