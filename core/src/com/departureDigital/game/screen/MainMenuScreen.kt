package com.departureDigital.game.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.OrthographicCamera
import com.departureDigital.game.Game
import com.departureDigital.game.assets.ImageAssets
import com.departureDigital.game.assets.load
import ktx.app.KtxScreen

class MainMenuScreen(val game: Game) : KtxScreen {
    private val camera = OrthographicCamera().apply { setToOrtho(false, 800f, 480f) }

    override fun show() {
        ImageAssets.values().forEach { game.assets.load(it) }
    }

    override fun render(delta: Float) {
        // continue loading our assets
        game.assets.update()

        camera.update()
        game.batch.projectionMatrix = camera.combined

        game.batch.begin()
        game.font.draw(game.batch, "Welcome to a new game!!! ", 350f, 430f)

        if (game.assets.isFinished) {
            game.font.draw(game.batch, "Tap anywhere to begin!", 100f, 100f)
        } else {
            game.font.draw(game.batch, "Loading assets...", 100f, 100f)
        }
        game.batch.end()
        if (Gdx.input.isTouched && game.assets.isFinished) {
            game.addScreen(GameScreen(game))
            game.setScreen<GameScreen>()
            game.removeScreen<MainMenuScreen>()
            dispose()
        }
    }
}
