package com.dronina.modsapp.ui.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dronina.modsapp.R
import com.dronina.modsapp.data.entities.FavoriteMod
import com.dronina.modsapp.ui.base.BaseViewModelFactory
import com.dronina.modsapp.ui.main.MainViewModel
import com.dronina.modsapp.ui.mods.ModsRvAdapter
import com.dronina.modsapp.ui.mods.ModsViewModel
import com.dronina.modsapp.utils.VerticalSpaceItemDecoration
import kotlinx.android.synthetic.main.fragment_favorites.*
import kotlinx.android.synthetic.main.fragment_mods.*

class FavoritesFragment : Fragment(), FavoritesRvAdapter.OnItemClickListener {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var viewModel: FavoriteViewModel
    private var recyclerViewAdapter: FavoritesRvAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel = ViewModelProviders
            .of(requireActivity(), BaseViewModelFactory(requireActivity()))
            .get(MainViewModel::class.java)
        viewModel = ViewModelProviders
            .of(this, BaseViewModelFactory(requireActivity()))
            .get(FavoriteViewModel::class.java)
        viewModel.view = this
        recyclerViewAdapter = null

        mainViewModel.favoriteMods.observe(viewLifecycleOwner, { mods ->
            if (recyclerViewAdapter == null) {
                setRecyclerView(mods)
            } else {
                recyclerViewAdapter?.update(mods)
            }
        })
    }

    private fun setRecyclerView(mods: List<FavoriteMod>) {
        rv_favorites.setHasFixedSize(true)
        rv_favorites.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewAdapter = FavoritesRvAdapter(this, requireActivity())
        rv_favorites.addItemDecoration(VerticalSpaceItemDecoration(64))
        recyclerViewAdapter?.update(mods)
        rv_favorites.adapter = recyclerViewAdapter

        if (viewModel.scrollPosition != 0) {
            rv_favorites.scrollToPosition(viewModel.scrollPosition)
        }
    }

    override fun onItemClick(mod: FavoriteMod?) {
        viewModel.onItemClick(mod)
    }

    override fun onFavoriteClick(mod: FavoriteMod?) {
        viewModel.onFavoriteClick(mod)
    }

    fun navigateDetailsPage(bundle: Bundle) {
        findNavController().navigate(R.id.action_favorites_to_details, bundle)
    }

    fun dataUpdated() {
        mainViewModel.onCreate()
    }

    override fun onStop() {
        super.onStop()
        rv_favorites.layoutManager?.let { layoutManager ->
            viewModel.scrollPosition =
                (layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
        }
    }
}