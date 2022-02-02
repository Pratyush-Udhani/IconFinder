package com.example.iconfinder.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.iconfinder.BuildConfig.BASE_URL
import com.example.iconfinder.api.ApiClient
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

private const val REQUEST_CODE = 2
private val interceptor = HttpLoggingInterceptor()
private val httpClient = OkHttpClient.Builder().addInterceptor(interceptor)

val retrofitClient: ApiClient by lazy {
    interceptor.level = HttpLoggingInterceptor.Level.BODY

    val build = httpClient.connectTimeout(100, TimeUnit.SECONDS)
        .readTimeout(100, TimeUnit.SECONDS)
        .build()

    val client = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(build)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiClient::class.java)
    client
}

fun View.makeGone(){
    visibility = View.GONE
}

fun View.makeVisible(){
    visibility = View.VISIBLE
}
fun askForPermission(activity: Activity) {
    ActivityCompat.requestPermissions(
        activity,
        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
        REQUEST_CODE
    )
}

fun isPermissionGranted(context: Context): Boolean {
    return (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED)
}

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}


fun downloadImage(context: Context, downloadUrl: String) {
    val intent = Intent(context, DownloadService::class.java)
    intent.putExtra("url", downloadUrl)
    context.startService(intent)
}

