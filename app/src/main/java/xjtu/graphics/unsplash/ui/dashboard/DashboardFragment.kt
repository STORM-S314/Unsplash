package xjtu.graphics.unsplash.ui.dashboard

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.core.content.FileProvider
import androidx.core.content.FileProvider.getUriForFile
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import xjtu.graphics.unsplash.Image
import xjtu.graphics.unsplash.databinding.FragmentDashboardBinding
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private lateinit var  gridContainer: GridView
    private val imageLists = mutableListOf<Image>()
    private lateinit var  context:Context
    private val showImage: Int = 1

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        gridContainer = binding.gridContainer
        context = root.context
        Thread{initImage(context)}.start()
        //TODO: 依据用户滑动动作，分页加载图片 gridContainer.setOnScrollListener()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private val handler = object : Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            when(msg.what){
                showImage -> showImage()
            }
        }
    }
    private fun initImage(context: Context){
        val imgDir = context.filesDir
        if(!imgDir.exists()){
            imgDir.mkdir()
            imgDir.setReadable(true)
            imgDir.setWritable(true)
        }
        val imgs = imgDir.listFiles { f -> f.extension == "jpg" }
        val executorService = Executors.newCachedThreadPool()
        for (img: File in imgs) {
                executorService.execute {
                    val opt = BitmapFactory.Options()
                    opt.inSampleSize = 20
                    val bm = BitmapFactory.decodeFile(img.path, opt)
                    println("init ${img.path} ${img.length()} ")
                    val uri = getUriForFile(context, "xjtu.graphics.unsplash.fileProvider", img)
                    imageLists.add(
                        Image(
                            bm,
                            img.name,
                            img.path,
                            uri,
                            img.length() / (1024.0 * 1024.0)
                        )
                    )
                    val msg = Message()
                    msg.what = showImage
                    handler.sendMessage(msg)
                }
        }
    }
    private fun showImage(){
        val imageAdapter = ImageAdapter(imageLists,context)
        gridContainer.adapter = imageAdapter
    }
}