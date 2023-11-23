package com.kevin.playwithcompose.util

import android.util.Log

object LogUtils {
    fun logI(tag: String, msg: String) {
//        if (BuildConfig.DEBUG) {
        Log.i(tag, msg)
//        }
    }

    fun logD(tag: String, msg: String) {
//        if (BuildConfig.DEBUG) {
        Log.d(tag, msg)
//        }
    }

    fun logW(tag: String, msg: String) {
//        if (BuildConfig.DEBUG) {
        Log.w(tag, msg)
//        }
    }

    fun logE(tag: String, msg: String) {
//        if (BuildConfig.DEBUG) {
        Log.e(tag, msg)
//        }
    }

    fun logV(tag: String, msg: String) {
//        if (BuildConfig.DEBUG) {
        Log.v(tag, msg)
//        }
    }

    fun printD(msg: String) {
        val stackTrace = Thread.currentThread().stackTrace
        val callerMethodName = stackTrace[3].methodName
        val callerClassName = stackTrace[3].fileName
        val callerLineNumber = stackTrace[3].lineNumber
        Log.d(callerMethodName,"$callerClassName/$callerMethodName/$callerLineNumber:=>:$msg")
    }

    fun printE(msg: String) {
        val stackTrace = Thread.currentThread().stackTrace
        val callerMethodName = stackTrace[3].methodName
        val callerClassName = stackTrace[3].fileName
        val callerLineNumber = stackTrace[3].lineNumber
        Log.e(callerMethodName,"$callerClassName/$callerMethodName/$callerLineNumber:=>:$msg")
    }

    fun printI(msg: String) {
        val stackTrace = Thread.currentThread().stackTrace
        val callerMethodName = stackTrace[3].methodName
        val callerClassName = stackTrace[3].fileName
        val callerLineNumber = stackTrace[3].lineNumber
        Log.i(callerMethodName,"$callerClassName/$callerMethodName/$callerLineNumber:=>:$msg")
    }

    fun printW(msg: String) {
        val stackTrace = Thread.currentThread().stackTrace
        val callerMethodName = stackTrace[3].methodName
        val callerClassName = stackTrace[3].fileName
        val callerLineNumber = stackTrace[3].lineNumber
        Log.w(callerMethodName,"$callerClassName/$callerMethodName/$callerLineNumber:=>:$msg")
    }

    fun printV(msg: String) {
        val stackTrace = Thread.currentThread().stackTrace
        val callerMethodName = stackTrace[3].methodName
        val callerClassName = stackTrace[3].fileName
        val callerLineNumber = stackTrace[3].lineNumber
        Log.v(callerMethodName,"$callerClassName/$callerMethodName/$callerLineNumber:=>:$msg")
    }

    fun log(priority: Int, tag: String, msg: String) {
//        if (BuildConfig.DEBUG) {
        when (priority) {
            Log.VERBOSE -> Log.v(tag, msg)
            Log.DEBUG -> Log.d(tag, msg)
            Log.INFO -> Log.i(tag, msg)
            Log.ERROR -> Log.e(tag, msg)
            Log.WARN -> Log.w(tag, msg)
        }
//        }
    }


}