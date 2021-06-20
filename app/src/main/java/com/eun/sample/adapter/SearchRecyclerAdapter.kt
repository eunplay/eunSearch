package com.eun.sample.adapter

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eun.sample.databinding.AdapterItemBinding
import com.eun.sample.model.Item
import java.util.*

/**
 * 검색 리스트를 위한 Custom Adapter
 */
class SearchRecyclerAdapter(
    private val itemList: ArrayList<Item>? ,
    private val keyword: String
) : RecyclerView.Adapter<SearchRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup , viewType: Int): ViewHolder {
        val mBinding =
            AdapterItemBinding.inflate(LayoutInflater.from(viewGroup.context) , viewGroup , false)
        return ViewHolder(mBinding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder , position: Int) {
        val mItemList = itemList?.get(position)
        viewHolder.setData(mItemList , keyword)
    }

    override fun getItemCount(): Int = itemList!!.size

    inner class ViewHolder(private val binding: AdapterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(item: Item? , keyword: String) {

            // ================================================
            // ==== 목록에서 키워드만 다른 색으로 보이게 하기 ====
            // ================================================
            val mKeywordStartIndex =
                item!!.name!!.toLowerCase(Locale.KOREA).indexOf(keyword.toLowerCase(Locale.KOREA))
            if (mKeywordStartIndex > -1) {
                val spannable = SpannableString(item.name)
                spannable.setSpan(
                    ForegroundColorSpan(Color.BLUE) ,
                    mKeywordStartIndex ,
                    mKeywordStartIndex + keyword.length ,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                binding.nameText.text = spannable
            } else {
                binding.nameText.text = item.name
            }
            binding.descriptionText.text = item.repository!!.description
            binding.repositoryText.text = item.repository!!.name
            binding.htmlUrlText.text = item.repository!!.htmlUrl

            val mPosition = adapterPosition
            if (mPosition != RecyclerView.NO_POSITION) {
                itemView.setOnClickListener {
                    mListener?.onItemClick(itemView , mPosition , item)
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(view: View , position: Int , item: Item?)
    }

    private var mListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.mListener = listener
    }

}