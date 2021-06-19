package com.eun.sample.activity


import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import com.eun.sample.R

/**
 * ACTIVITY 기본이 되는 부모 클래스
 */
open class BasisActivity : AppCompatActivity() {

    val mAppPermissionState = 1

    private var mAlertDialog: AlertDialog? = null
    private var mDialog: DialogInterface? = null


    fun showDialog(msg: String) {
        val mBuilder = AlertDialog.Builder(this)
        mBuilder.setTitle(R.string.notification).setMessage(msg)
            .setPositiveButton(R.string.message_confirm) { dialog , _ -> dialog.dismiss() }

        mAlertDialog = mBuilder.create()
        mAlertDialog!!.show()
    }

    /**
     * Progress Dialog 보이기
     */
    fun showProgressDialog() {

        val mBuilder = AlertDialog.Builder(ContextThemeWrapper(this , R.style.AppTheme))
        mBuilder.setView(R.layout.progress_dialog)
        mBuilder.setCancelable(false)
        mBuilder.create()
        mDialog = mBuilder.show()

    }

    /**
     * Progress Dialog 감추기
     */
    fun closeProgressDialog() {
        if (mDialog != null) {
            mDialog!!.dismiss()
            mDialog = null
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        if (mAlertDialog != null) {
            mAlertDialog!!.dismiss()
        }
    }
}