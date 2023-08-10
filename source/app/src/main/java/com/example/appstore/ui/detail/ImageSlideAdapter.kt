package com.example.appstore.ui.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.appstore.R
import com.example.appstore.data.domain.ScreenshotDetailModel
import com.squareup.picasso.Picasso

class ImageSlideAdapter(private val context: Context, private var imageList: ArrayList<ScreenshotDetailModel>) : PagerAdapter() {
    override fun getCount(): Int {
        return imageList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view: View =  (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
            R.layout.image_slider_item, null)
        val ivImages = view.findViewById<ImageView>(R.id.iv_images)

        imageList[position].let {

            Picasso.with(context).load(it.url).fit().centerCrop()
                .placeholder(R.drawable.ic_camera)
                .centerCrop()
                .error(R.drawable.ic_camera)
                .into(ivImages)
        }


        val vp = container as ViewPager
        vp.addView(view, 0)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val vp = container as ViewPager
        val view = `object` as View
        vp.removeView(view)
    }
}