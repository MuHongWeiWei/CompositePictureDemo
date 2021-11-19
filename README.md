# Android 合成兩張圖片並存到手機相簿

##### 我們有時候會需要把兩張圖片合在一起，這時候我們就會用到圖層關係，我們把要當底層的圖片先放在Canvas，接著再把另外一張覆蓋在上面，前提是那張圖片是有透明背景的，這樣才能透到下面的底層圖片。

---

#### 文章目錄
<ol>
    <li><a href="#a">獲取圖片並轉成Bitmap</a></li>
    <li><a href="#b">合成兩張圖片</a></li>
    <li><a href="#c">儲存到手機相簿</a></li>
	<li><a href="#d">效果展示</a></li>
	<li><a href="#e">Github</a></li>
</ol>

---

<a id="a"></a>
#### 1.獲取圖片並轉成Bitmap
```Kotlin
//背景圖片
val bgBitmap = BitmapFactory.decodeResource(resources, R.drawable.bg)
//前景圖片
val coinBitmap = BitmapFactory.decodeResource(resources, R.drawable.coin)
```

<a id="b"></a>
#### 2.合成兩張圖片
```Kotlin
//將背景圖在塗層最底下
val bitmap = backBitmap.copy(Bitmap.Config.ARGB_8888, true)
val canvas = Canvas(bitmap)
val baseRect = Rect(0, 0, backBitmap.width, backBitmap.height)
val frontRect = Rect(0, 0, frontBitmap.width, frontBitmap.height)
canvas.drawBitmap(frontBitmap, frontRect, baseRect, null)
```

<a id="c"></a>
#### 3.儲存到手機相簿
```Kotlin
val contentValues = ContentValues()
contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, System.currentTimeMillis())
contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
uri?.apply {
    val ops = contentResolver.openOutputStream(this)
	//把Bitmap除存到手機相簿
    mergeBitmap(bgBitmap, coinBitmap).compress(Bitmap.CompressFormat.JPEG, 100, ops)
    ops?.close()
}
```

<a id="d"></a>
#### 4.效果展示
<a href="https://badgameshow.com/fly/wp-content/uploads/2021/11/video-1637312564.gif"><img src="https://badgameshow.com/fly/wp-content/uploads/2021/11/video-1637312564.gif" width="40%"/></a>

<a id="e"></a>
#### 5.Github
[Android 合成兩張圖片並存到手機相簿 Github](https://github.com/MuHongWeiWei/CompositePictureDemo)
