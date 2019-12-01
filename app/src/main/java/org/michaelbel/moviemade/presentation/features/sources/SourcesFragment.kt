package org.michaelbel.moviemade.presentation.features.sources

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_lce.*
import org.michaelbel.core.Browser
import org.michaelbel.core.adapter.ListAdapter
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.ktx.getViewModel
import org.michaelbel.moviemade.ktx.reObserve
import org.michaelbel.moviemade.presentation.common.base.BaseFragment
import timber.log.Timber
import java.io.IOException
import java.io.Serializable
import java.nio.charset.Charset

class SourcesFragment: BaseFragment(R.layout.fragment_lce) {

    companion object {
        private const val FILE_NAME = "libraries.json"
    }

    data class Source(
            @SerializedName("name") val name: String? = null,
            @SerializedName("url") val url: String? = null,
            @SerializedName("license") val license: String? = null
    ): Serializable

    private val adapter = ListAdapter()
    private val viewModel: SourcesModel by lazy { getViewModel<SourcesModel>() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.apply {
            title = getString(R.string.open_source_libs)
            navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow_back)
            setNavigationOnClickListener { requireFragmentManager().popBackStack() }
        }

        val list: List<Source> = Gson().fromJson(loadJsonFromAsset(FILE_NAME), object: TypeToken<List<Source>>() {}.type)
        viewModel.init(list)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)

        viewModel.items.reObserve(viewLifecycleOwner, Observer { adapter.setItems(it) })
        viewModel.click.reObserve(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { url ->
                Browser.openUrl(requireContext(), url)
            }
        })
    }

    private fun loadJsonFromAsset(fileName: String): String? {
        return try {
            val inputStream = requireContext().assets.open(fileName)
            val buffer = ByteArray(inputStream.available())
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charset.forName("utf-8"))
        } catch (e: IOException) {
            Timber.e(e)
            return null
        }
    }
}