package com.pureblacksoft.gradinfo.function

import android.util.Log

class LogFun
{
    companion object {
        fun logDataI(msg: String) {
            Log.i("Data", msg)
        }

        fun logDataE(msg: String) {
            Log.e("Data", msg)
        }

        fun logTestD(msg: String) {
            Log.d("Test", msg)
        }
    }
}