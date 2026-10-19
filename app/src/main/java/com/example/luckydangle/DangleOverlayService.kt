package com.example.luckydangle

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.Gravity
import android.view.WindowManager

class DangleOverlayService : Service() {
    private lateinit var windowManager: WindowManager
    private var dangleView: DangleView? = null

    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        showOverlay()
    }

    private fun showOverlay() {
        if (dangleView != null) return

        val view = DangleView(this)
        dangleView = view

        val params = WindowManager.LayoutParams(
            220,
            360,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
            x = 0
            y = 0
        }

        try {
            windowManager.addView(view, params)
        } catch (_: Exception) {
            stopSelf()
        }
    }

    override fun onDestroy() {
        dangleView?.let {
            try { windowManager.removeView(it) } catch (_: Exception) {}
        }
        dangleView = null
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
