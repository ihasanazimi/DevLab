package ir.hasanazimi.devlab.common.file

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.provider.MediaStore
import android.util.Log
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

private const val TAG = "FileUtils_TAG"


object ImagePicker {
    const val IMAGE_PICKER_REQUEST_CODE = 123
    fun pickImage(activity: Activity) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        activity.startActivityForResult(intent, IMAGE_PICKER_REQUEST_CODE)
    }
    /**
     *
     * Usage :
     * class YourActivity : AppCompatActivity() {
     *     // ...
     *
     *     fun openImagePicker() {
     *         ImagePicker.pickImage(this)
     *     }
     *
     *     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
     *         super.onActivityResult(requestCode, resultCode, data)
     *         if (requestCode == ImagePicker.IMAGE_PICKER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
     *             // Handle the selected image URI here
     *             val selectedImageUri = data?.data
     *             // Do something with the selected image URI
     *         }
     *     }
     * }
     *
     */
}






object ImageColorAnalyzer {
    /**
     *
     * USAGE :
     * val bitmap: Bitmap = // Obtain bitmap from image
     * val mostUsedColor = ImageColorAnalyzer.getMostUsedColor(bitmap)
     * */

    fun getMostUsedColor(bitmap: Bitmap): Int {
        val pixels = IntArray(bitmap.width * bitmap.height)
        bitmap.getPixels(pixels, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)

        val colorMap = mutableMapOf<Int, Int>()

        for (pixel in pixels) {
            if (colorMap.containsKey(pixel)) {
                colorMap[pixel] = colorMap[pixel]!! + 1
            } else {
                colorMap[pixel] = 1
            }
        }

        var maxColor = 0
        var maxCount = 0

        for ((color, count) in colorMap) {
            if (count > maxCount) {
                maxColor = color
                maxCount = count
            }
        }

        return maxColor
    }
}





fun getFileFromThisPath(path: String): File {
    return File(path).also {
        Log.i(TAG, "getFileFromThisPath: $it")
    }
}





fun ByteArray.saveFile(filePath: String): Boolean {
    return try {
        val file = File(filePath)
        val outputStream = FileOutputStream(file)
        outputStream.write(this)
        outputStream.close()
        true
    } catch (e: IOException) {
        e.printStackTrace()
        false
    }
    /** return save successfully or no */
}


fun openBitmap(context: Context?, imageFileName: String?): Bitmap {
    return BitmapFactory.decodeStream(context!!.openFileInput(imageFileName))
}


fun createImageFromBitmap(context: Context?, bitmap: Bitmap, signFileName: String): String? {
    var fileName: String? = signFileName
    try {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val fo: FileOutputStream = context!!.openFileOutput(fileName, Context.MODE_PRIVATE)
        fo.write(bytes.toByteArray())
        // remember close file output
        fo.close()
    } catch (e: Exception) {
        e.printStackTrace()
        fileName = null
    }
    Log.i(TAG, "createImageFromBitmap: ${fileName}")
    return fileName
}