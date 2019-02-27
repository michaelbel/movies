package org.michaelbel.moviemade.presentation.features.keywords

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.chip_keyword.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.entity.Keyword
import java.util.*

class KeywordsAdapter(
        private val listener: Listener
): RecyclerView.Adapter<KeywordsAdapter.KeywordsViewHolder>() {

    interface Listener {
        fun onKeywordClick(keyword: Keyword)
    }

    private val keywords = ArrayList<Keyword>()

    fun addKeywords(results: List<Keyword>) {
        keywords.addAll(results)
        notifyItemRangeInserted(keywords.size + 1, results.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeywordsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chip_keyword, parent, false)
        return KeywordsViewHolder(view)
    }

    override fun onBindViewHolder(holder: KeywordsViewHolder, position: Int) {
        holder.bind(keywords[position])
    }

    override fun getItemCount() = keywords.size

    inner class KeywordsViewHolder(override val containerView: View):
            RecyclerView.ViewHolder(containerView), View.OnClickListener, LayoutContainer {

        fun bind(keyword: Keyword) {
            keywordName.text = keyword.name
            containerView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            if (adapterPosition != RecyclerView.NO_POSITION) {
                listener.onKeywordClick(keywords[adapterPosition])
            }
        }
    }
}