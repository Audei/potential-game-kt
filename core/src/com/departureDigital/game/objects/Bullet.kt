package com.departureDigital.game.objects
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Pool.Poolable;

class Bullet(private var player: Vector2, 
             private var speed: Float, 
             xPos: Float, 
             yPos: Float, 
             width:Float, 
             height: Float, 
             private var target: Vector2) : Rectangle(xPos, yPos, width, height), Poolable{

    constructor() : this(Vector2(0f,0f), 0f, 0f, 0f, 0f, 0f, Vector2(0f, 0f))

    private var movement = Vector2(0f, 0f)

    public fun init(player: Vector2, 
                    speed: Float, 
                    xPos: Float, 
                    yPos: Float, 
                    width:Float, 
                    height: Float, 
                    target: Vector2) {
        this.player = player
        this.speed = speed
        this.target = target
        this.set(Rectangle(xPos, yPos, width, height))
        movement = calculateMovement()
    }


    private fun calculateMovement():Vector2 {
        val angle = target.sub(player).angleRad()
        return Vector2(MathUtils.cos(angle) * speed, MathUtils.sin(angle) * speed)
    }
    
    public fun move(delta: Float) {
        x += movement.x * delta
        y += movement.y * delta
    }

    
	override fun reset() {
		//called when bullet is freed
        x = player.x
        y = player.y
        movement = Vector2(0f, 0f)
	}

}
