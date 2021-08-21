package org.michaelbel.moviemade.presentation.features.sources

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.michaelbel.core.Browser
import org.michaelbel.core.adapter.ListAdapter
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.databinding.FragmentLceBinding
import org.michaelbel.moviemade.ktx.launchAndRepeatWithViewLifecycle
import org.michaelbel.moviemade.presentation.common.base.BaseFragment
import timber.log.Timber
import java.io.IOException
import java.io.Serializable
import java.nio.charset.Charset

@AndroidEntryPoint
class SourcesFragment: BaseFragment(R.layout.fragment_lce) {

    private val viewModel: SourcesModel by viewModels()
    private val binding: FragmentLceBinding by viewBinding()

    private val listAdapter = ListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.run {
            title = getString(R.string.open_source_libs)
            navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow_back)
            setNavigationOnClickListener { requireFragmentManager().popBackStack() }
        }
        binding.recyclerView.run {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        val list: List<Source> = Gson().fromJson(loadJsonFromAsset(FILE_NAME), object: TypeToken<List<Source>>() {}.type)
        viewModel.init(list)

        launchAndRepeatWithViewLifecycle {
            launch {
                viewModel.content.collect { listAdapter.setItems(it) }
            }
            launch { viewModel.click.collect { Browser.openUrl(requireContext(), it) } }
        }
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

    data class Source(
        @SerializedName("name") val name: String? = null,
        @SerializedName("url") val url: String? = null,
        @SerializedName("license") val license: String? = null
    ): Serializable

    private companion object {
        private const val FILE_NAME = "libraries.json"
    }
}