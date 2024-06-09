package ir.ha.meproject.utility.security

import android.os.Build
import androidx.annotation.RequiresApi
import java.io.File
import java.security.Key
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class Base64Util {

    @RequiresApi(Build.VERSION_CODES.O)
    fun encryptBase64(data: String): String {
        val encodedBytes = Base64.getEncoder().encodeToString(data.toByteArray())
        return encodedBytes
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun decryptBase64(encodedData: String): String {
        val decodedBytes = Base64.getDecoder().decode(encodedData)
        return String(decodedBytes)
    }

}



class AESCryptor(private val key: String) {
    private val cipher: Cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
    private val secretKey: Key = SecretKeySpec(key.toByteArray(), "AES")

    @RequiresApi(Build.VERSION_CODES.O)
    fun encrypt(data: String): String {
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val encryptedBytes = cipher.doFinal(data.toByteArray())
        return Base64.getEncoder().encodeToString(encryptedBytes)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun decrypt(encryptedData: String): String {
        cipher.init(Cipher.DECRYPT_MODE, secretKey)
        val decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData))
        return String(decryptedBytes)
    }


    // this is sample

    /*fun main() {
        val key = "mySecretKey123456" // 128-bit key for AES
        val cryptor = AESCryptor(key)

        // Example usage
        val originalData = "Hello, world!"
        val encryptedData = cryptor.encrypt(originalData)
        println("Encrypted data: $encryptedData")

        val decryptedData = cryptor.decrypt(encryptedData)
        println("Decrypted data: $decryptedData")
    }*/


}


@RequiresApi(Build.VERSION_CODES.O)
fun encodeFileToBase64(file: File): String {
    val bytes = file.readBytes()
    return Base64.getEncoder().encodeToString(bytes)

    /**
     *
     *     // Load your image file
     *     val imageFile = File("path/to/your/image.jpg")
     *
     *     // Convert the image file to Base64 string
     *     val base64Image = encodeFileToBase64(imageFile)
     *
     *     // Create a JSON object with the Base64 image data
     *     val jsonObject = mapOf("image" to base64Image)
     *
     *     // Convert the JSON object to JSON text
     *     val jsonText = gson.toJson(jsonObject)
     *
     *     // Output the JSON text
     *     println(jsonText)
     *
     */
}
