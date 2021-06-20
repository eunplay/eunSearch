package com.eun.sample.common

/**
 * 상수값 정의한 샘플
 */

class CommonVariable {

    companion object {

        const val RESPONSE = "response"
        const val ERROR = "error"
        const val KEYWORD = "keyword"
        const val AUTHORIZATION: String = "Authorization"
        const val ACCEPT: String = "Accept"
        const val GITHUB_V3: String = "application/vnd.github.v3+json"
        const val ACCESS_TOKEN: String = "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"

        // const val SEARCH_URL = "https://api.github.com/search/code"
        const val SEARCH_URL = "api.github.com"

        // ============================
        // ==== Network StatusCode ====
        // ============================
        const val SEARCH_REQUEST_SUCCESS = 200
        const val SEARCH_REQUEST_FAILED = 201
        const val SEARCH_REQUEST_NETWORK_FAILED = 301
        const val SEARCH_REQUEST_NETWORK_BODY_EMPTY = 302
        const val SEARCH_REQUEST_EXCEPTION = 303

    }
}
