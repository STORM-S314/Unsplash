package xjtu.graphics.unsplash.ui.dashboard

import android.app.Dialog
import android.app.WallpaperManager
import android.content.Context
import android.content.Intent
import android.database.DataSetObserver
import android.graphics.BitmapFactory
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Button
import android.widget.GridView
import android.widget.ImageView
import android.widget.ListAdapter
import xjtu.graphics.unsplash.Image
import xjtu.graphics.unsplash.R

class ImageAdapter(private val imageLists :List<Image>, private val context: Context): ListAdapter {
    override fun registerDataSetObserver(p0: DataSetObserver?) {

    }

    override fun unregisterDataSetObserver(p0: DataSetObserver?) {

    }

    override fun getCount(): Int {
        return imageLists.size
    }

    override fun getItem(index: Int): Any {
        return imageLists[index]
    }

    override fun getItemId(index: Int): Long {
        return index.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getView(index: Int, p1: View?, p2: ViewGroup?): View {
        val img = imageLists[index]
        val imageView = ImageView(context)
        imageView.setImageBitmap(img.getImage())
        imageView.layoutParams = AbsListView.LayoutParams(300,300)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        imageView.setOnClickListener {
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.image_popup_layout)
            val popupImageView = dialog.findViewById<ImageView>(R.id.popup_image_view)
            popupImageView.setImageBitmap(img.getImage())
            val btnShare = dialog.findViewById<Button>(R.id.btn_share)
            btnShare.setOnClickListener {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "image/*"
                shareIntent.putExtra(Intent.EXTRA_STREAM,imageLists[index].getUri())
                context.startActivity(Intent.createChooser(shareIntent,"Share Image to ...."))
            }
            val btnWallpaper = dialog.findViewById<Button>(R.id.btn_wallpaper)
            btnWallpaper.setOnClickListener{
                val wallpaperManager = WallpaperManager.getInstance(context)
                val bitmap = BitmapFactory.decodeStream(context.contentResolver.openInputStream(imageLists[index].getUri()))
                wallpaperManager.setBitmap(bitmap)
            }
            dialog.show()
        }
        return imageView
    }

    override fun getItemViewType(p0: Int): Int {
        return 1
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun isEmpty(): Boolean {
        return false
    }

    override fun areAllItemsEnabled(): Boolean {
        return true
    }

    override fun isEnabled(p0: Int): Boolean {
        return true
    }
}