package com.dronina.modsapp.ui.favorites

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dronina.modsapp.R
import com.dronina.modsapp.data.entities.FavoriteMod
import com.dronina.modsapp.utils.TAG
import com.dronina.modsapp.utils.printImage
import com.dronina.modsapp.utils.showDescription
import com.dronina.modsapp.utils.showTitle

class FavoritesRvAdapter(
    private var listener: OnItemClickListener,
    private var activity: Activity
) : ListAdapter<FavoriteMod, FavoritesRvAdapter.ModsViewHolder>(ModDiffCallback()) {
    var list: ArrayList<FavoriteMod> = ArrayList()

    interface OnItemClickListener {
        fun onItemClick(mod: FavoriteMod?)
        fun onFavoriteClick(mod: FavoriteMod?)
    }

    fun update(list: List<FavoriteMod>) {
        super.submitList(list)
        this.list.clear()
        this.list.addAll(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModsViewHolder {
        return ModsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.mod_rv_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ModsViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ModDiffCallback : DiffUtil.ItemCallback<FavoriteMod>() {
        override fun areItemsTheSame(oldItem: FavoriteMod, newItem: FavoriteMod): Boolean =
            oldItem.toString() == newItem.toString()

        override fun areContentsTheSame(oldItem: FavoriteMod, newItem: FavoriteMod): Boolean =
            oldItem.toString() == newItem.toString()
    }

    inner class ModsViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private lateinit var mod: FavoriteMod
        private var tvModTitle: TextView? = null
        private var tvModText: TextView? = null
        private var ivModImage: ImageView? = null
        private var btnFavorite: ImageButton? = null

        init {
            tvModTitle = view.findViewById(R.id.tv_item_title)
            tvModText = view.findViewById(R.id.tv_item_text)
            ivModImage = view.findViewById(R.id.iv_mod)
            btnFavorite = view.findViewById(R.id.btn_favorite)
        }

        fun bind(mod: FavoriteMod) {
            this.mod = mod
            tvModTitle?.text = mod.showTitle()
            tvModText?.text = mod.showDescription()
            activity.printImage(mod.images[0].toString(), ivModImage)
            btnFavorite?.setImageDrawable(activity.resources.getDrawable(R.drawable.ic_favorites_rotated))
            itemView.setOnClickListener(this)
            btnFavorite?.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            when (view) {
                btnFavorite -> {
                    mod.favorite = !mod.favorite
                    val updatedList = ArrayList<FavoriteMod>()
                    updatedList.addAll(list)
                    updatedList.remove(mod)
                    update(updatedList)
                    listener.onFavoriteClick(mod)
                }
                itemView -> listener.onItemClick(mod)
            }
        }
    }
}