package com.eun.sample.view.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.eun.sample.R
import com.eun.sample.adapter.RecyclerViewDecoration
import com.eun.sample.adapter.SearchRecyclerAdapter
import com.eun.sample.common.CommonVariable.Companion.KEYWORD
import com.eun.sample.common.CommonVariable.Companion.RESPONSE
import com.eun.sample.databinding.FragmentItemListBinding
import com.eun.sample.model.Item
import com.eun.sample.model.SearchResult
import com.eun.sample.util.LogManager
import com.eun.sample.view.BasisActivity
import java.util.*


class SearchListFragment : Fragment() {
    private var mActivity: BasisActivity? = null
    private var mKeyword: String? = null
    private var mResponse: String? = null

    private var mFragmentBinding: FragmentItemListBinding? = null
    private val binding get() = mFragmentBinding!!

    override fun onCreateView(
        inflater: LayoutInflater ,
        container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View {
        mFragmentBinding = FragmentItemListBinding.inflate(inflater , container , false)
        val mRootView = binding.root

        if (activity != null) {
            mActivity = activity as BasisActivity
        }

        if (arguments != null) {
            mKeyword = requireArguments().getString(KEYWORD)
            val mSearchResult = requireArguments().getSerializable(RESPONSE) as SearchResult
            LogManager.log(this.javaClass.toString() , "mResponse=$mResponse")

            // ============================
            // ==== 검색 리스트 보여주기 ====
            // ============================
            showContentsList(mKeyword!! , mSearchResult)
        }


        return mRootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mFragmentBinding = null
    }

    /**
     * Github 검색 리스트 화면에 보이기
     */
    private fun showContentsList(keyword: String , searchResult: SearchResult) {

        // =============================
        // ==== 검색 리스트 조회하기 ====
        // =============================
        val mResultList: ArrayList<Item>? = searchResult.items

        // ====================================
        // ==== 검색 리스트 목록이 없는 경우 ====
        // ===================================
        if (mResultList?.isEmpty()!!) {
            mActivity!!.showDialog(resources.getString(R.string.message_failed_get_search_list))

            // ====================================
            // ==== 검색 리스트 목록이 있는 경우 ====
            // ====================================
        } else {

            val mAdapter = SearchRecyclerAdapter(mResultList , keyword)
            mAdapter.setOnItemClickListener(object : SearchRecyclerAdapter.OnItemClickListener {
                override fun onItemClick(view: View , position: Int , item: Item?) {

                    val mBrowserIntent =
                        Intent(Intent.ACTION_VIEW , Uri.parse(item!!.repository!!.htmlUrl))
                    startActivity(mBrowserIntent)
                }
            })
            binding.itemList.adapter = mAdapter
            binding.itemList.addItemDecoration(RecyclerViewDecoration(20))
        }
    }
}