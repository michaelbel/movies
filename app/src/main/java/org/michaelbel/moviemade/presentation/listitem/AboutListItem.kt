package org.michaelbel.moviemade.presentation.listitem

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.michaelbel.core.adapter.ListItem
import org.michaelbel.core.adapter.ViewTypes.ABOUT_ITEM
import org.michaelbel.moviemade.BuildConfig
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.time.DateUtil
import org.michaelbel.moviemade.databinding.ListitemAboutBinding

data class AboutListItem(private val nothing: Nothing? = null): ListItem {

    override val id: Long
        get() = RecyclerView.NO_ID

    override val viewType: Int
        get() = ABOUT_ITEM

    override fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(ListitemAboutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as ViewHolder
        val context = holder.itemView.context

        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        val versionName = packageInfo.versionName
        val versionCode = packageInfo.versionCode
        val versionDate = DateUtil.formatSystemTime(BuildConfig.VERSION_DATE)

        holder.binding.appName.text = context.getString(R.string.app_for_android, context.getString(R.string.app_name))
        holder.binding.versionText.text = context.getString(R.string.version_build_date, versionName, versionCode, versionDate)
    }

    private inner class ViewHolder(val binding: ListitemAboutBinding): RecyclerView.ViewHolder(binding.root)
}