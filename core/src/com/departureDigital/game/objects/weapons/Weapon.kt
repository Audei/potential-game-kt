package com.departureDigital.game.objects
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Vector2
import com.departureDigital.game.objects.Bullet
import com.departureDigital.game.objects.BulletPool
import com.badlogic.gdx.graphics.Texture

class Weapon (private var texture: Texture, private val fireSpeed: Float) {
    private val bp = BulletPool();

    public fun fire(firePosition: Vector2, target: Vector2) : Bullet {
        val bullet = bp.obtain()
        bullet.init(firePosition, fireSpeed, firePosition.x - texture.getWidth() / 2, firePosition.y - texture.getHeight() / 2, texture.getWidth().toFloat(), texture.getHeight().toFloat(), target)
        return bullet
    }

    public fun free(bullet: Bullet) {
        bp.free(bullet)
    }

    public fun getTexture() : Texture {
        return texture
    }

    public fun setTexture(texture: Texture) {
        this.texture = texture
    }
}
