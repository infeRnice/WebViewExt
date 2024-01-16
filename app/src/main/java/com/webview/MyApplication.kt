package com.webview

import android.app.Application
import android.content.Context
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.webview.network.DeepLinkHandler

class MyApplication : Application() {

    private lateinit var deepLinkHandler: DeepLinkHandler

    override fun onCreate() {
        super.onCreate()
        deepLinkHandler = DeepLinkHandler(this)

        val appsFlyerConversionListener = object : AppsFlyerConversionListener {
            override fun onConversionDataSuccess(data: Map<String, Any>) {
                val companyName = data["company_name"] as? String
                val status = data["af_status"] as? String
                val mediaSource = data["media_source"] as? String
                val campaign = data["campaign"] as? String

                val isNonOrganic =
                    status == "Non-organic" && (mediaSource == "Google" || mediaSource == "Facebook" || mediaSource == "Instagram" || mediaSource == "TikTok")

                if (isNonOrganic) {
                    //save status
                    saveUserStatus("Non-organic")
                } else {
                    //organic user logic
                    saveUserStatus("Organic")
                }
            }

            override fun onConversionDataFail(errorMessage: String) {
                // Обработка ошибок атрибуции
            }

            override fun onAppOpenAttribution(data: Map<String, String>) {
                // Данные при открытии приложения
                val deepLink = data["deeplink_value"]
                //link processing
                deepLinkHandler.handleDeeplink(deepLink)
            }

            override fun onAttributionFailure(errorMessage: String) {
                // Ошибка атрибуции

            }
        }
        AppsFlyerLib.getInstance().setDebugLog(true)
        AppsFlyerLib.getInstance().init("nvTgEzEo2kc6WmPwciMdpa", appsFlyerConversionListener, this)
        AppsFlyerLib.getInstance().start(this)
    }

    private fun saveUserStatus(status: String?) {
        status?.let {
            val sharedPref = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putString("user_status", status)
                apply()
            }
        }
    }

    private fun saveCompanyName(companyName: String?) {
        companyName?.let {
            val sharedPref = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putString("s1_s2_s3_s4_s5_s6", companyName)
                apply()
            }
        }
    }
}