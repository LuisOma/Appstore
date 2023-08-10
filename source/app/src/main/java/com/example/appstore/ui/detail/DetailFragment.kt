package com.example.appstore.ui.detail

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.appstore.R
import com.example.appstore.base.BaseFragment
import com.example.appstore.data.domain.AppsDetailModel
import com.example.appstore.data.domain.CommentsDetailModel
import com.example.appstore.data.domain.ScreenshotDetailModel
import com.example.appstore.databinding.FragmentDetailBinding
import com.example.appstore.ui.util.Constants.Companion.APP
import com.squareup.picasso.Picasso
import me.relex.circleindicator.CircleIndicator


class DetailFragment : DialogFragment() {
    var appInfo: AppsDetailModel? = null
    lateinit var viewPagerAdapter: ImageSlideAdapter
    lateinit var indicator: CircleIndicator

    private val commentsAdapter by lazy {
        CommentsAdapter()
    }

    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.WRAP_CONTENT
            val height = ViewGroup.LayoutParams.WRAP_CONTENT
            dialog.window?.setLayout(width, height)
        }
    }

    fun initComponents(savedInstanceState: Bundle?) {
        arguments?.let {
            appInfo = it.getParcelable(APP)
        }
        val items =  getComments(appInfo?.comments)
        commentsAdapter.apply {
            addItems(items)
        }
        binding.commentsListRecycler.adapter = commentsAdapter
        Picasso.with(requireContext()).load(appInfo?.image).fit().centerCrop()
            .placeholder(R.drawable.ic_camera)
            .error(R.drawable.ic_camera)
            .into(binding.appImage)

        binding.titleDetailTextview.text = appInfo?.nombre
        binding.devDetailTextview.text = appInfo?.dev
        binding.descriptionDetailTextview.text = appInfo?.description
        if((appInfo?.price ?: 0.0) >= 0.5) {
            binding.priceDetailTextview.text = "$ ${appInfo?.price.toString()}"
        }
        binding.ratingDetailTextview.text = appInfo?.rating.toString()
        val drawableReview: Drawable = binding.ratingBar.progressDrawable
        if ((appInfo?.rating ?: 0.0) >= 3.0) {
            drawableReview.setColorFilter(Color.parseColor("#FF4CAF50"), PorterDuff.Mode.SRC_ATOP)
            binding.ratingDetailTextview.setTextColor(requireContext().getColor(R.color.green_500))
        } else {
            drawableReview.setColorFilter(Color.parseColor("#FFF44336"), PorterDuff.Mode.SRC_ATOP)
            binding.ratingDetailTextview.setTextColor(requireContext().getColor(R.color.red_500))
        }
        binding.ratingBar.rating = (appInfo?.rating ?: 0.0).toFloat()
        if(appInfo?.isInstalled == true){
            binding.installButton.visibility = View.GONE
        } else {
            binding.installButton.visibility = View.VISIBLE
        }
        binding.closeButton.setOnClickListener {
            dismiss()
        }
        getImages(appInfo?.screenShotsList).let {
            if(it.isNullOrEmpty()){
                binding.viewpager.visibility = View.GONE
                binding.indicator.visibility = View.GONE
            }else{
            viewPagerAdapter = ImageSlideAdapter(requireContext(), it)
            binding.viewpager.adapter = viewPagerAdapter
            binding.indicator.setViewPager(binding.viewpager)}
        }
    }

    private fun getComments(comments: List<CommentsDetailModel>?): ArrayList<CommentsDetailModel> {
        val lists = ArrayList<CommentsDetailModel>()
        if (comments != null) {
            for (s in comments) {
                lists.add(CommentsDetailModel(s.idComment, s.comment, s.imageUser))
            }
        }
        return lists
    }

    private fun getImages(images: List<ScreenshotDetailModel>?): ArrayList<ScreenshotDetailModel> {
        val lists = ArrayList<ScreenshotDetailModel>()
        if (images != null) {
            for (s in images) {
                lists.add(ScreenshotDetailModel(s.idImage, s.url))
            }
        }
        return lists
    }
}
