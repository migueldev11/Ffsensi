package com.ffcheat.pro

import android.content.Intent
import android.os.Bundle
import android.os.Process
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    private lateinit var btnStart: Button
    private lateinit var btnStop: Button
    private lateinit var tvStatus: TextView
    private var isCheatRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnStart = findViewById(R.id.btnStartCheat)
        btnStop = findViewById(R.id.btnStopCheat)
        tvStatus = findViewById(R.id.tvStatus)

        // Verificar permissão de Overlay
        if (!WindowManager.canDrawOverlays(this)) {
            val intent = Intent(android.provider.Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            intent.data = android.net.Uri.parse("package:$packageName")
            startActivity(intent)
        }

        btnStart.setOnClickListener {
            if (WindowManager.canDrawOverlays(this)) {
                startCheat()
            } else {
                tvStatus.text = "Permissão de Overlay negada!"
            }
        }

        btnStop.setOnClickListener {
            stopCheat()
        }
    }

    private fun startCheat() {
        // 1. Encontrar o PID do Free Fire
        val pid = findFreeFirePid()
        if (pid == -1) {
            tvStatus.text = "Free Fire não está em execução!"
            return
        }

        // 2. Iniciar o Serviço de Overlay
        val intent = Intent(this, OverlayService::class.java)
        intent.putExtra("TARGET_PID", pid)
        startForegroundService(intent)

        // 3. Inicializar o Cheat Nativo
        val displayMetrics = resources.displayMetrics
        NativeBridge.initCheat(pid, displayMetrics.widthPixels, displayMetrics.heightPixels)

        // 4. Configurar Padrões
        NativeBridge.setAimbotEnabled(true)
        NativeBridge.setAimbotFov(120f)
        NativeBridge.setEspEnabled(true)
        NativeBridge.setRecoilEnabled(true)

        isCheatRunning = true
        tvStatus.text = "Status: Ativo (PID: $pid)"
        tvStatus.setTextColor(getColor(R.color.accent_green))
        btnStart.isEnabled = false
        btnStop.isEnabled = true
    }

    private fun stopCheat() {
        val intent = Intent(this, OverlayService::class.java)
        stopService(intent)
        isCheatRunning = false
        tvStatus.text = "Status: Inativo"
        tvStatus.setTextColor(getColor(R.color.grey))
        btnStart.isEnabled = true
        btnStop.isEnabled = false
    }

    private fun findFreeFirePid(): Int {
        val procDir = File("/proc")
        val files = procDir.listFiles() ?: return -1
        for (file in files) {
            if (file.name.toIntOrNull() != null) {
                val cmdlineFile = File(file, "cmdline")
                if (cmdlineFile.exists()) {
                    val process = BufferedReader(InputStreamReader(cmdlineFile.inputStream()))
                    val cmdline = process.readLine()?.replace("\u0000", " ")
                    process.close()
                    
                    if (cmdline != null && cmdline.contains("com.dts.freefireth")) {
                        return file.name.toIntOrNull() ?: -1
                    }
                }
            }
        }
        return -