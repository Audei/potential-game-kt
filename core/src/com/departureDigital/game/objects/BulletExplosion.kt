package com.departureDigital.game.objects
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

class BulletExplosion (xPos: Float, yPos: Float): ParticleEffect() {
    init {
        load(Gdx.files.internal("boom2.p"), Gdx.files.internal(""))
        setPosition(xPos, yPos);
        start()
    }
}
