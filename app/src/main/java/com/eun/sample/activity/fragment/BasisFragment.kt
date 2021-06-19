package com.eun.sample.activity.fragment

import android.content.DialogInterface
import androidx.fragment.app.Fragment


/**
 * 공통 Fragment
 */

open class BasisFragment : Fragment() {
    var mPopupDlg: DialogInterface? = null

//    protected open fun alertDialogShow(title: Int , msg: String , status: Boolean) {
//
//        val inflater = LayoutInflater.from(activity)
//        val mView = inflater.inflate(R.layout.dialog_confirm , null)
//        val mTitleTextView = mView.findViewById<TextView>(R.id.dialog_title)
//        val mContentTextView = mView.findViewById<TextView>(R.id.dialog_content_msg)
//        val mConfirmView = mView.findViewById<Button>(R.id.dialog_commit_btn)
//
//        mTitleTextView.setText(title)
//        mContentTextView.text = msg
//
//        val mBuilder = AlertDialog.Builder(activity)
//        mBuilder.setCancelable(false)
//        mBuilder.setView(mView)
//        mBuilder.create()
//        mPopupDlg = mBuilder.show()
//
//        val mDialogInterface = mPopupDlg
//
//        mConfirmView.setOnClickListener {
//            mDialogInterface!!.dismiss()
//
//            if (status) {
//                val intent = Intent(activity , BioMenuActivity::class.java)
//                startActivity(intent)
//                activity!!.finish()
//                activity!!.overridePendingTransition(android.R.anim.fade_in , android.R.anim.fade_out)
//
//            }
//        }
//    }
//
//    /**
//     * 디자인 팝업 메시지
//     *
//     * @param title            팝업 메시지 타이틀
//     * @param msg              파업 메시지
//     * @param okListener    '확인' 버튼 이벤트 리스너
//     * @param noListener    '취소' 버튼 이벤트 리스너
//     */
//    protected fun alertDialogShow(title: Int , msg: String , okListener: View.OnClickListener , noListener: View.OnClickListener?) {
//
//        val inflater = LayoutInflater.from(activity)
//
//        val mView: View?
//        val mTitleTextView: TextView?
//        val mContentTextView: TextView?
//        val mConfirmBtn: Button?
//        val mCancelBtn: Button?
//
//        val mBuilder = AlertDialog.Builder(activity)
//
//        if (noListener != null) {
//
//            mView = inflater.inflate(R.layout.dialog_option , null)
//            mTitleTextView = mView!!.findViewById(R.id.dialog_title)
//            mContentTextView = mView.findViewById(R.id.dialog_content_msg)
//            mConfirmBtn = mView.findViewById(R.id.dialog_commit_btn)
//            mCancelBtn = mView.findViewById(R.id.dialog_cancel_btn)
//
//            mConfirmBtn!!.setOnClickListener(okListener)
//            mCancelBtn!!.setOnClickListener(noListener)
//
//        } else {
//            mView = inflater.inflate(R.layout.dialog_confirm , null)
//            mTitleTextView = mView!!.findViewById(R.id.dialog_title)
//            mContentTextView = mView.findViewById(R.id.dialog_content_msg)
//            mConfirmBtn = mView.findViewById(R.id.dialog_commit_btn)
//
//            mConfirmBtn!!.setOnClickListener(okListener)
//        }
//
//
//        mTitleTextView!!.setText(title)
//        mContentTextView!!.text = msg
//
//        mBuilder.setView(mView)
//        mBuilder.setCancelable(false)
//        mBuilder.create()
//
//        mPopupDlg = mBuilder.show()
//    }
//
//    /**
//     * 디자인 팝업 메시지
//     *
//     * @param title            팝업 메시지 타이틀
//     * @param msg              파업 메시지
//     * @param okListener    '확인' 버튼 이벤트 리스너
//     * @param noListener    '취소' 버튼 이벤트 리스너
//     */
//    protected fun alertLongTextDialogShow(title: Int , msg: String , subMsg: String? , okListener: View.OnClickListener , noListener: View.OnClickListener?) {
//
//        val inflater = LayoutInflater.from(activity)
//
//        val mView: View?
//        val mTitleTextView: TextView?
//        val mContentTextView: TextView?
//        val mContentSubTextView: TextView?
//        val mConfirmBtn: Button?
//        val mCancelBtn: Button?
//
//        val builder = AlertDialog.Builder(activity)
//
//        if (noListener != null) {
//
//            mView = inflater.inflate(R.layout.dialog_option , null)
//            mTitleTextView = mView!!.findViewById(R.id.dialog_title)
//            mContentTextView = mView.findViewById(R.id.dialog_content_msg)
//            mContentSubTextView = mView.findViewById(R.id.dialog_content_sub_msg)
//            mConfirmBtn = mView.findViewById(R.id.dialog_commit_btn)
//            mCancelBtn = mView.findViewById(R.id.dialog_cancel_btn)
//
//            mConfirmBtn!!.setOnClickListener(okListener)
//            mCancelBtn!!.setOnClickListener(noListener)
//
//        } else {
//            mView = inflater.inflate(R.layout.dialog_confirm , null)
//            mTitleTextView = mView!!.findViewById(R.id.dialog_title)
//            mContentTextView = mView.findViewById(R.id.dialog_content_msg)
//            mContentSubTextView = mView.findViewById(R.id.dialog_content_sub_msg)
//            mConfirmBtn = mView.findViewById(R.id.dialog_commit_btn)
//
//            mConfirmBtn!!.setOnClickListener(okListener)
//        }
//
//
//        mTitleTextView!!.setText(title)
//        mContentTextView!!.text = msg
//
//        if (mContentSubTextView != null) {
//            mContentSubTextView.text = subMsg
//        }
//
//        builder.setView(mView)
//        builder.setCancelable(false)
//        builder.create()
//
//        mPopupDlg = builder.show()
//    }

    fun closeDialog() {
        // LogManager.log("BasisFragment" , "closeDialog")

        if (mPopupDlg != null) {
            mPopupDlg!!.dismiss()
        }
    }


    override fun onDestroy() {
        // LogManager.log("BasisFragment" , "onDestroy")

        closeDialog()

        super.onDestroy()
    }
}
