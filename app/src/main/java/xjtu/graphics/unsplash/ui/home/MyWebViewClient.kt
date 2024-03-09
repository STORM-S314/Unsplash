package xjtu.graphics.unsplash.ui.home

import android.graphics.Bitmap
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import java.io.File
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class MyWebViewClient:WebViewClient() {
    //TODO: 当下拉到底部时，加载网页
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        if (request != null && view != null) {
            if("jpg" in request.url.toString()){
                downloadImg(view.context.filesDir,request.url.toString())
            }else {
                view.loadUrl(request.url.toString())
            }
        }
        return true
    }
    private fun downloadImg(imgDir:File,url: String){
        Thread {
            try {
                val connection = URL(url).openConnection() as HttpsURLConnection
                connection.requestMethod = "GET"
                connection.connectTimeout = 8000
                connection.readTimeout = 8000
                if (!imgDir.exists()) {
                    imgDir.mkdir()
                    imgDir.setReadable(true)
                    imgDir.setWritable(true)
                }
                val name = url.split("&dl=")[1]
                val img = File("$imgDir/$name")
                if (img.exists()) {
                    img.delete()
                }
                connection.inputStream.buffered().copyTo(img.outputStream())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
        //TODO :下载时弹窗提示
    }
}