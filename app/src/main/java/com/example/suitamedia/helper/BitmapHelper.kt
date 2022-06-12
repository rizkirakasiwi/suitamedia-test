package com.example.suitamedia.helper

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import java.io.ByteArrayOutputStream
import java.io.File

fun Bitmap.toUri(context:Context): Uri {
    val file = File(context.filesDir,"avatar")
    file.delete() // Delete the File, just in Case, that there was still another File
    file.createNewFile()
    val fos = file.outputStream()
    val baos = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, 100, baos)
    val byteArray = baos.toByteArray()
    fos.write(byteArray)
    fos.flush()
    fos.close()
    baos.close()
    return Uri.fromFile(file)
}