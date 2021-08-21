package org.michaelbel.moviemade.presentation.widget

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.NonNull
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.michaelbel.core.adapter.ListAdapter
import org.michaelbel.core.adapter.ListItem
import org.michaelbel.moviemade.databinding.DialogActionBinding
import org.michaelbel.moviemade.ktx.toDp

class BottomSheetDialog(
    private val start: Float = 0F,
    private val top: Float = 0F,
    private val end: Float = 0F,
    private val bottom: Float = 0F
): BottomSheetDialogFragment() {

    private var adapter: ListAdapter = ListAdapter()
    var onDismissListener: OnDismissListener? = null

    private var _binding: DialogActionBinding? = null
    private val binding get() = _binding!!

    private val bottomSheetBehaviorCallback = object: BottomSheetBehavior.BottomSheetCallback() {

        override fun onStateChanged(@NonNull bottomSheet: View, newState: Int) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                onDismissListener?.onDismiss()
                dismiss()
            }
        }

        override fun onSlide(@NonNull bottomSheet: View, slideOffset: Float) {}
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogActionBinding.inflate(LayoutInflater.from(requireContext()))

        val dialog = super.onCreateDialog(savedInstanceState)

        dialog.setOnShowListener {
            val bottomSheetInternal = dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            BottomSheetBehavior.from<View>(bottomSheetInternal).setState(BottomSheetBehavior.STATE_EXPANDED)
        }

        return dialog
    }

    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)

        binding.list.adapter = adapter
        binding.list.layoutManager = LinearLayoutManager(requireContext())
        binding.list.setPadding(
            start.toDp(requireContext()),
            top.toDp(requireContext()),
            end.toDp(requireContext()),
            bottom.toDp(requireContext())
        )

        dialog.setContentView(binding.root)
        val layoutParams = (binding.root as View).layoutParams as? CoordinatorLayout.LayoutParams?
        val behavior = layoutParams?.behavior
        if (behavior is BottomSheetBehavior<*>) {
            behavior.addBottomSheetCallback(bottomSheetBehaviorCallback)
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        onDismissListener?.onDismiss()
    }

    fun addItem(listItem: ListItem) {
        adapter.setItems(arrayListOf(listItem))
    }

    interface OnDismissListener {
        fun onDismiss()
    }
}