package xjtu.graphics.unsplash

import android.graphics.Bitmap
import android.net.Uri

class Image(private val image: Bitmap, private val name: String, private val location: String, private val uri: Uri, private val size: Double) {
    fun getImage():Bitmap{
        return image
    }
    fun getName():String{
        return name
    }
    fun getLocation():String{
        return location
    }
    fun getSize():Double{
        return size
    }
    fun getUri():Uri{
        return uri
    }
}