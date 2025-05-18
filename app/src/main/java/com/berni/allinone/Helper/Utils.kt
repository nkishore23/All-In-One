package com.berni.allinone.Helper

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

fun saveImageToInternalStorage(context: Context, sourceUri: Uri, fileName: String): String? {
    return try {
        val inputStream = context.contentResolver.openInputStream(sourceUri)
        val file = File(context.filesDir, fileName)
        val outputStream = FileOutputStream(file)

        inputStream?.copyTo(outputStream)

        inputStream?.close()
        outputStream.close()

        file.absolutePath
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}