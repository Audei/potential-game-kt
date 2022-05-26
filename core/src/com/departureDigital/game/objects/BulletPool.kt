package com.departureDigital.game.objects
import com.badlogic.gdx.utils.Pool
import com.departureDigital.game.objects.Bullet

class BulletPool() : Pool<Bullet>() {

    override fun newObject() : Bullet{
        return Bullet()
    }

}
