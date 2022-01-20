package org.michaelbel.moviemade.presentation.features.main.adapter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.view.forEach
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.roundToInt

class PagingItemDecoration(private val context: Context): RecyclerView.ItemDecoration() {

    private val mBounds = Rect()

    /*private val shadowDividerHeight: Float by lazy {
        context.resources.getDimension(R.dimen.vertical_space)
    }

    private val normalDividerHeight: Float by lazy {
        1F.dp(context)
    }*/

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        if (parent.layoutManager == null || parent.childCount == 0) {
            return
        }
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.layoutManager == null || parent.childCount == 0) {
            return
        }

        canvas.save()

        parent.forEach {
            var left: Int
            var right: Int

            if (parent.clipToPadding) {
                left = parent.paddingLeft
                right = parent.width - parent.paddingRight
                canvas.clipRect(
                    left,
                    parent.paddingTop,
                    right,
                    parent.height - parent.paddingBottom
                )
            } else {
                left = 0
                right = parent.width
            }

            val dividerData = getDividerData(it, parent)

            // общий цвет разделителя должен быть таким же, как цвет listitem, иначе
            // по-умолчанию будет прозрачным и отобразит цвет экрана.
            /*val backgroundDivider: Drawable = context.getColorRes(R.color.osnova_theme_skins_BgContentDefault)

            val drawable: Drawable = when (dividerData.type) {
                DividerType.Normal -> context.getColorRes(R.color.osnova_theme_skins_Background)
                DividerType.Space -> context.getColorRes(dividerData.colorRes)
            }*/

            left += dividerData.marginLeft
            right -= dividerData.marginRight

            if (dividerData.height > 0) {
                parent.getDecoratedBoundsWithMargins(it, mBounds)

                val bottom = mBounds.bottom + it.translationY.roundToInt()
                val top = bottom - dividerData.height.toInt()

                /*backgroundDivider.apply {
                    setBounds(0, top, parent.width, bottom)
                    draw(canvas)
                }
                drawable.apply {
                    setBounds(left, top, right, bottom)
                    draw(canvas)
                }*/
            }
        }

        canvas.restore()
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val dividerData = getDividerData(view, parent)
        outRect.set(0, 0, 0, dividerData.height.toInt())
    }

    /**
     * Эти элементы будут показываться только в зависимости от того,
     * разрешает ли следующий элемент разделители
     */
    private fun getDividerDataForNextItem(id: Long, itemViewType: Int): DividerPayload? {
        return when (itemViewType) {
            /*AppViewTypes.DISCOVERY_SUBSITE_V2,
            AppViewTypes.DISCOVERY_SUBSITE_V2_SMALL -> return DividerPayload(
                itemId = id,
                viewType = itemViewType,
                type = DividerType.Space,
                height = 1F.dp(context),
                colorRes = R.color.osnova_theme_skins_LineD02,
                marginLeft = 64.dp(context),
                marginRight = context.resources.getDimension(R.dimen.app_margin).toInt()
            )

            AppViewTypes.ENTRY,
            AppViewTypes.COUB,
            AppViewTypes.COMMENT_PROFILE,
            AppViewTypes.MOCK_ENTRY,
            AppViewTypes.MOCK_COMMENT_PROFILE,
            AppViewTypes.EVENT,
            AppViewTypes.VACANCY,
            AppViewTypes.RATE_APP,
            AppViewTypes.SUBSCRIBE,
            AppViewTypes.VIEWS_PROMO,
            AppViewTypes.TIMEOUT -> return DividerPayload(
                itemId = id,
                viewType = itemViewType,
                type = DividerType.Space,
                height = shadowDividerHeight,
                colorRes = R.color.osnova_theme_skins_Background
            )

            AppViewTypes.TEXT,
            AppViewTypes.FILTER,
            AppViewTypes.SUMMARY,
            AppViewTypes.ACTION_TEXT,
            AppViewTypes.ACTION_TEXT_SUMMARY -> return DividerPayload(
                itemId = id,
                viewType = itemViewType,
                type = DividerType.Space,
                height = normalDividerHeight,
                marginLeft = context.resources.getDimension(R.dimen.app_margin).toInt(),
                colorRes = R.color.osnova_theme_skins_LineD02
            )

            AppViewTypes.ACTION_TEXT_ICON -> return DividerPayload(
                itemId = id,
                viewType = itemViewType)
            AppViewTypes.PROFILE_NOTIFICATION -> return DividerPayload(
                itemId = id,
                viewType = itemViewType,
                type = DividerType.Space,
                height = normalDividerHeight,
                marginLeft = 72.dp(context),
                marginRight = context.resources.getDimension(R.dimen.app_margin).toInt(),
                colorRes = R.color.osnova_theme_skins_LineD02
            )
            AppViewTypes.SUMMARY_PROFILE -> return DividerPayload(
                itemId = id,
                viewType = itemViewType,
                type = DividerType.Space,
                height = normalDividerHeight,
                marginLeft = 51.dp(context),
                colorRes = R.color.osnova_theme_skins_LineD02
            )
            AppViewTypes.VOTER -> return DividerPayload(
                itemId = id,
                viewType = itemViewType,
                type = DividerType.Space,
                height = normalDividerHeight,
                marginLeft = 72.dp(context),
                colorRes = R.color.osnova_theme_skins_LineD02
            )
            AppViewTypes.MENTION -> return DividerPayload(
                itemId = id,
                viewType = itemViewType,
                type = DividerType.Space,
                height = 0.5F.dp(context),
                marginLeft = context.resources.getDimension(R.dimen.app_margin).toInt(),
                colorRes = R.color.osnova_theme_skins_LineD02
            )
            AppViewTypes.RATING_ITEM_V2 -> return DividerPayload(
                itemId = id,
                viewType = itemViewType,
                type = DividerType.Space,
                height = normalDividerHeight,
                marginLeft = 74.dp(context),
                marginRight = context.resources.getDimension(R.dimen.app_margin).toInt(),
                colorRes = R.color.osnova_theme_skins_LineD02
            )*/
            else -> null
        }
    }

    /**
     * Эти элементы будут показываться всегда
     */
    private fun getDividerDataForCurrentItem(id: Long, itemViewType: Int): DividerPayload? {
        return when (itemViewType) {
            /*AppViewTypes.ENTRY_FLASH -> return DividerPayload(
                itemId = id,
                viewType = itemViewType,
                type = DividerType.Space,
                height = shadowDividerHeight,
                colorRes = R.color.osnova_theme_skins_Background
            )*/
            else -> null
        }
    }

    private fun getDividerData(view: View, parent: RecyclerView): DividerPayload {
        parent.getChildViewHolder(view)

        val viewHolderPosition: Int = parent.getChildAdapterPosition(view)
        var dividerDataForItem: DividerPayload? = null

        if (parent.adapter is ConcatAdapter) {
            val concatAdapter = parent.adapter as ConcatAdapter
            val pagingAdapter = concatAdapter.adapters[0]
            /*if (pagingAdapter is PagingListAdapter) {
                var item: OsnovaListItem? = null
                var nextItem: OsnovaListItem? = null

                if (viewHolderPosition >= 0 && viewHolderPosition < pagingAdapter.itemCount) {
                    item = pagingAdapter.getListItem(viewHolderPosition)
                    nextItem = if (viewHolderPosition + 1 < pagingAdapter.itemCount) {
                        pagingAdapter.getListItem(viewHolderPosition + 1)
                    } else {
                        null
                    }
                }

                if (item != null && nextItem != null && nextItem.showTopDivider()) {
                    dividerDataForItem = getDividerDataForNextItem(
                        item.getId(),
                        item.getViewType()
                    )
                }

                if (dividerDataForItem == null && item != null) {
                    dividerDataForItem = getDividerDataForCurrentItem(
                        item.getId(),
                        item.getViewType()
                    )
                }
            }*/
        }

        return dividerDataForItem ?: DividerPayload(type = DividerType.Normal)
    }

    data class DividerPayload(
        val viewType: Int = -1,
        val itemId: Long = -1,
        val type: DividerType = DividerType.Normal,
        val height: Float = 0F,
        val marginLeft: Int = 0,
        val marginRight: Int = 0,
        @ColorRes val colorRes: Int = android.R.color.transparent
    )

    sealed class DividerType {
        object Space: DividerType()
        object Normal: DividerType()
    }
}