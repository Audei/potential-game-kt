package com.departureDigital.game;

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.departureDigital.game.screen.MainMenuScreen
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.inject.Context
import ktx.inject.register

import com.badlogic.gdx.utils.ScreenUtils;

class Game : KtxGame<KtxScreen>() {
    val batch by lazy { SpriteBatch() }
    // use LibGDX's default Arial font
    val font by lazy { BitmapFont() }
    val assets = AssetManager()
    private val context = Context()

    override fun create() {
        context.register {
            bindSingleton<Batch>(SpriteBatch())
            bindSingleton(BitmapFont())
            bindSingleton(AssetManager())
            bindSingleton(OrthographicCamera().apply { setToOrtho(false, 800f, 480f) })
            addScreen(MainMenuScreen(this@Game, inject(), inject(), inject(), inject()))
        }
        setScreen<MainMenuScreen>()
        super.create()
    }

    override fun dispose() {
        // context.dispose()
        batch.dispose()
        font.dispose()
        assets.dispose()
        super.dispose()
    }
}
