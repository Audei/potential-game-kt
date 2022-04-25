package com.departureDigital.game.objects
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Vector2
import com.departureDigital.game.objects.Bullet
// import com.departureDigital.game.objects.BulletPool
import com.badlogic.gdx.graphics.Texture

//add other properties here
class Weapon (texture: Texture, fireSpeed: Float) {
    private var texture: Texture
    private val fireSpeed: Float
    // private val BulletPool bp = new BulletPool();
    init {
        this.texture = texture
        this.fireSpeed = fireSpeed
    }

    public fun fire(firePosition: Vector2, target: Vector2) : Bullet {
        return Bullet(firePosition, fireSpeed, firePosition.x - texture.getWidth() / 2, firePosition.y - texture.getHeight() / 2, texture.getWidth().toFloat(), texture.getHeight().toFloat(), target)
    }

    public fun getTexture() : Texture {
        return texture
    }

    public fun setTexture(texture: Texture) {
        this.texture = texture
    }
}
