package com.eun.sample.activity.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eun.sample.R
import com.eun.sample.activity.BasisActivity
import com.eun.sample.adapter.RecyclerViewDecoration
import com.eun.sample.adapter.SearchRecyclerAdapter
import com.eun.sample.common.CommonVariable.Companion.OPERATION
import com.eun.sample.databinding.FragmentCertListBinding

class SearchListFragment : BasisFragment() {
    private var mActivity: BasisActivity? = null
    private var mOperation: String? = null

    private var mFragmentBinding: FragmentCertListBinding? = null
    private val binding get() = mFragmentBinding!!

    override fun onCreateView(
        inflater: LayoutInflater ,
        container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View {
        mFragmentBinding = FragmentCertListBinding.inflate(inflater , container , false)
        val mRootView = binding.root

        if (activity != null) {
            mActivity = activity as BasisActivity
        }

        if (arguments != null) {
            mOperation = arguments!!.getString(OPERATION)
        }

        showContentsList()

        return mRootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mFragmentBinding = null
    }

    /**
     * Github 검색 리스트 화면에 보이기
     */
    private fun showContentsList() {

        // =============================
        // ==== 검색 리스트 조회하기 ====
        // =============================
        val mCertList: List<String>? = listOf("공용" , "개인")


        // ====================================
        // ==== 검색 리스트 목록이 없는 경우 ====
        // ===================================
        if (mCertList?.isEmpty()!!) {
            mActivity!!.showDialog(resources.getString(R.string.message_failed_get_search_list))

            // ====================================
            // ==== 검색 리스트 목록이 있는 경우 ====
            // ====================================
        } else {

            val mAdapter = SearchRecyclerAdapter(mCertList)
            mAdapter.setOnItemClickListener(object : SearchRecyclerAdapter.OnItemClickListener {
                override fun onItemClick(view: View , position: Int , searchResult: String) {


                    val mBundle = Bundle()
                    mBundle.putString(OPERATION , mOperation)

                    val mFragment = SearchListFragment()
                    mFragment.arguments = mBundle

                    val mFragmentManager = mActivity!!.supportFragmentManager
                    val mTransaction = mFragmentManager.beginTransaction()
                    mTransaction.replace(R.id.fragment_layout , mFragment)
                    mTransaction.commitAllowingStateLoss()

                }
            })
            binding.cerList.adapter = mAdapter
            binding.cerList.addItemDecoration(RecyclerViewDecoration(20))
        }
    }
}