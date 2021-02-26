package com.dronina.modsapp.ui.details

import android.app.Activity
import android.graphics.drawable.Animatable
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModelProviders
import com.dronina.modsapp.R
import com.dronina.modsapp.data.entities.Image
import com.dronina.modsapp.utils.IMAGE_BUNDLE
import com.dronina.modsapp.utils.printImage
import com.dronina.modsapp.utils.printImageForViewPager

class ImageFragment : Fragment() {
    private var imageView: ImageView? = null
    private var image: Image? = null

    companion object {
        fun newInstance(image: Image): ImageFragment {
            val args = Bundle()
            args.putParcelable(IMAGE_BUNDLE, image)
            val fragment = ImageFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        bundle: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_image, container, false)
        view.setup()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageView = view.findViewById(R.id.iv_image)
        requireActivity().printImageForViewPager(image.toString(), imageView)
    }

    private fun View.setup() {
        image = arguments?.getParcelable(IMAGE_BUNDLE)

    }
}