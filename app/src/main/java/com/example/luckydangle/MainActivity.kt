package com.example.luckydangle

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {
    private val overlayRequestCode = 2201

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(28, 32, 28, 32)
        }

        val title = TextView(this).apply {
            text = "Lucky Dangle"
            textSize = 28f
            gravity = Gravity.CENTER
        }
        val description = TextView(this).apply {
            text = "கயிறு உங்கள் திரையின் மேல் நடுப்பகுதியிலிருந்து தொங்கும்.\\n\\nPunch-hole கேமராவின் துல்லியமான இடம் மொபைல் மாடலைப் பொறுத்து மாறலாம்."
            textSize = 16f
            gravity = Gravity.CENTER
            setPadding(0, 24, 0, 24)
        }
        val start = Button(this).apply {
            text = "கயிற்றை இயக்கவும்"
            setOnClickListener { enableAndStart() }
        }
        val stop = Button(this).apply {
            text = "கயிற்றை நிறுத்தவும்"
            setOnClickListener {
                stopService(Intent(this@MainActivity, DangleOverlayService::class.java))
                Toast.makeText(this@MainActivity, "Dangle நிறுத்தப்பட்டது", Toast.LENGTH_SHORT).show()
            }
        }

        root.addView(title, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        root.addView(description, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        root.addView(start)
        root.addView(stop)
        setContentView(root)
    }

    private fun enableAndStart() {
        if (!Settings.canDrawOverlays(this)) {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            )
            startActivityForResult(intent, overlayRequestCode)
            return
        }
        startService(Intent(this, DangleOverlayService::class.java))
        Toast.makeText(this, "Lucky Dangle இயக்கப்பட்டது", Toast.LENGTH_SHORT).show()
    }

    @Deprecated("Use Activity Result APIs in production apps")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == overlayRequestCode && Settings.canDrawOverlays(this)) {
            startService(Intent(this, DangleOverlayService::class.java))
        }
    }
}
