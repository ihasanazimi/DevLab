package ir.hasanazimi.me.common.file

import android.content.Context
import android.graphics.Bitmap
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import ir.hasanazimi.me.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

suspend fun saveUserCoverImage(context: Context, imageUrl: String, savePath: String) {
    if (imageUrl.isNotEmpty() && savePath.isNotEmpty()) {
        withContext(Dispatchers.IO) {
            try {
                val loader = ImageLoader(context)
                val request = ImageRequest.Builder(context)
                    .data(imageUrl)
                    .placeholder(R.drawable.baseline_error_outline_24)
                    .error(R.drawable.baseline_error_outline_24)
                    .build()

                val result = loader.execute(request)
                if (result is SuccessResult) {
                    val bitmap =
                        (result.drawable as android.graphics.drawable.BitmapDrawable).bitmap
                    saveImage(
                        image = bitmap,
                        savePath = savePath
                    )
                } else {
                    throw Exception("Failed to load image with Coil")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}


private fun saveImage(image: Bitmap, savePath: String) {
    var savedImagePath: String? = null
    val imageFileName = "JPEG_" + "YOUR_IMAGE_DOWNLOADED" + ".jpg"
    val storageDir = File(savePath, "userCoverDir")
    var success = true
    if (!storageDir.exists()) success = storageDir.mkdirs()
    if (success) {
        val imageFile = File(storageDir, imageFileName)
        savedImagePath = imageFile.absolutePath
        try {
            val fOut: OutputStream = FileOutputStream(imageFile)
            image.compress(Bitmap.CompressFormat.JPEG, 100, fOut)
            fOut.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
