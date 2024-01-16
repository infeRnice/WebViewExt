package com.webview.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.webview.MyApplication
import com.webview.network.DeepLinkHandler
import com.webview.network.NetworkRepository
import com.webview.network.NetworkRepositoryImpl
import com.webview.network.ServiceBuilder
import com.webview.ui.theme.WebViewTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {

    private lateinit var deepLinkHandler: DeepLinkHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        deepLinkHandler = DeepLinkHandler(this)

        handleIntent(intent)

        val networkRepository = NetworkRepositoryImpl(ServiceBuilder.apiService)

        setContent {
            WebViewTheme {
                MyApp(networkRepository)
            }
        }
    }
    private fun handleIntent(intent: Intent?){
        intent?.data?.let { uri ->
            if (uri.scheme == "webview" && uri.host == "deeplinktest") {
                //pass the diplink to the app
                deepLinkHandler.handleDeeplink(uri.toString())
            }
        }
    }
}

@Composable
fun MyApp(networkRepository: NetworkRepository) {
    val viewModel: MainViewModel = viewModel(factory = MainViewModelFactory(networkRepository))
    var showWebView by remember { mutableStateOf<Boolean?>(null) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        when (showWebView) {
            true -> WebViewScreen()
            false -> PlaceholderScreen()
            null -> SplashScreen { result -> showWebView = result }
        }
    }
}

@Composable
fun SplashScreen(onResult: (Boolean) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Loading...") //SplashScreen
    }
    LaunchedEffect(Unit) {
        val result = fetchNetWorkData()
        onResult(result)
    }
}

suspend fun fetchNetWorkData(): Boolean {
    return withContext(Dispatchers.IO) {
        //запрос на сервер
        true
    }
}

@Composable
fun mainScreen(showWebView: Boolean) {
    if (showWebView) {
        WebViewScreen()
    } else {
        PlaceholderScreen() //заглушка
    }
}

@Composable
fun WebViewScreen() {
    //WebView
}

@Composable
fun PlaceholderScreen() {
    //Заглушка
}
