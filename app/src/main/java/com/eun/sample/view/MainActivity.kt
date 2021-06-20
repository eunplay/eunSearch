package com.eun.sample.view


import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.eun.sample.R
import com.eun.sample.common.CommonVariable.Companion.ERROR
import com.eun.sample.common.CommonVariable.Companion.KEYWORD
import com.eun.sample.common.CommonVariable.Companion.RESPONSE
import com.eun.sample.common.CommonVariable.Companion.SEARCH_REQUEST_EXCEPTION
import com.eun.sample.common.CommonVariable.Companion.SEARCH_REQUEST_FAILED
import com.eun.sample.common.CommonVariable.Companion.SEARCH_REQUEST_NETWORK_FAILED
import com.eun.sample.common.CommonVariable.Companion.SEARCH_REQUEST_SUCCESS
import com.eun.sample.databinding.ActivityMainBinding
import com.eun.sample.model.SearchResult
import com.eun.sample.task.SearchRequestTask
import com.eun.sample.util.LogManager
import com.eun.sample.view.fragment.SearchListFragment


/**
 * 채은 샘플 메인 액티비티
 */
class MainActivity : BasisActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private var mKeyword: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)

        val mRootView = mBinding.root
        setContentView(mRootView)

        // ==================
        // ==== 버전 정보 ====
        // ==================
        try {
            val mPackageInfo = packageManager.getPackageInfo(this.packageName , 0)
            val mFinalText = "버전 정보 v" + mPackageInfo.versionName
            mBinding.tvVersion.text = mFinalText
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        mBinding.etKeyword.setOnKeyListener { _ , keyCode , event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KEYCODE_ENTER) {

                onSearchPrepare()
            }

            true
        }
    }

    /**
     * 클릭 이벤트 리스너
     *
     * @param view 클릭 이벤트 View
     */
    fun btnClick(view: View) {
        when (view.id) {
            R.id.bt_search -> {

                onSearchPrepare()
            }
        }
    }

    /**
     * Search Process 를 진행하기 전 UI 로직
     */
    private fun onSearchPrepare() {
        val mEditTable = mBinding.etKeyword.text

        // ==========================
        // ==== 키워드가 없는 경우 ====
        // ==========================
        if (mEditTable.isEmpty()) {
            showDialog(resources.getString(R.string.message_enter_your_keyword))

            // ==========================
            // ==== 키워드가 있는 경우 ====
            // ==========================
        } else {

            showProgressDialog()

            mKeyword = mEditTable.toString()

            // ===============================
            // ==== Github Search API 연동 ====
            // ===============================
            onSearchGithub(mKeyword!!)
        }
    }

    /**
     * Github Search API 연동 TASK
     */
    private fun onSearchGithub(keyword: String) {
        val mSearchRequestTask =
            SearchRequestTask(MainActivity@ this , keyword , mSearchResultListener)
        mSearchRequestTask.execute()
    }

    private val mSearchResultListener =
        Handler(object : Handler.Callback {
            override fun handleMessage(message: Message): Boolean {

                // ============================================================
                // ==== View 처리(프로그레스바 다이얼로그 닫기, 키보드 내리기) ====
                // ============================================================
                closeProgressDialog()

                val imm: InputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(mBinding.etKeyword.windowToken , 0)


                when (message.what) {
                    SEARCH_REQUEST_SUCCESS -> {
                        LogManager.log(this.javaClass.toString() , "SEARCH_REQUEST_SUCCESS")

                        val mSearchResult = message.data.getSerializable(RESPONSE) as SearchResult

                        // =========================================
                        // ==== 키워드 검색한 결과 텍스트 보여주기 ====
                        // =========================================
                        mBinding.tvKeywordView.text =
                            "'$mKeyword' 검색한 결과 (${mSearchResult.totalCount})"

                        // =====================================================
                        // ==== 검색 결과를 화면에 보여주기 위해 Fragment 이동 ====
                        // =====================================================
                        val mBundle = Bundle()
                        mBundle.putString(KEYWORD , mKeyword)
                        mBundle.putSerializable(RESPONSE , mSearchResult)

                        val mFragment = SearchListFragment()
                        mFragment.arguments = mBundle

                        val mFragmentManager = supportFragmentManager
                        val mTransaction = mFragmentManager.beginTransaction()
                        mTransaction.replace(R.id.fragment_layout , mFragment)
                        mTransaction.commitAllowingStateLoss()

                    }
                    SEARCH_REQUEST_FAILED -> {
                        LogManager.log(this.javaClass.toString() , "SEARCH_REQUEST_FAILED")
                        showDialog("SEARCH_REQUEST_FAILED")

                    }
                    SEARCH_REQUEST_NETWORK_FAILED -> {
                        LogManager.log(this.javaClass.toString() , "SEARCH_REQUEST_NETWORK_FAILED")
                        showDialog("SEARCH_REQUEST_NETWORK_FAILED")
                    }

                    SEARCH_REQUEST_EXCEPTION -> {
                        LogManager.log(this.javaClass.toString() , "SEARCH_REQUEST_EXCEPTION")
                        val mErrorMsg = message.data.getString(ERROR)
                        showDialog("SEARCH_REQUEST_EXCEPTION ($mErrorMsg)")
                    }
                }
                return false
            }
        })
}
