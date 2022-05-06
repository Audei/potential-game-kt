package com.departureDigital.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.departureDigital.game.Game;

fun main()  {
    Lwjgl3Application(Game(), Lwjgl3ApplicationConfiguration().apply {
        setTitle("newGame")
        setWindowedMode(800, 480)
        useVsync(true)
    })
}
