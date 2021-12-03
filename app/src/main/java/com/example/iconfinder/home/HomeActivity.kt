package com.example.iconfinder.home

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.iconfinder.R
import com.example.iconfinder.base.BaseActivity

class HomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        init()
    }

    private fun init() {
        setUpFragment()
        setUpListeners()
    }

    private fun setUpListeners() {

    }

    private fun setUpFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.homeContainer, HomeFragment.newInstance())
        fragmentTransaction.commit()
    }

    companion object {
        fun newInstance(context: Context) = Intent(context, HomeActivity::class.java)
    }
}