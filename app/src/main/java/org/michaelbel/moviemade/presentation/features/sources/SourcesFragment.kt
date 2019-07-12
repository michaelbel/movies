package org.michaelbel.moviemade.presentation.features.sources

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_lce.*
import org.michaelbel.core.adapter.ListAdapter
import org.michaelbel.core.customtabs.Browser
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.presentation.common.base.BaseFragment
import java.io.IOException
import java.io.Serializable
import java.nio.charset.Charset

class SourcesFragment: BaseFragment() {

    companion object {
        private const val FILE_NAME = "libraries.json"

        internal fun newInstance() = SourcesFragment()
    }

    data class Source(
            @Expose @SerializedName("name") val name: String,
            @Expose @SerializedName("url") val url: String,
            @Expose @SerializedName("license") val license: String
    ): Serializable

    private val adapter = ListAdapter()
    private lateinit var viewModel: SourcesModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(requireActivity()).get(SourcesModel::class.java)
        return inflater.inflate(R.layout.fragment_lce, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.title = getString(R.string.open_source_libs)
        toolbar.navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener { requireFragmentManager().popBackStack() }

        val list: List<Source> = Gson().fromJson(loadJsonFromAsset(FILE_NAME), object: TypeToken<List<Source>>() {}.type)
        viewModel.init(list)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)

        viewModel.items.observe(viewLifecycleOwner, Observer { adapter.setItems(it) })
        viewModel.click.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { url ->
                Browser.openUrl(requireContext(), url)
            }
        })
    }

    private fun loadJsonFromAsset(fileName: String): String {
        return try {
            val inputStream = requireContext().assets.open(fileName)
            val buffer = ByteArray(inputStream.available())
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charset.forName("utf-8"))
        } catch (ex: IOException) {
            return ""
        }
    }
}