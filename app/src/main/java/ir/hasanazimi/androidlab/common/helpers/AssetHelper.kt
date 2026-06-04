package ir.hasanazimi.androidlab.common.helpers

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Picture
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Date
import java.util.Locale

object AssetHelper {
    fun getFilePathFromAssets(context: Context, assetFileName: String): String? {
        val file = File(context.filesDir, assetFileName)
        try {
            val inputStream = context.assets.open(assetFileName)
            val outputStream = FileOutputStream(file)
            val buffer = ByteArray(1024)
            var length: Int
            while (inputStream.read(buffer).also { length = it } > 0) {
                outputStream.write(buffer, 0, length)
            }
            outputStream.flush()
            outputStream.close()
            inputStream.close()
            return file.absolutePath
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
    }

    fun readJsonFromAssets(context: Context, fileName: String): String? {
        return try {
            context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ex: Exception) {
            ex.printStackTrace()
            null
        }
    }
}

object FontHelper {
    fun getTypeface(context: Context, fontResId: Int): Typeface? {
        return ResourcesCompat.getFont(context, fontResId)
    }
}

object ImagePicker {
    const val IMAGE_PICKER_REQUEST_CODE = 123

    fun pickImage(activity: Activity) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
            type = "image/*"
        }
        activity.startActivityForResult(intent, IMAGE_PICKER_REQUEST_CODE)
    }
}

object ImageColorAnalyzer {
    fun getMostUsedColor(bitmap: Bitmap): Int {
        val pixels = IntArray(bitmap.width * bitmap.height)
        bitmap.getPixels(pixels, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        val colorMap = mutableMapOf<Int, Int>()
        for (pixel in pixels) {
            colorMap[pixel] = colorMap.getOrDefault(pixel, 0) + 1
        }
        return colorMap.maxByOrNull { it.value }?.key ?: Color.BLACK
    }
}

object RealPathHelper {
    fun getRealPath(context: Context, uri: Uri): String? {
        return when {
            Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT -> getRealPathFromURI_BelowAPI11(context, uri)
            Build.VERSION.SDK_INT < Build.VERSION_CODES.M -> getRealPathFromURI_API11to18(context, uri)
            else -> getRealPathFromURI_API19(context, uri)
        }
    }

    private fun getRealPathFromURI_BelowAPI11(context: Context, uri: Uri): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        context.contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
            val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(columnIndex)
        }
        return null
    }

    private fun getRealPathFromURI_API11to18(context: Context, uri: Uri): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(uri, projection, null, null, null)
        cursor?.use {
            val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            it.moveToFirst()
            return it.getString(columnIndex)
        }
        return null
    }

    @SuppressLint("NewApi")
    private fun getRealPathFromURI_API19(context: Context, uri: Uri): String? {
        if (DocumentsContract.isDocumentUri(context, uri)) {
            when {
                isExternalStorageDocument(uri) -> {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split = docId.split(":")
                    if (split.size == 2 && "primary" == split[0]) {
                        return "${Environment.getExternalStorageDirectory()}/${split[1]}"
                    }
                }
                isDownloadsDocument(uri) -> {
                    val id = DocumentsContract.getDocumentId(uri)
                    val contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        id.toLong()
                    )
                    return getDataColumn(context, contentUri, null, null)
                }
                isMediaDocument(uri) -> {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split = docId.split(":")
                    if (split.size == 2) {
                        val type = split[0]
                        val contentUri = when (type) {
                            "image" -> MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                            "video" -> MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                            "audio" -> MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                            else -> null
                        }
                        if (contentUri != null) {
                            return getDataColumn(
                                context,
                                contentUri,
                                "_id=?",
                                arrayOf(split[1])
                            )
                        }
                    }
                }
            }
        }
        if ("content" == uri.scheme) {
            if (isGooglePhotosUri(uri)) {
                return uri.lastPathSegment
            }
            return getDataColumn(context, uri, null, null)
        }
        if ("file" == uri.scheme) {
            return uri.path
        }
        return null
    }

    private fun getDataColumn(context: Context, uri: Uri, selection: String?, selectionArgs: Array<String>?): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        context.contentResolver.query(uri, projection, selection, selectionArgs, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                return cursor.getString(columnIndex)
            }
        }
        return null
    }

    private fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    private fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    private fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }

    private fun isGooglePhotosUri(uri: Uri): Boolean {
        return "com.google.android.apps.photos.content" == uri.authority
    }
}

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

fun drawableToPicture(drawable: Drawable): Picture {
    val bitmap = drawableToBitmap(drawable, drawable.intrinsicWidth, drawable.intrinsicHeight)
    return bitmapToPicture(bitmap)
}

fun drawableToPicture(drawable: Drawable, width: Int, height: Int): Picture {
    val bitmap = drawableToBitmap(drawable, width, height)
    return bitmapToPicture(bitmap)
}

fun getFileFromThisPath(path: String): File {
    return File(path).also { Log.i("FileUtils_TAG", "getFileFromThisPath: $it") }
}

fun ByteArray.saveFile(filePath: String): Boolean {
    return try {
        val file = File(filePath)
        FileOutputStream(file).use { it.write(this) }
        true
    } catch (e: IOException) {
        e.printStackTrace()
        false
    }
}

fun openBitmap(context: Context?, imageFileName: String?): Bitmap {
    return BitmapFactory.decodeStream(context!!.openFileInput(imageFileName))
}


fun View.captureToBitmap(): Bitmap {
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    draw(canvas)
    return bitmap
}


fun createImageFromBitmap(context: Context?, bitmap: Bitmap, signFileName: String): String? {
    var fileName: String? = signFileName
    try {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        context!!.openFileOutput(fileName, Context.MODE_PRIVATE).use { it.write(bytes.toByteArray()) }
    } catch (e: Exception) {
        e.printStackTrace()
        fileName = null
    }
    Log.i("FileUtils_TAG", "createImageFromBitmap: $fileName")
    return fileName
}

fun getCurrentTimeIn24HourFormat(): String {
    val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    return dateFormat.format(Date())
}

fun String.toDateTimeOrNull(): Date? {
    return try {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault()).apply { isLenient = false }
        sdf.parse(this)
    } catch (e: Exception) {
        null
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun String.toLocalTimeOrNull(): LocalTime? {
    return try {
        LocalTime.parse(this, DateTimeFormatter.ofPattern("HH:mm"))
    } catch (e: DateTimeParseException) {
        null
    }
}

fun convertSecondToMilliSecond(time: Long): Long {
    return time * 1000L
}

fun convertSystemTimeMSToSecond(millisecond: Long): Long {
    return millisecond / 1000L
}

fun convertSystemTimeMSToMinute(millisecond: Long): String? {
    val second = (millisecond / 1000) % 60
    val minute = (millisecond / (1000 * 60)) % 60
    return String.format(Locale.US, "%02d:%02d", minute, second)
}

fun getTimeFormat(hour: String, minute: String): String {
    return String.format(Locale.getDefault(), "%02d:%02d", hour.toInt(), minute.toInt())
}