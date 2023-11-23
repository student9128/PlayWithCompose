package com.kevin.playwithcompose.util

import android.util.Log

fun getCurrentMethodName(): String {
    val stackTrace = Thread.currentThread().stackTrace
    // 获取调用当前getCurrentMethodName方法的方法名
    val callerMethodName = stackTrace[4].methodName
    return callerMethodName
}