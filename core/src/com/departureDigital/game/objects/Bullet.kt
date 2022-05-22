package com.departureDigital.game.objects
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Pool.Poolable;

class Bullet(private val player: Vector2, 
             private val speed: Float, 
             private val xPos: Float, 
             private val yPos: Float, 
             private val width:Float, 
             private val height: Float, 
             private val target: Vector2) : Rectangle(xPos, yPos, width, height){

    private val movement = calculateMovement()

    private fun calculateMovement():Vector2 {
        val angle = target.sub(player).angleRad()
        return Vector2(MathUtils.cos(angle) * speed, MathUtils.sin(angle) * speed)
    }
    
    public fun move(delta: Float) {
        x += movement.x * delta
        y += movement.y * delta
    }

}
