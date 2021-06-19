package com.eun.sample.common

/**
 * 상수값 정의한 샘플
 */

class CommonVariable {

    companion object {

        // =====================
        // ==== 데이터 파라미터 ====
        // =====================
        const val REQUEST_CODE = "requestCode"
        const val RESPONSE = "response"
        const val ERROR = "error"
        const val OPERATION: String = "operation"


        // CC_DEV
        private const val CC_DEV_CA_URL = "10.10.20.16"

        // CC_QA
        private const val CC_QA_CA_URL = "211.192.169.151"

        const val CA_URL = CC_DEV_CA_URL
        const val CA_PORT = 4502
    }
}
