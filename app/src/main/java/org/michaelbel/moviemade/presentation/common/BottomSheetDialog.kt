package org.michaelbel.moviemade.presentation.common

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_backdrop.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.local.SharedPrefs.KEY_ACCOUNT_BACKDROP
import org.michaelbel.moviemade.ktx.argumentDelegate
import org.michaelbel.moviemade.ktx.toast
import org.michaelbel.moviemade.presentation.App
import javax.inject.Inject

class BottomSheetDialog: BottomSheetDialogFragment() {

    companion object {
        fun newInstance(path: String): BottomSheetDialog {
            return BottomSheetDialog().apply {
                arguments = bundleOf("path" to path)
            }
        }
    }

    private val path: String? by argumentDelegate()

    @Inject
    lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App[requireActivity().application as App].createFragmentComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_backdrop, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBtn.setOnClickListener {
            preferences.edit().putString(KEY_ACCOUNT_BACKDROP, path).apply()
            toast(R.string.msg_done)
            dismiss()
        }

        cancelBtn.setOnClickListener { dismiss() }
    }
}