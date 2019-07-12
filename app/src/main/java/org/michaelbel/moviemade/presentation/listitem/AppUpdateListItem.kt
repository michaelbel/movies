package org.michaelbel.moviemade.presentation.listitem

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.listitem_app_update.view.*
import org.michaelbel.core.adapter.ListItem
import org.michaelbel.core.adapter.ViewTypes.APP_UPDATE_ITEM
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.ViewUtil
import org.michaelbel.moviemade.presentation.common.DebouncingOnClickListener
import java.io.Serializable

data class AppUpdateListItem(internal var data: Data): ListItem {

    private var animator: ObjectAnimator? = null

    data class Data(internal var listener: Listener): Serializable

    interface Listener {
        fun onClick() {}
    }

    override fun getData() = data

    override fun getViewType() = APP_UPDATE_ITEM

    override fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.listitem_app_update, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.text.setText(R.string.update_available)
        holder.itemView.value.setText(R.string.update_available_summary)
        holder.itemView.iconView.setImageDrawable(ViewUtil.getIcon(holder.itemView.context, R.drawable.ic_update, R.color.accent_green))

        holder.itemView.setOnClickListener(object: DebouncingOnClickListener() {
            override fun doClick(v: View) {
                if (holder.adapterPosition != NO_POSITION) {
                    getData().listener.onClick()
                }
            }
        })
    }

    override fun setActive(itemView: View, position: Int) {
        animateIcon(itemView.iconView)
    }

    override fun setInactivate(itemView: View, position: Int) {
        animator?.cancel()
        animator = null
    }

    private fun animateIcon(view: AppCompatImageView) {
        animator = ObjectAnimator.ofPropertyValuesHolder(view, PropertyValuesHolder.ofFloat("scaleX", 1.15F), PropertyValuesHolder.ofFloat("scaleY", 1.15F))
        animator?.repeatCount = ObjectAnimator.INFINITE
        animator?.repeatMode = ObjectAnimator.REVERSE
        animator?.duration = 750L
        animator?.start()
    }

    class ViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer
}