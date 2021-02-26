package com.dronina.modsapp.ui.details

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.dronina.modsapp.data.entities.Image

class ViewPagerAdapter(
    fragment: Fragment,
    private val images: List<Image>
) :
    FragmentStatePagerAdapter(
        fragment.childFragmentManager,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    ) {
    override fun getCount(): Int {
        return images.size
    }

    override fun getItem(position: Int): Fragment {
        return ImageFragment.newInstance(images[position])
    }
}