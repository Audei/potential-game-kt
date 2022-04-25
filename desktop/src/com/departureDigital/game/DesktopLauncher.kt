package com.departureDigital.game;

import com.badlogic.gdx.Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.departureDigital.game.Game;

object DesktopLauncher {
    @JvmStatic
    fun main(args: Array<String>)  {
        Lwjgl3Application(Game(), Lwjgl3ApplicationConfiguration().apply {
            setTitle("newGame")
            setWindowedMode(800, 480)
            useVsync(true)
        })
    }
}
