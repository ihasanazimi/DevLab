package ir.ha.mylibrary.common.file

import android.R
import android.content.Context
import android.graphics.Bitmap
import com.bumptech.glide.Glide
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

@DelicateCoroutinesApi
fun saveUserCoverImage(context : Context, imageUrl : String, savePath : String){
    // save profile cover into storage and save it on prefFile
    if (imageUrl.isNotEmpty() && savePath.isNotEmpty()) {
        GlobalScope.launch(Dispatchers.IO) {
            saveImage(
                image = Glide.with(context)
                    .asBitmap()
                    .load(imageUrl)
                    .placeholder(R.drawable.progress_indeterminate_horizontal)
                    .error(R.drawable.stat_notify_error)
                    .submit()
                    .get(),
                savePath = savePath
            )
        }
    }
}


private fun saveImage(image: Bitmap, savePath : String) {
    var savedImagePath: String? = null
    val imageFileName = "JPEG_" + "YOUR_IMAGE_DOWNLOADED" + ".jpg"
    val storageDir = File(savePath , "userCoverDir")
    var success = true
    if (!storageDir.exists()) success = storageDir.mkdirs()
    if (success) {
        val imageFile = File(storageDir, imageFileName)
        savedImagePath = imageFile.absolutePath
        try {
            val fOut: OutputStream = FileOutputStream(imageFile)
            image.compress(Bitmap.CompressFormat.JPEG, 100, fOut)
            fOut.close()
        } catch (e: Exception) { e.printStackTrace() }
    }
}
