package org.michaelbel.moviemade.presentation.features.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.internal.DebouncingOnClickListener
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.utils.Browser
import org.michaelbel.moviemade.presentation.base.BaseFragment
import java.util.*

class LibsFragment : BaseFragment() {

    private var activity: AboutActivity? = null

    private inner class Source(val name: String, internal val url: String, val license: String)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = getActivity() as AboutActivity?
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recycler, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity!!.getToolbar().setNavigationOnClickListener { v -> activity!!.finishFragment() }
        if (activity!!.supportActionBar != null) {
            activity!!.supportActionBar!!.setTitle(R.string.open_source_libs)
        }

        // FIXME add all sources.
        val adapter = LibsAdapter()
        adapter.addSource("BottomSheet", "https://github.com/michaelbel/bottomsheet", "Apache License 2.0")
        adapter.addSource("Gson", "https://github.com/google/gson", "Apache License 2.0")
        adapter.addSource("Retrofit", "https://square.github.io/retrofit", "Apache License 2.0")
        adapter.addSource("RxJava", "https://github.com/reactivex/rxjava", "Apache License 2.0")
        adapter.addSource("Picasso", "https://square.github.io/picasso", "Apache License 2.0")
        adapter.addSource("GestureViews", "https://github.com/alexvasilkov/gestureviews", "Apache License 2.0")
        adapter.addSource("ChipsLayoutManager", "https://github.com/beloos/chipslayoutmanager", "Apache License 2.0")
        adapter.addSource("ExpandableTextView", "https://github.com/blogcat/android-expandabletextview", "Apache License 2.0")
        adapter.addSource("Android Animated Menu Items", "https://github.com/adonixis/android-animated-menu-items", "Apache License 2.0")

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
    }

    private fun onSourceClick(source: Source) {
        Browser.openUrl(activity!!, source.url)
    }

    inner class LibsAdapter : RecyclerView.Adapter<LibsAdapter.LibsViewHolder>() {

        private val sources = ArrayList<LibsFragment.Source>()

        fun addSource(name: String, url: String, license: String) {
            sources.add(Source(name, url, license))
            notifyItemInserted(sources.size - 1)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibsViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.view_cell_details, parent, false)
            val viewHolder = LibsViewHolder(view)
            view.setOnClickListener(object : DebouncingOnClickListener() {
                override fun doClick(v: View) {
                    val position = viewHolder.adapterPosition
                    onSourceClick(sources[position])
                }
            })

            return viewHolder
        }

        override fun onBindViewHolder(holder: LibsViewHolder, position: Int) {
            val source = sources[position]
            holder.textView.text = source.name
            holder.valueView.text = source.license
            holder.dividerView.visibility = if (position != sources.size - 1) View.VISIBLE else View.GONE
        }

        override fun getItemCount(): Int {
            return sources.size
        }

        inner class LibsViewHolder (view: View) : RecyclerView.ViewHolder(view) {

            var textView: AppCompatTextView
            var valueView: AppCompatTextView
            var dividerView: View

            init {
                textView = view.findViewById(R.id.text_view)
                valueView = view.findViewById(R.id.value_text)
                dividerView = view.findViewById(R.id.divider_view)
            }
        }
    }
}