package com.example.iconfinder.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun View.makeGone(){
    visibility = View.GONE
}

fun View.makeVisible(){
    visibility = View.VISIBLE
}

fun closeKeyboard(context: Context, currentFocus: View?) {
    if (currentFocus != null) {
        val imm: InputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus.windowToken, 0)
    }
}
