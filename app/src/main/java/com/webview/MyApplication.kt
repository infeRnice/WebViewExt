package com.webview

import android.app.Application
import android.content.Context
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        val appsFlyerConversionListener = object : AppsFlyerConversionListener {
            override fun onConversionDataSuccess(data: Map<String, Any>) {
               val companyName = data["s1_s2_s3_s4_s5_s6"] as? String
                val status = data["af_status"] as? String

                // save companyName to shared preferences
                saveCompanyName(companyName)
            }

            override fun onConversionDataFail(errorMessage: String) {
                // Обработка ошибок атрибуции
            }

            override fun onAppOpenAttribution(data: Map<String, String>) {
                // Данные при открытии приложения
                val deepLink = data["deeplink_value"]
                //link processing
                handleDeeplink(deepLink)
            }

            override fun onAttributionFailure(errorMessage: String) {
                // Ошибка атрибуции

            }

            private fun handleDeeplink(deepLink: String?) {
                deepLink?.let {
                    //processing logic
                }
            }
        }
        AppsFlyerLib.getInstance().setDebugLog(true)
        AppsFlyerLib.getInstance().init("nvTgEzEo2kc6WmPwciMdpa", appsFlyerConversionListener, this)
        AppsFlyerLib.getInstance().start(this)
    }

    private fun saveCompanyName(companyName: String?) {
        companyName?.let {
            val sharedPref = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
            with (sharedPref.edit()) {
                putString("s1_s2_s3_s4_s5_s6", companyName)
                apply()
            }
        }
    }
}