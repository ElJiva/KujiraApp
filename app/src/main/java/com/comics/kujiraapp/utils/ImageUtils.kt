package com.comics.kujiraapp.utils

import android.content.Context
import androidx.annotation.DrawableRes

@DrawableRes
fun getDrawableResourceId(context: Context, imageName: String): Int {
    val nameWithoutExtension = imageName
        .replace(".jpg", "")
        .replace(".png", "")
        .replace("-", "_")
        .lowercase()

    return context.resources.getIdentifier(
        nameWithoutExtension,
        "drawable",
        context.packageName
    )
}