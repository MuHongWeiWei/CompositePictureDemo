package com.example.compositepicturedemo

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView

class MainActivity : AppCompatActivity() {

    private lateinit var bgBitmap: Bitmap
    private lateinit var coinBitmap: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val photoOne = findViewById<ImageView>(R.id.photo_one)
        val photoResult = findViewById<ImageView>(R.id.photo_two)

        bgBitmap = BitmapFactory.decodeResource(resources, R.drawable.bg)
        photoOne.setImageBitmap(bgBitmap)

        coinBitmap = BitmapFactory.decodeResource(resources, R.drawable.coin)
        photoResult.setImageBitmap(coinBitmap)
    }

    private fun mergeBitmap(backBitmap: Bitmap, frontBitmap: Bitmap): Bitmap {
val bitmap = backBitmap.copy(Bitmap.Config.ARGB_8888, true)
val canvas = Canvas(bitmap)
val baseRect = Rect(0, 0, backBitmap.width, backBitmap.height)
val frontRect = Rect(0, 0, frontBitmap.width, frontBitmap.height)
canvas.drawBitmap(frontBitmap, frontRect, baseRect, null)
        return bitmap
    }

    fun merge(view: View) {
        val contentValues = ContentValues()
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, System.currentTimeMillis())
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        val uri =
            contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        uri?.apply {
            val ops = contentResolver.openOutputStream(this)
            mergeBitmap(bgBitmap, coinBitmap).compress(Bitmap.CompressFormat.JPEG, 100, ops)
            ops?.close()
        }
    }
}