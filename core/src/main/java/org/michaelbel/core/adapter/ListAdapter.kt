package org.michaelbel.core.adapter

import android.os.Handler
import android.os.Looper
import android.util.SparseIntArray
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ListAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: ArrayList<ListItem> = ArrayList()

    private var recyclerView: RecyclerView? = null
    private var isCancelled = false

    private val viewTypesPositions = SparseIntArray()
    private val handler = Handler(Looper.getMainLooper())
    private var pendingUpdates: Queue<Collection<ListItem>> = ArrayDeque()
    private val executorService: ExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    fun getItems(): ArrayList<ListItem> = items

    fun setItems(listItems: MutableList<ListItem>) {
        setItems(listItems, false)
    }

    fun setItems(listItems: MutableList<ListItem>, isDiffUtilEnabled: Boolean) {
        var diffUtilCallback: DiffUtilCallback? = null

        if (isDiffUtilEnabled) {
            diffUtilCallback = DiffUtilCallback(items, listItems)
        }

        setItems(listItems, diffUtilCallback)
    }

    fun setItems(listItems: MutableList<ListItem>, callback: DiffUtil.Callback?) {
        if (callback != null) {
            pendingUpdates.add(listItems)
            if (pendingUpdates.size == 1) {
                updateData(listItems, callback)
            }
        } else {
            items.clear()
            items.addAll(listItems)
            notifyDataSetChanged()
        }
    }

    fun addItem(listItem: ListItem) {
        items.add(listItem)
        notifyDataSetChanged()
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val posViewHolder = viewTypesPositions.get(viewType)
        return items[posViewHolder].getViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        items[position].onBindViewHolder(holder, position)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty() || !(items[position].onBindViewHolder(holder, position, payloads))) {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val viewType = items[position].viewType
        viewTypesPositions.put(viewType, position)
        return viewType
    }

    override fun getItemCount() = items.size

    fun getRealItemPosition(position: Int) = position

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)

        val position = holder.adapterPosition
        if (position >= 0 && position < items.size) {
            val listItem = items[position]
            listItem.activate(holder.itemView, position)
        }
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(holder)

        val position = holder.adapterPosition
        if (position >= 0 && position < items.size) {
            val listItem = items[position]
            listItem.deactivate(holder.itemView, position)
        }
    }

    override fun getItemId(position: Int): Long {
        return items[position].id
    }

    private fun updateData(newItems: Collection<ListItem>, callback: DiffUtil.Callback) {
        executorService.execute {
            val diffResult = DiffUtil.calculateDiff(callback, false)
            postDiffResults(newItems, diffResult, callback)
        }
    }

    private fun postDiffResults(newItems: Collection<ListItem>, diffResult: DiffUtil.DiffResult, callback: DiffUtil.Callback) {
        if (!isCancelled) {
            handler.post {
                if (!pendingUpdates.isEmpty())
                    pendingUpdates.remove()

                diffResult.dispatchUpdatesTo(object : ListUpdateCallback {
                    override fun onInserted(position: Int, count: Int) {
                        notifyItemRangeInserted(getRealItemPosition(position), count)
                    }

                    override fun onRemoved(position: Int, count: Int) {
                        notifyItemRangeRemoved(getRealItemPosition(position), count)
                    }

                    override fun onMoved(fromPosition: Int, toPosition: Int) {
                        notifyItemMoved(getRealItemPosition(fromPosition), getRealItemPosition(toPosition))
                    }

                    override fun onChanged(position: Int, count: Int, payload: Any?) {
                        notifyItemRangeChanged(getRealItemPosition(position), count, payload)
                    }
                })

                items.clear()
                items.addAll(newItems)

                if (pendingUpdates.size > 0) {
                    updateData(pendingUpdates.peek() as Collection<ListItem>, callback)
                }
            }
        }
    }

    inner class DiffUtilCallback(
            private var oldListItems: MutableList<ListItem>,
            private var newListItems: MutableList<ListItem>
    ): DiffUtil.Callback() {

        override fun getOldListSize() = oldListItems.size

        override fun getNewListSize() = newListItems.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldListItem = oldListItems[oldItemPosition]
            val newListItem = newListItems[newItemPosition]
            return oldListItem.id == newListItem.id && oldListItem.viewType == newListItem.viewType
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldListItem = oldListItems[oldItemPosition]
            val newListItem = newListItems[newItemPosition]
            return oldListItem == newListItem && oldListItem.hashCode() == newListItem.hashCode()
        }

        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
            val oldListItem = oldListItems[oldItemPosition]
            val newListItem = newListItems[newItemPosition]
            return oldListItem.getChangePayload(newListItem)
        }
    }
}