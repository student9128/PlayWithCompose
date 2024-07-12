package com.kevin.playwithcompose

import android.app.Notification.Action
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.usb.UsbManager
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsbCheckPage(navHostController: NavHostController) {
    val context = LocalContext.current
    val usbManager = context.getSystemService(Context.USB_SERVICE) as UsbManager
    DisposableEffect(key1 = "") {
        val intentFilter = IntentFilter()
        intentFilter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED)
        intentFilter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED)
        intentFilter.addAction(UsbManager.ACTION_USB_ACCESSORY_ATTACHED)
        intentFilter.addAction(UsbManager.ACTION_USB_ACCESSORY_DETACHED)
        intentFilter.addAction("android.hardware.usb.action.USB_STATE")
        intentFilter.addAction("android.intent.action.ACTION_POWER_CONNECTED")
        intentFilter.addAction("android.intent.action.ACTION_POWER_DISCONNECTED")
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if(intent?.action=="android.hardware.usb.action.USB_STATE"){
                    val boolean = intent.extras?.getBoolean("connected")
                    if(boolean==true){
                    Toast.makeText(context, "USB connected= $boolean", Toast.LENGTH_SHORT).show()
                    }else{
                    Toast.makeText(context, "USB connected= $boolean", Toast.LENGTH_SHORT).show()
                    }
                }else if(intent?.action == Intent.ACTION_POWER_CONNECTED){
                    Toast.makeText(context, "Power connected", Toast.LENGTH_SHORT).show()
                }else if(intent?.action==Intent.ACTION_POWER_DISCONNECTED){
                    Toast.makeText(context, "Power disconnected", Toast.LENGTH_SHORT).show()
                }
                if (intent?.action == UsbManager.ACTION_USB_DEVICE_ATTACHED) {
                    // USB device attached
                    Toast.makeText(context, "USB device attached", Toast.LENGTH_SHORT).show()
                } else if (intent?.action == UsbManager.ACTION_USB_DEVICE_DETACHED) {
                    // USB device detached
                    Toast.makeText(context, "USB device detached", Toast.LENGTH_SHORT).show()
                }

            }
        }
        context.registerReceiver(receiver, intentFilter)
        onDispose {
            context.unregisterReceiver(receiver)
        }
    }

    Scaffold(topBar = {
        AppBar(title = "usb check", onBackClick = {
            navHostController.navigateUp()
        })
    }) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Button(onClick = {
                checkUsbDeviceStatus(usbManager,context)
            }) {
                Text(text = "检测手机是否连接usb")
            }

        }

    }

}

fun checkUsbDeviceStatus(usbManager: UsbManager,context: Context) {
    // 获取当前连接的 USB 设备列表
    val deviceList = usbManager.deviceList

    // 如果列表不为空，则至少有一个 USB 设备已连接
    if (deviceList.isNotEmpty()) {
        // 在这里处理 USB 设备已连接的逻辑
        Toast.makeText(context, "USB device is connected", Toast.LENGTH_SHORT).show()
    } else {
        Toast.makeText(context, "No USB device connected", Toast.LENGTH_SHORT).show()
    }
}
