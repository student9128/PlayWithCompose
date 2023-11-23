package com.kevin.playwithcompose.base

import androidx.activity.ComponentActivity
import com.kevin.playwithcompose.util.LogUtils.logD
import com.kevin.playwithcompose.util.LogUtils.logE
import com.kevin.playwithcompose.util.LogUtils.logI
import com.kevin.playwithcompose.util.LogUtils.logW


open class BaseActivity:ComponentActivity() {
    var TAG: String = javaClass.simpleName
    fun printD(msg: String?) {
        logD("$TAG=>", msg!!)
    }

    fun printI(msg: String?) {
        logI("$TAG=>", msg!!)
    }

    fun printE(msg: String?) {
        logE("$TAG=>", msg!!)
    }

    fun printW(msg: String?) {
        logW("$TAG=>", msg!!)
    }
}