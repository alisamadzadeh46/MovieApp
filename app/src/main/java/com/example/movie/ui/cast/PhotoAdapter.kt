package com.example.movie.ui.cast

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.movie.R
import com.example.movie.glid.GlideApp
import com.example.movie.model.Movie
import com.example.movie.model.ProfileImage
import kotlinx.android.synthetic.main.fragment_cast.*

class PhotoAdapter : ListAdapter<ProfileImage, PhotoAdapter.PhotoViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: ProfileImage?) {
            item?.let {
                GlideApp.with(itemView)
                    .load("https://image.tmdb.org/t/p/original${item.path}")
                    .placeholder(R.drawable.ic_user)
                    .error(R.drawable.ic_user)
                    .thumbnail(0.5f)
                    .into(itemView as ImageView)
            }

        }

        companion object {
            fun from(parent: ViewGroup): PhotoViewHolder {
                val viewHolder =
                    LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
                return PhotoViewHolder(viewHolder)
            }
        }

    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<ProfileImage>() {
            override fun areItemsTheSame(oldItem: ProfileImage, newItem: ProfileImage): Boolean {
                return oldItem.path == newItem.path
            }

            override fun areContentsTheSame(oldItem: ProfileImage, newItem: ProfileImage): Boolean {
                return oldItem == newItem
            }


        }
    }


}