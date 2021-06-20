package com.eun.sample.task

import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.webkit.CookieManager
import com.eun.sample.common.CommonVariable
import com.eun.sample.common.CommonVariable.Companion.ERROR
import com.eun.sample.common.CommonVariable.Companion.RESPONSE
import com.eun.sample.model.SearchResult
import com.eun.sample.util.LogManager
import com.eun.sample.view.BasisActivity
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException
import java.util.*

class SearchRequestTask internal constructor(
    activity: BasisActivity ,
    keyword: String? ,
    mHandler: Handler
) : AsyncTask<String? , String? , String?>() , Interceptor {

    private val mHandler: Handler
    private var mClient: OkHttpClient? = null
    private var mRequest: Request? = null
    private var mResponse: Response? = null
    private var mKeyword: String? = null
    private var mActivity: BasisActivity? = null

    override fun doInBackground(vararg params: String?): String? {

        mClient = OkHttpClient.Builder().cookieJar(object : CookieJar {

            override fun saveFromResponse(url: HttpUrl , cookies: List<Cookie>) {
                val cookieManager = CookieManager.getInstance()
                for (cookie in cookies) {
                    cookieManager.setCookie(url.toString() , cookie.toString())
                }
            }

            override fun loadForRequest(url: HttpUrl): List<Cookie> {
                val cookieManager = CookieManager.getInstance()
                val cookies: MutableList<Cookie> = ArrayList()
                if (cookieManager.getCookie(url.toString()) != null) {
                    val splitCookies =
                        cookieManager.getCookie(url.toString()).split("[,;]").toTypedArray()
                    for (splitCookie in splitCookies) {
                        cookies.add(Cookie.parse(url , splitCookie.trim { it <= ' ' })!!)
                    }
                }
                return cookies
            }
        }).build()

        // =========================
        // ==== 키워드 검색 요청 ====
        // =========================
        requestSearchInfo()

        return null
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        Log.d(this.javaClass.toString() , "intercept")
        val mRequest = Request.Builder() //                .url(SEARCH_URL)
            .addHeader(
                CommonVariable.AUTHORIZATION ,
                CommonVariable.ACCESS_TOKEN
            )
            .addHeader(CommonVariable.ACCEPT , CommonVariable.GITHUB_V3)
            .build()
        return chain.proceed(mRequest)
    }

    /**
     * 키워드 검색 요청
     */
    private fun requestSearchInfo(): Boolean {
        return try {

            // =====================
            // ==== OK HTTP GET ====
            // =====================
            val mURL = CommonVariable.SEARCH_URL
            val mHttpUrl = HttpUrl.Builder()
                .scheme("https")
                .host(mURL)
                .addPathSegment("search")
                .addPathSegment("code")
                .addQueryParameter("q" , "filename:$mKeyword+org:apache")
                .build()

            mRequest = Request.Builder().url(mHttpUrl).build()

            mResponse = mClient!!.newCall(mRequest!!).execute()

            // ============================
            // ==== 정상적인 통신인 경우 ====
            // ============================
            if (mResponse!!.isSuccessful) {

                // =======================================
                // ==== Response Body 데이터가 있는 경우 ===
                // =======================================
                if (mResponse!!.body != null) {
                    val mResult = mResponse!!.body!!.string()
                    Log.d(this.javaClass.toString() , "mResult=$mResult")

                    // =========================================
                    // ==== JSON 파싱 후 Model 에 데이터 전달 ====
                    // =========================================
                    val mGson = GsonBuilder().create()
                    val mSearchResult = mGson.fromJson(mResult , SearchResult::class.java)

                    val mBundle = Bundle()
                    mBundle.putSerializable(RESPONSE , mSearchResult)

                    val message = Message()
                    message.what = CommonVariable.SEARCH_REQUEST_SUCCESS
                    message.data = mBundle
                    mHandler.sendMessage(message)
                    true

                    // =======================================
                    // ==== Response Body 데이터가 없는 경우 ===
                    // =======================================
                } else {
                    val message = Message()
                    message.what = CommonVariable.SEARCH_REQUEST_NETWORK_BODY_EMPTY
                    mHandler.sendMessage(message)
                    false
                }

                // =================================
                // ==== 정상적인 통신이 아닌 경우 ====
                // ================================
            } else {
                val message = Message()
                message.what = CommonVariable.SEARCH_REQUEST_NETWORK_FAILED
                mHandler.sendMessage(message)
                false
            }
        } catch (e: IOException) {
            LogManager.error(this.javaClass.toString() , "IOException occurred (" + e.message + ")")

            val mBundle = Bundle()
            mBundle.putString(ERROR , e.message)

            val message = Message()
            message.what = CommonVariable.SEARCH_REQUEST_EXCEPTION
            message.data = mBundle
            mHandler.sendMessage(message)
            false
        }
    }

    init {
        mKeyword = keyword
        mActivity = activity

        this.mHandler = mHandler
    }
}