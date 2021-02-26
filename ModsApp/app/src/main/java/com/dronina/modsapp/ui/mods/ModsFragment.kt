package com.dronina.modsapp.ui.mods

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dronina.modsapp.R
import com.dronina.modsapp.data.entities.Favorite
import com.dronina.modsapp.data.entities.FavoriteMod
import com.dronina.modsapp.data.entities.Mod
import com.dronina.modsapp.ui.base.BaseViewModelFactory
import com.dronina.modsapp.ui.main.MainViewModel
import com.dronina.modsapp.utils.VerticalSpaceItemDecoration
import com.dronina.modsapp.utils.doNothingOnBackPressed
import kotlinx.android.synthetic.main.fragment_mods.*


class ModsFragment : Fragment(), ModsRvAdapter.OnItemClickListener {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var viewModel: ModsViewModel
    private var recyclerViewAdapter: ModsRvAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mods, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        doNothingOnBackPressed()

        mainViewModel = ViewModelProviders
            .of(requireActivity(), BaseViewModelFactory(requireActivity()))
            .get(MainViewModel::class.java)
        viewModel = ViewModelProviders
            .of(this, BaseViewModelFactory(requireActivity()))
            .get(ModsViewModel::class.java)
        viewModel.view = this
        recyclerViewAdapter = null

        mainViewModel.mods.observe(viewLifecycleOwner, { mods ->
            if (recyclerViewAdapter == null) {
                setRecyclerView(mods)
            } else {
                recyclerViewAdapter?.update(mods)
            }
        })

    }

    private fun setRecyclerView(mods: List<FavoriteMod>) {
        rv_mods.setHasFixedSize(true)
        rv_mods.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewAdapter = ModsRvAdapter(this, requireActivity())
        rv_mods.addItemDecoration(VerticalSpaceItemDecoration(64))
        recyclerViewAdapter?.update(mods)
        rv_mods.adapter = recyclerViewAdapter

        if (viewModel.scrollPosition != 0) {
            rv_mods.scrollToPosition(viewModel.scrollPosition)
        }
    }

    override fun onItemClick(mod: FavoriteMod?) {
        viewModel.onItemClick(mod)
    }

    override fun onFavoriteClick(mod: FavoriteMod?) {
        viewModel.onFavoriteClick(mod)
    }

    fun navigateDetailsPage(bundle: Bundle) {
        findNavController().navigate(R.id.action_mods_to_details, bundle)
    }

    fun dataUpdated() {
        mainViewModel.onCreate()
    }

    override fun onStop() {
        super.onStop()
        rv_mods.layoutManager?.let { layoutManager ->
            viewModel.scrollPosition =
                (layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
        }
    }
}