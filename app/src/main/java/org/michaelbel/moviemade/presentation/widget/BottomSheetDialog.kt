package org.michaelbel.moviemade.presentation.widget

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.annotation.NonNull
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HIDDEN
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_action.view.*
import org.michaelbel.core.adapter.ListAdapter
import org.michaelbel.core.adapter.ListItem
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.ktx.toDp

class BottomSheetDialog(private val start: Float = 0F, private val top: Float = 0F, private val end: Float = 0F, private val bottom: Float = 0F): BottomSheetDialogFragment() {

    private var adapter: ListAdapter = ListAdapter()
    var onDismissListener: OnDismissListener? = null

    private val bottomSheetBehaviorCallback: BottomSheetBehavior.BottomSheetCallback = object: BottomSheetBehavior.BottomSheetCallback() {

        override fun onStateChanged(@NonNull bottomSheet: View, newState: Int) {
            if (newState == STATE_HIDDEN) {
                onDismissListener?.onDismiss()
                dismiss()
            }
        }

        override fun onSlide(@NonNull bottomSheet: View, slideOffset: Float) {}
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        dialog.setOnShowListener {
            val bottomSheetInternal = dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            BottomSheetBehavior.from<View>(bottomSheetInternal).setState(BottomSheetBehavior.STATE_EXPANDED)
        }

        return dialog
    }

    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)

        val contentView = View.inflate(context, R.layout.dialog_action, null)
        contentView.list.adapter = adapter
        contentView.list.layoutManager = LinearLayoutManager(requireContext())
        contentView.list.setPadding(start.toDp(requireContext()), top.toDp(requireContext()), end.toDp(requireContext()), bottom.toDp(requireContext()))

        dialog.setContentView(contentView)
        val layoutParams = (contentView.parent as View).layoutParams as? CoordinatorLayout.LayoutParams?
        val behavior = layoutParams?.behavior
        if (behavior is BottomSheetBehavior<*>) {
            behavior.bottomSheetCallback = bottomSheetBehaviorCallback
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