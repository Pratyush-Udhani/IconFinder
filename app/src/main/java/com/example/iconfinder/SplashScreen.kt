package com.example.iconfinder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.iconfinder.base.BaseActivity
import com.example.iconfinder.home.HomeActivity
import com.example.iconfinder.utils.SPLASH_SCREEN_TIMEOUT
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreen : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        setUp()
    }

    private fun setUp() {
        splashBackground.animate().alpha(1f).duration = SPLASH_SCREEN_TIMEOUT
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(HomeActivity.newInstance(this))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left)
        }, SPLASH_SCREEN_TIMEOUT)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}