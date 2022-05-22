package com.departureDigital.game.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.departureDigital.game.Game
import com.departureDigital.game.assets.ImageAssets
import com.departureDigital.game.assets.load
import ktx.app.KtxScreen

class MainMenuScreen(private val game: Game,
                     private val batch: Batch,
                     private val font: BitmapFont,
                     private val assets: AssetManager,
                     private val camera: OrthographicCamera) : KtxScreen {

    override fun show() {
        ImageAssets.values().forEach { assets.load(it) }
    }

    override fun render(delta: Float) {
        // continue loading our assets
        assets.update()

        camera.update()
        batch.projectionMatrix = camera.combined
        batch.begin()
        font.draw(batch, "Welcome to a new game!!! ", 350f, 430f)
        if (assets.isFinished) {
            font.draw(batch, "Tap anywhere to begin!", 100f, 100f)
        } else {
            font.draw(batch, "Loading assets...", 100f, 100f)
        }
        batch.end()
        if (Gdx.input.isTouched && assets.isFinished) {
            game.addScreen(GameScreen(batch, font, assets, camera))
            game.setScreen<GameScreen>()
            game.removeScreen<MainMenuScreen>()
            dispose()
        }
    }
}
