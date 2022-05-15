package com.departureDigital.game.objects
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Input.Buttons
import com.badlogic.gdx.math.Circle

class Player(width: Float, height: Float) {
    private val hitbox = Circle(width / 2f, height / 2f, 32f)
    private val defaultMove = 200
    private var speedMultiplier = 1
    private var acceleration = 1f

    public fun getX() : Float {
        return hitbox.x
    }

    public fun getY() : Float {
        return hitbox.y
    }

    public fun getRadius() : Float {
        return hitbox.radius
    }

    public fun move(amt: Float) {
        hitbox.setX(hitbox.x + amt)
    }

    public fun getHitBox() : Circle{
        return hitbox
    }

    public fun getDefaultMove() : Int {
        return defaultMove
    }

    public fun getSpeedMultiplier() : Int {
        return speedMultiplier
    }

    public fun setSpeedMultiplier(speed: Int) {
        speedMultiplier = speed
    }

    public fun getAcceleration() : Float {
        return acceleration
    }

    public fun setAcceleration(accel: Float) {
        acceleration = accel
    }
}
