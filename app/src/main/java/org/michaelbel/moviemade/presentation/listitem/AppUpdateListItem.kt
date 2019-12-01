package org.michaelbel.moviemade.presentation.listitem

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator.INFINITE
import android.animation.ValueAnimator.REVERSE
import android.view.LayoutInflater
import android.view.View
import android.view.View.SCALE_X
import android.view.View.SCALE_Y
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.listitem_app_update.view.*
import org.michaelbel.core.adapter.ListItem
import org.michaelbel.core.adapter.ViewTypes.APP_UPDATE_ITEM
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.ktx.setIcon
import org.michaelbel.moviemade.presentation.common.DebouncingOnClickListener
import java.io.Serializable

data class AppUpdateListItem(private val data: Data): ListItem {

    data class Data(var downloadMode: Boolean = false): Serializable

    private var viewHolder: ViewHolder? = null

    private var animator: ObjectAnimator? = null

    var listener: Listener? = null

    interface Listener {
        fun onClick() {}
    }

    override val id: Long
        get() = RecyclerView.NO_ID

    override val viewType: Int
        get() = APP_UPDATE_ITEM

    override fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        viewHolder = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.listitem_app_update, parent, false))
        return viewHolder as ViewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.text.setText(R.string.update_available)
        holder.itemView.summary.setText(R.string.update_available_summary)
        holder.itemView.iconView.setIcon(R.drawable.ic_update, R.color.accent_green)

        holder.itemView.setOnClickListener(object: DebouncingOnClickListener() {
            override fun doClick(v: View) {
                if (holder.adapterPosition != NO_POSITION) {
                    listener?.onClick()
                }
            }
        })
    }

    override fun activate(itemView: View, position: Int) {
        animateIcon(itemView.iconView)
    }

    override fun deactivate(itemView: View, position: Int) {
        animator?.cancel()
        animator = null
    }

    private fun animateIcon(view: AppCompatImageView) {
        animator = ObjectAnimator.ofPropertyValuesHolder(view,
                PropertyValuesHolder.ofFloat(SCALE_X, 1.15F),
                PropertyValuesHolder.ofFloat(SCALE_Y, 1.15F)
        ).apply {
            repeatCount = INFINITE
            repeatMode = REVERSE
            duration = 750L
            start()
        }
    }

    private inner class ViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer
}