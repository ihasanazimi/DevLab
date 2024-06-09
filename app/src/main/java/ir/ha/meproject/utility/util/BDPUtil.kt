package ir.ha.meproject.utility.util

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Picture
import android.graphics.drawable.Drawable

// B -> Bitmap
// D -> Drawable
// P -> Picture

fun drawableToBitmap(drawable: Drawable, width: Int, height: Int): Bitmap {
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap
}


fun bitmapToPicture(bitmap: Bitmap): Picture {
    val picture = Picture()
    val canvas = picture.beginRecording(bitmap.width, bitmap.height)
    canvas.drawBitmap(bitmap, 0f, 0f, null)
    picture.endRecording()
    return picture
}


fun drawableToPicture(drawable: Drawable, width: Int, height: Int): Picture {
    // Step 1: Convert Drawable to Bitmap
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)

    // Step 2: Convert Bitmap to Picture
    val picture = Picture()
    val pictureCanvas = picture.beginRecording(bitmap.width, bitmap.height)
    pictureCanvas.drawBitmap(bitmap, 0f, 0f, null)
    picture.endRecording()

    return picture
}