package com.departureDigital.game.screen

// clean up imports maybe
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.Input.Buttons
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Vector2 //Vector2 and Vector3 from libktx should be used
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.TimeUtils
import com.departureDigital.game.assets.ImageAssets
import com.departureDigital.game.assets.get
import com.departureDigital.game.Game
import com.departureDigital.game.objects.*
import ktx.app.KtxScreen
import ktx.graphics.*

class GameScreen(private val batch: Batch,
                 private val font: BitmapFont,
                 assets: AssetManager,
                 private val camera: OrthographicCamera) : KtxScreen {

    private val red = assets[ImageAssets.enemy]
    private val gun1 = assets[ImageAssets.gun1]
    private val gun2 = assets[ImageAssets.gun2]
    private var activeWeapon = Weapon(gun1, 400f)

    private val ball = Texture( Pixmap(64, 64, Pixmap.Format.RGBA8888).apply {
            setColor(Color.BLACK) 
            fillCircle(32, 32, 32)
        }
    )

    // probably should be somewhere else
    private val width = 800f
    private val height = 480f

    //player info
    private var player = Player(width, height)

    // create the touchPos to store mouse click position
    private val touchPos = Vector3()

    // enemy info
    private val enemies = Array<Rectangle>() // gdx, not Kotlin Array
    private var lastSpawnTime = 0L
    private var enemiesKilled = 0
    private var damageTaken = 0

    // Bullet info
    private val bulletTexture = assets[ImageAssets.bullet]
    private val bullets = Array<Bullet>() // gdx, not Kotlin Array
    private var lastShootTime = 0L
    private val particleEffects = Array<BulletExplosion>() // gdx, not Kotlin Array

    private fun spawnEnemy() {
        // make spawnable on other edges as well later
        enemies.add(Rectangle(MathUtils.random(0f, width - 64f), height, 25f, 25f))
        lastSpawnTime = TimeUtils.nanoTime()
    }

    private fun spawnBullet() {
        bullets.add(activeWeapon.fire(Vector2(player.getX(), player.getY()), Vector2(touchPos.x, touchPos.y)))
        lastShootTime = TimeUtils.nanoTime()
    }

    override fun render(delta: Float) {

        // set background color
        ScreenUtils.clear(0.5f, 0.3f, 0.2f, 1f)

        // generally good practice to update the camera's matrices once per frame
        camera.update()

        // Process touch events
        touchPos.set(Gdx.input.x.toFloat(), Gdx.input.y.toFloat(), 0f)
        camera.unproject(touchPos)

        // begin a new batch and draw 
        batch.use(camera) {
            font.draw(it, "Enemies Killed: $enemiesKilled", 0f, height)
            font.draw(it, "Damage Taken: $damageTaken", 550f, height)
            it.draw(ball, player.getX() - player.getRadius(), player.getY() - player.getRadius())

            for (enemy in enemies) {
                it.draw(red, enemy.x, enemy.y, enemy.width, enemy.height)
            }
            for (bullet in bullets) {
                it.draw(bulletTexture, bullet.getX(), bullet.getY())
            }
            val particleIter = particleEffects.iterator()
            while(particleIter.hasNext()) {
                val particleEffect = particleIter.next()
                particleEffect.draw(batch, delta)
                if(particleEffect.isComplete()) {
                    particleIter.remove()
                }
            }
            it.draw(
                activeWeapon.getTexture(), 
                player.getX() - activeWeapon.getTexture().getWidth() / 2, 
                player.getY() - activeWeapon.getTexture().getHeight() / 2, 
                activeWeapon.getTexture().getWidth().toFloat() / 2, 
                activeWeapon.getTexture().getHeight().toFloat() / 2, 
                activeWeapon.getTexture().getWidth().toFloat(), 
                activeWeapon.getTexture().getHeight().toFloat(), 
                1f, 
                1f, 
                MathUtils.radiansToDegrees * MathUtils.atan2(touchPos.y - (player.getY() + player.getRadius() / 2), 
                touchPos.x - (player.getX() + player.getRadius() / 2)) - 90, 
                0, 
                0, 
                activeWeapon.getTexture().getWidth(), 
                activeWeapon.getTexture().getHeight(), 
                false, 
                false
            ) // don't know how but make this better
        }

        // process user input
        // This should not be here in the future. I made it bad like this just so it would run while refactoring
        if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
            if (TimeUtils.nanoTime() - lastShootTime > 100_000_000L) {
                spawnBullet()
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
            activeWeapon.setTexture(gun1)
        }

        if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
            activeWeapon.setTexture(gun2)
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            player.setSpeedMultiplier(3)
        } else {
            player.setSpeedMultiplier(1)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.setAcceleration(player.getAcceleration() - 5f * delta * player.getSpeedMultiplier())
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.setAcceleration(player.getAcceleration() + 5f * delta * player.getSpeedMultiplier())
        }
        //else {
            player.setAcceleration(player.getAcceleration() * (60f*0.95f) * delta) 
        //}
        player.move(player.getDefaultMove() * delta * player.getAcceleration()) 

        // make sure the bucket stays within the screen bounds
        player.getHitBox().setX(MathUtils.clamp(player.getX(), player.getRadius(), width - player.getRadius()))

        // check if we need to create a new enemy
        if (TimeUtils.nanoTime() - lastSpawnTime > 1_000_000_000L) {
            spawnEnemy()
        }

        // move the enemies, remove any that are beneath the bottom edge of the
        //    screen or that hit the bucket.  In the latter case, play back a sound
        //    effect also
        var enemyIter = enemies.iterator()
        while (enemyIter.hasNext()) {
            val enemy = enemyIter.next()
            enemy.y -= 200 * delta
            if (enemy.y + 64 < 0) {
                enemyIter.remove()
            } 

            if (Intersector.overlaps(player.getHitBox(), enemy)) {
                enemyIter.remove()
                damageTaken++
            }
        }
        // move the bullets, remove any that are beneath the bottom edge of the
        //    screen or that hit the bucket. 
        val bulletIter = bullets.iterator()
        while (bulletIter.hasNext()) {
            val bullet = bulletIter.next()

            if (bullet.getY() + bulletTexture.getHeight() < 0 || bullet.getY() - bulletTexture.getHeight() > height || bullet.getX() + bulletTexture.getWidth() < 0 || bullet.getX() - bulletTexture.getWidth() > width) { 
                bulletIter.remove() 
                continue
            }

            val enemyIter = enemies.iterator()
            while(enemyIter.hasNext()) {
                val enemy = enemyIter.next()
                // gets index ouf of bounds for -1 sometimes
                if (Intersector.overlaps(bullet, enemy)) {
                    particleEffects.add(BulletExplosion(enemy.getX(), enemy.getY()))
                    enemyIter.remove()
                    bulletIter.remove()
                    enemiesKilled++
                }
            }
            bullet.move(delta)
        }
    }
}
