package org.michaelbel.moviemade.presentation.features.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_default.*
import kotlinx.android.synthetic.main.fragment_keywords.*
import kotlinx.android.synthetic.main.view_cell_details.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.customtabs.Browser
import org.michaelbel.moviemade.presentation.base.BaseFragment
import org.michaelbel.moviemade.presentation.common.DebouncingOnClickListener
import java.util.*

class LibsFragment: BaseFragment() {

    data class Source(val name: String, val url: String, val license: String)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AboutActivity).toolbar.setNavigationOnClickListener {
            (requireActivity() as AboutActivity).supportFragmentManager.popBackStack()
        }
        (requireActivity() as AboutActivity).supportActionBar?.setTitle(R.string.open_source_libs)

        val adapter = LibsAdapter()
        adapter.addSource("BottomSheet", "https://github.com/michaelbel/bottomsheet")
        adapter.addSource("Gson", "https://github.com/google/gson")
        adapter.addSource("Retrofit", "https://square.github.io/retrofit")
        adapter.addSource("RxJava", "https://github.com/reactivex/rxjava")
        adapter.addSource("Picasso", "https://square.github.io/picasso")
        adapter.addSource("GestureViews", "https://github.com/alexvasilkov/gestureviews")
        adapter.addSource("ChipsLayoutManager", "https://github.com/beloos/chipslayoutmanager")
        adapter.addSource("ExpandableTextView", "https://github.com/blogcat/android-expandabletextview")
        adapter.addSource("Android Animated Menu Items", "https://github.com/adonixis/android-animated-menu-items")

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
    }

    private inner class LibsAdapter: RecyclerView.Adapter<LibsAdapter.LibsViewHolder>() {

        private val sources = ArrayList<Source>()

        fun addSource(name: String, url: String, license: String = "Apache License 2.0") {
            sources.add(Source(name, url, license))
            notifyItemInserted(sources.size - 1)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibsViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.view_cell_details, parent, false)
            return LibsViewHolder(view)
        }

        override fun onBindViewHolder(holder: LibsViewHolder, position: Int) {
            holder.bind(sources[position])
        }

        override fun getItemCount() = sources.size

        private inner class LibsViewHolder(override val containerView: View):
                RecyclerView.ViewHolder(containerView), LayoutContainer {

            fun bind(source: Source) {
                text.text = source.name
                value.text = source.license
                divider.visibility = if (adapterPosition != sources.size - 1) VISIBLE else GONE

                containerView.setOnClickListener(object: DebouncingOnClickListener() {
                    override fun doClick(v: View) {
                        if (adapterPosition != RecyclerView.NO_POSITION) {
                            Browser.openUrl(requireContext(), sources[adapterPosition].url)
                        }
                    }
                })
            }
        }
    }
}