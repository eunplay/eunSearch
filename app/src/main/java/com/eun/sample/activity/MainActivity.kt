package com.eun.sample.activity


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.eun.sample.R
import com.eun.sample.activity.fragment.SearchListFragment
import com.eun.sample.databinding.ActivityMainBinding


/**
 * 채은 샘플 메인 액티비티
 */
class MainActivity : BasisActivity() {

    private lateinit var mBinding: ActivityMainBinding

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

        permissionCheck()
    }

    /**
     * 앱 권한 체크
     */
    private fun permissionCheck() {
        if (ContextCompat.checkSelfPermission(
                this ,
                Manifest.permission.READ_PHONE_STATE
            ) != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(
                this ,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this ,
                    Manifest.permission.READ_PHONE_STATE
                )
            ) {

                // 초기 권한 요청시 유저가 권한 거부를 하였을때
                showDialog("'READ_PHONE_STATE' and 'WRITE_EXTERNAL_STORAGE'is mandatory permission for use Application.")

                ActivityCompat.requestPermissions(
                    this ,
                    arrayOf(
                        Manifest.permission.READ_PHONE_STATE ,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) ,
                    mAppPermissionState
                )
            } else {

                // 처음 권한 요청시
                ActivityCompat.requestPermissions(
                    this ,
                    arrayOf(
                        Manifest.permission.READ_PHONE_STATE ,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) ,
                    mAppPermissionState
                )
            }
        }
    }

    /**
     * 클릭 이벤트 리스너
     *
     * @param view 클릭 이벤트 View
     */
    fun btnClick(view: View) {
        val intent: Intent
        when (view.id) {
            R.id.bt_search -> {
                showDialog("해당 앱의 라이선스 정보는 아래와 같습니다.\n\n1234}\n\n위 라이선스 정보는 정식 라이선스를 발급하기 위한 앱 정보로써 한국전자인증 담당자에게 전달하여 정식 라이선스 발급을 요청해 주세요.")

                val mBundle = Bundle()
                val mFragment = SearchListFragment()

                mFragment.arguments = mBundle

                val mFragmentManager = supportFragmentManager
                val mTransaction = mFragmentManager.beginTransaction()
                mTransaction.replace(R.id.fragment_layout , mFragment)
                mTransaction.commitAllowingStateLoss()
            }

            R.id.cert_list_view -> {

                val mBundle = Bundle()
                val mFragment = SearchListFragment()

                mFragment.arguments = mBundle

                val mFragmentManager = supportFragmentManager
                val mTransaction = mFragmentManager.beginTransaction()
                mTransaction.replace(R.id.fragment_layout , mFragment)
                mTransaction.commitAllowingStateLoss()
            }
        }
    }
}
