package org.michaelbel.moviemade.presentation.features.keywords.fragment

import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager
import kotlinx.android.synthetic.main.activity_default.*
import kotlinx.android.synthetic.main.fragment_keywords.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.entity.Keyword
import org.michaelbel.moviemade.core.utils.EmptyViewMode
import org.michaelbel.moviemade.core.utils.MOVIE_ID
import org.michaelbel.moviemade.presentation.App
import org.michaelbel.moviemade.presentation.base.BaseFragment
import org.michaelbel.moviemade.presentation.common.network.NetworkChangeReceiver
import org.michaelbel.moviemade.presentation.features.keywords.KeywordsAdapter
import org.michaelbel.moviemade.presentation.features.keywords.KeywordsContract
import org.michaelbel.moviemade.presentation.features.keywords.activity.KeywordsActivity
import javax.inject.Inject

class KeywordsFragment: BaseFragment(), KeywordsContract.View, NetworkChangeReceiver.Listener {

    companion object {
        fun newInstance(movieId: Int): KeywordsFragment {
            val args = Bundle()
            args.putInt(MOVIE_ID, movieId)

            val fragment = KeywordsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var movieId: Int = 0
    private var adapter: KeywordsAdapter? = null

    private var networkChangeReceiver: NetworkChangeReceiver? = null
    private var connectionFailure = false

    @Inject
    lateinit var presenter: KeywordsContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        networkChangeReceiver = NetworkChangeReceiver(this)
        requireContext().registerReceiver(networkChangeReceiver, IntentFilter(NetworkChangeReceiver.INTENT_ACTION))

        App[requireActivity().application].createFragmentComponent().inject(this)
        presenter.attach(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_keywords, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as KeywordsActivity).toolbar.setOnClickListener { recycler_view.smoothScrollToPosition(0) }

        adapter = KeywordsAdapter()

        recycler_view.adapter = adapter
        recycler_view.emptyView = empty_view
        recycler_view.layoutManager = ChipsLayoutManager.newBuilder(requireContext()).setOrientation(ChipsLayoutManager.HORIZONTAL).build()
        recycler_view.setOnItemClickListener { _, position ->
            val keyword = adapter!!.keywords[position]
            (requireActivity() as KeywordsActivity).startKeyword(keyword)
        }

        empty_view.setOnClickListener {
            empty_view.visibility = GONE
            progress_bar.visibility = VISIBLE
            presenter.getKeywords(movieId)
        }

        movieId = if (arguments != null) arguments!!.getInt(MOVIE_ID) else 0
        presenter.getKeywords(movieId)
    }

    override fun setKeywords(keywords: List<Keyword>) {
        connectionFailure = false
        adapter!!.addKeywords(keywords)
        progress_bar.visibility = GONE
    }

    override fun setError(@EmptyViewMode mode: Int) {
        connectionFailure = true
        empty_view.visibility = VISIBLE
        empty_view.setMode(mode)
        progress_bar.visibility = GONE
    }

    override fun onNetworkChanged() {
        if (connectionFailure && adapter!!.itemCount == 0) {
            presenter.getKeywords(movieId)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        requireContext().unregisterReceiver(networkChangeReceiver)
        presenter.destroy()
    }
}