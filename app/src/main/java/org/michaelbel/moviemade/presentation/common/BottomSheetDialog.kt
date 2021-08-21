package org.michaelbel.moviemade.presentation.common

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.local.SharedPrefs.KEY_ACCOUNT_BACKDROP
import org.michaelbel.moviemade.databinding.DialogBackdropBinding
import org.michaelbel.moviemade.ktx.argumentDelegate
import org.michaelbel.moviemade.ktx.toast
import javax.inject.Inject

class BottomSheetDialog: BottomSheetDialogFragment() {

    @Inject lateinit var preferences: SharedPreferences

    private val path: String? by argumentDelegate()
    private val binding: DialogBackdropBinding by viewBinding()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_backdrop, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.setBtn.setOnClickListener {
            preferences.edit().putString(KEY_ACCOUNT_BACKDROP, path).apply()
            toast(R.string.msg_done)
            dismiss()
        }

        binding.cancelBtn.setOnClickListener { dismiss() }
    }

    companion object {
        fun newInstance(path: String): BottomSheetDialog {
            return BottomSheetDialog().apply {
                arguments = bundleOf("path" to path)
            }
        }
    }
}