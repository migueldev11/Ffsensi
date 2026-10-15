package com.ffcheat.pro

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import android.view.*
import android.widget.*
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import java.io.File
import java.util.concurrent.Executors

class OverlayService : Service() {

    private lateinit var windowManager: WindowManager
    private var overlayView: View? = null
    private var isOverlayVisible = true
    private val executor = Executors.newSingleThreadExecutor()
    private var lastVolumeUp = 0L
    private var lastVolumeDown = 0L
    private var volumeUpCount = 0
    private var volumeDownCount = 0

    // Variáveis de estado do Cheat
    var aimbotEnabled = true
    var espEnabled = true
    var recoilEnabled = true
    var aimbotFov = 120f

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        
        // Cria a View do Painel
        val layoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        overlayView = layoutInflater.inflate(R.layout.overlay_panel, null)
        
        setupOverlayClicks()
        
        // Adiciona o Overlay à tela
        addOverlay()
        
        // Inicia a thread de monitoramento do jogo
        startCheatLoop()
    }

    private fun addOverlay() {
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) 
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY 
            else 
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            PixelFormat.TRANSLUCENT
        )
        params.gravity = Gravity.TOP or Gravity.START
        params.x = 0
        params.y = 100
        
        windowManager.addView(overlayView, params)
    }

    private fun setupOverlayClicks() {
        overlayView?.let { view ->
            val btnAimbot = view.findViewById<Switch>(R.id.switchAimbot)
            val btnEsp = view.findViewById<Switch>(R.id.switchEsp)
            val btnRecoil = view.findViewById<Switch>(R.id.switchRecoil)
            val btnMinimize = view.findViewById<ImageButton>(R.id.btnMinimize)
            
            btnAimbot.isChecked = aimbotEnabled
            btnEsp.isChecked = espEnabled
            btnRecoil.isChecked = recoilEnabled

            btnAimbot.setOnCheckedChangeListener { _, isChecked ->
                aimbotEnabled = isChecked
                NativeBridge.setAimbotEnabled(isChecked)
            }
            
            btnEsp.setOnCheckedChangeListener { _, isChecked ->
                espEnabled = isChecked
                NativeBridge.setEspEnabled(isChecked)
            }
            
            btnRecoil.setOnCheckedChangeListener { _, isChecked ->
                recoilEnabled = isChecked
                NativeBridge.setRecoilEnabled(is