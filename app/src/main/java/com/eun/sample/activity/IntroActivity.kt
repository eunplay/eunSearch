package com.eun.sample.activity

import android.Manifest.permission.READ_PHONE_STATE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.view.View
//import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.eun.sample.R
import com.eun.sample.util.LogManager

class IntroActivity : AppCompatActivity() /*, View.OnClickListener*/ {
    private val mAppPermissionState = 1
    private val mTagName = IntroActivity::class.java.simpleName

    //    private var mConfirmView: Button? = null
    private var mAlertDialog: AlertDialog? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_intro)

//        mConfirmView = findViewById(R.id.bt_permission_confirm)
//        mConfirmView!!.setOnClickListener(this)

        permissionCheck()
    }

    /**
     * 앱 권한 체크 ACCESS_WIFI_STATE
     */
    private fun permissionCheck() {

        if (ActivityCompat.checkSelfPermission(this , READ_PHONE_STATE) != PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this ,
                WRITE_EXTERNAL_STORAGE
            ) != PERMISSION_GRANTED
        ) {

            LogManager.log(mTagName , "Permission not granted")

        } else {
            initialize()
        }

    }

    /**
     * 앱 권한 체크 ACCESS_WIFI_STATE
     */
    private fun showPermission() {
        if (ContextCompat.checkSelfPermission(this , READ_PHONE_STATE) != PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(
                this ,
                WRITE_EXTERNAL_STORAGE
            ) != PERMISSION_GRANTED
        ) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this , READ_PHONE_STATE)
                && ActivityCompat.shouldShowRequestPermissionRationale(
                    this ,
                    WRITE_EXTERNAL_STORAGE
                )
            ) {

                ActivityCompat.requestPermissions(
                    this , arrayOf(READ_PHONE_STATE , WRITE_EXTERNAL_STORAGE) ,
                    mAppPermissionState
                )
            } else {

                ActivityCompat.requestPermissions(
                    this , arrayOf(READ_PHONE_STATE , WRITE_EXTERNAL_STORAGE) ,
                    mAppPermissionState
                )
            }

        } else {
            initialize()
        }
    }

    /**
     * 앱 권한 체크 이후 로직 처리
     *
     * @param requestCode  requestCode
     * @param permissions  permission List
     * @param grantResults grantResult List
     */
    override fun onRequestPermissionsResult(
        requestCode: Int ,
        permissions: Array<String> ,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode , permissions , grantResults)

        if (requestCode == mAppPermissionState) {
            if (grantResults.size > 1 && grantResults[0] == PERMISSION_GRANTED) {
                initialize()

            } else {

                showDialog(
                    "아래 해당 권한은 한국전자인증 자체인증 Lib 샘플 어플리케이션을 사용하기에 필수 권한입니다.\n\n반드시 [확인] 버튼을 눌러 권한을 허용해주세요." ,
                    resources.getString(R.string.message_confirm) ,
                    DialogInterface.OnClickListener { dialogInterface , _ ->
                        dialogInterface.dismiss()

                        ActivityCompat.requestPermissions(
                            this ,
                            arrayOf(READ_PHONE_STATE , WRITE_EXTERNAL_STORAGE) ,
                            mAppPermissionState
                        )
                    } ,
                    resources.getString(R.string.message_cancel) ,
                    DialogInterface.OnClickListener { dialogInterface , _ ->
                        dialogInterface.dismiss()
                    }
                )

            }
        }
    }

    /**
     * ACTIVITY 초기화
     */
    private fun initialize() {

        try {
            val intent = Intent(this , MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
            finish()

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun showDialog(
        msg: String ,
        positiveText: String ,
        positiveListener: DialogInterface.OnClickListener? ,
        negativeText: String ,
        negativeListener: DialogInterface.OnClickListener?
    ) {

        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.notification)
        builder.setMessage(msg)
        builder.setCancelable(false)

        if (positiveListener != null) {
            builder.setPositiveButton(positiveText , positiveListener)
        }
        if (negativeListener != null) {
            builder.setNegativeButton(negativeText , negativeListener)
        }

        mAlertDialog = builder.create()
        mAlertDialog!!.show()
    }

    /**
     * Called when the user clicks the button
     *
     * @param view click view
     */
    fun btnClick(view: View) {
        // Do something in response to button click
        if (view.id == R.id.bt_permission_confirm) {
            showPermission()
        }
    }
}