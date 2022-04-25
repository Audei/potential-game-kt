package com.departureDigital.game.objects
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Pool.Poolable;

class Bullet(playerInfo: Vector2, bulletSpeed: Float, xPos: Float, yPos: Float, width:Float, height: Float, touchPos: Vector2) : Rectangle(xPos, yPos, width, height){
    private val speed: Float 
    private val target: Vector2
    private val player: Vector2
    private val movement: Vector2
    private val origin: Vector2

    init {
        speed = bulletSpeed
        target = touchPos
        player = playerInfo
        movement = calculateMovement()
        origin = Vector2(xPos, yPos)
    }

	// override fun reset() {
        // x = origin.x
        // y = origin.y
	// 	System.out.println("Bullet is reset");
	// }

    private fun calculateMovement():Vector2 {
        val angle = target.sub(player).angleRad()
        return Vector2(MathUtils.cos(angle) * speed, MathUtils.sin(angle) * speed)
    }
    
    public fun move(delta: Float) {
        x += movement.x * delta
        y += movement.y * delta
    }

}
