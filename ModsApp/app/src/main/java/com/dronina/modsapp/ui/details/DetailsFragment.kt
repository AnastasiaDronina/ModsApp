package com.dronina.modsapp.ui.details

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.dronina.modsapp.R
import com.dronina.modsapp.ui.base.BaseViewModelFactory
import com.dronina.modsapp.utils.*
import kotlinx.android.synthetic.main.fragment_details.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest


class DetailsFragment : Fragment(), EasyPermissions.PermissionCallbacks {
    private lateinit var viewModel: DetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders
            .of(this, BaseViewModelFactory(requireActivity()))
            .get(DetailsViewModel::class.java)
        viewModel.view = this
        viewModel.onViewCreated(arguments)

        viewModel.currentMod.observe(viewLifecycleOwner, { mod ->
            tv_details_title.text = mod.showLongTitle()
            tv_details_description.text = mod.showLongDescription()
            view_pager.adapter = ViewPagerAdapter(this, mod.images)
        })

        viewModel.isDownloaded.observe(viewLifecycleOwner, { isDownloaded ->
            if (isDownloaded) btn_download.text = getString(R.string.install)
        })

        btn_download.setOnClickListener {
            viewModel.isDownloaded.value?.let { isDownloaded ->
                if (!isDownloaded) {
                    btn_download.text = getString(R.string.downloading)
                    viewModel.downloadClicked()
                } else {
                    viewModel.installClicked()
                }
            }
        }
        copyAssets()
    }

    override fun startActivity(intent: Intent) {
        try {
            requireContext().startActivity(intent)
        } catch (e: Exception) {
            try {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=com.mojang.minecraftpe")
                    )
                )
            } catch (e: android.content.ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=com.mojang.minecraftpe")
                    )
                )
            }
        }
    }


    @AfterPermissionGranted(RC_WRITE_STORAGE)
    private fun copyAssets() {
        val permission = Manifest.permission.WRITE_EXTERNAL_STORAGE
        if (EasyPermissions.hasPermissions(requireContext(), permission)) {
            viewModel.copyAssets()
        } else {
            EasyPermissions.requestPermissions(
                PermissionRequest.Builder(this, RC_WRITE_STORAGE, permission)
                    .build()
            )
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        viewModel.copyAssets()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
    }
}