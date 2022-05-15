package com.departureDigital.game.assets

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import ktx.assets.getAsset
import ktx.assets.load

// images
enum class ImageAssets(val path: String) {
    enemy("red.png"),
    gun1("cannon.png"),
    gun2("gun2.png"),
    bullet("bullet2.png")
}

inline fun AssetManager.load(asset: ImageAssets) = load<Texture>(asset.path)
inline operator fun AssetManager.get(asset: ImageAssets) = getAsset<Texture>(asset.path) 
