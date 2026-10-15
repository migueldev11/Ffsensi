package com.ffcheat.pro

object NativeBridge {
    init {
        System.loadLibrary("ffcheat")
    }

    // Inicializa o Cheat com o PID do Free Fire
    external fun initCheat(pid: Int, screenWidth: Int, screenHeight: Int)

    // Chama o tick principal (atualiza Aimbot/ESP/Recoil)
    external fun tickCheat(currentAimX: Float, currentAimY: Float)

    // Define a base da memória do jogo
    external fun setGameBase(base: Int)

    // Configurações
    external fun setAimbotEnabled(enabled: Boolean)
    external fun setAimbotFov(fov: Float)
    external fun setEspEnabled(enabled: Boolean)
    external fun setRecoilEnabled(enabled: Boolean)
    
    // Retorna o alvo atual (para debug)
    external fun getTargetName(): String
}