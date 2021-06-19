package com.eun.sample.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eun.sample.databinding.AdapterCertBinding

/**
 * 검색 리스트를 위한 Custom Adapter
 */
class SearchRecyclerAdapter(private val searchResult: List<String>) :
    RecyclerView.Adapter<SearchRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup , viewType: Int): ViewHolder {
        val mBinding =
            AdapterCertBinding.inflate(LayoutInflater.from(viewGroup.context) , viewGroup , false)
        return ViewHolder(mBinding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder , position: Int) {
        val mSearchResult = searchResult[position]
        viewHolder.setData(mSearchResult)
    }

    override fun getItemCount() = searchResult.size

    inner class ViewHolder(private val binding: AdapterCertBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(searchResult: String) {
            binding.typeText.text = searchResult
            binding.issuerText.text = "2"
            binding.userText.text = "3"
            binding.expirationDateText.text = "4"

            val mPosition = adapterPosition
            if (mPosition != RecyclerView.NO_POSITION) {
                itemView.setOnClickListener {
                    mListener?.onItemClick(itemView , mPosition , searchResult)
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(view: View , position: Int , searchResult: String)
    }

    private var mListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.mListener = listener
    }

}