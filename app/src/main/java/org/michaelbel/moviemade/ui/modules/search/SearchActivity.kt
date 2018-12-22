package org.michaelbel.moviemade.ui.modules.search

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.AppCompatEditText
import kotlinx.android.synthetic.main.activity_search.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.ui.base.BaseActivity
import org.michaelbel.moviemade.ui.modules.main.MainActivity
import org.michaelbel.moviemade.utils.DrawableUtil
import org.michaelbel.moviemade.utils.QUERY
import shortbread.Shortcut
import java.util.*

@Shortcut(id = "search", rank = 1, icon = R.drawable.ic_shortcut_search, shortLabelRes = R.string.search, backStack = [MainActivity::class])
class SearchActivity : BaseActivity() {

    companion object {
        const val SPEECH_REQUEST_CODE = 101
        const val MENU_ITEM_INDEX = 0
        const val MODE_ACTION_CLEAR = 1
        const val MODE_ACTION_VOICE = 2
    }

    private var iconActionMode: Int = 0

    private var actionMenu: Menu? = null
    private var fragment: SearchMoviesFragment? = null

    fun getSearchEditText() : AppCompatEditText {
        return search_edit_text
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        actionMenu = menu

        /* menu.add(R.string.filter).setIcon(R.drawable.ic_tune).setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM)
            .setOnMenuItemClickListener(item -> {
                showFilter();
                return true;
            });*/

        menu.add(null).setIcon(R.drawable.ic_voice).setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM)
            .setOnMenuItemClickListener {
                if (iconActionMode == MODE_ACTION_VOICE) {
                    val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH)
                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, R.string.speak_now)
                    startActivityForResult(intent, SPEECH_REQUEST_CODE)
                } else if (iconActionMode == MODE_ACTION_CLEAR) {
                    Objects.requireNonNull<Editable>(search_edit_text!!.text).clear()
                    changeActionIcon()

                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.showSoftInput(search_edit_text, InputMethodManager.SHOW_IMPLICIT)
                }
                true
            }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val query = intent.getStringExtra(QUERY)
        fragment = SearchMoviesFragment.newInstance(query)
        if (savedInstanceState == null) {
            startFragment(fragment!!, R.id.fragment_view)
        }

        setSupportActionBar(toolbar)
        toolbar!!.setNavigationOnClickListener { finish() }

        iconActionMode = MODE_ACTION_VOICE

        search_edit_text!!.background = null
        search_edit_text!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                changeActionIcon()
                if (s.length >= 2) {
                    fragment!!.presenter.search(s.toString().trim { it <= ' ' })
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
        search_edit_text!!.setOnEditorActionListener { view, actionId, event ->
            if (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_SEARCH) {
                fragment!!.presenter.search(view.text.toString().trim { it <= ' ' })
                hideKeyboard(search_edit_text)
                return@setOnEditorActionListener true
            }
            false
        }
        DrawableUtil.clearCursorDrawable(search_edit_text)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SPEECH_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                val results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                if (results != null && results.size > 0) {
                    val textResults = results[0]
                    if (!TextUtils.isEmpty(textResults)) {
                        if (search_edit_text != null) {
                            search_edit_text!!.setText(textResults)
                            search_edit_text!!.setSelection(Objects.requireNonNull<Editable>(search_edit_text!!.text).length)
                            changeActionIcon()
                            fragment!!.presenter.search(textResults)
                        }
                    }
                }
            }
        }
    }

    private fun changeActionIcon() {
        if (actionMenu != null) {
            if (Objects.requireNonNull<Editable>(search_edit_text!!.text).toString().trim { it <= ' ' }.isEmpty()) {
                iconActionMode = MODE_ACTION_VOICE
                actionMenu!!.getItem(MENU_ITEM_INDEX).setIcon(R.drawable.ic_voice)
            } else {
                iconActionMode = MODE_ACTION_CLEAR
                actionMenu!!.getItem(MENU_ITEM_INDEX).setIcon(R.drawable.ic_clear)
            }
        }
    }

    fun hideKeyboard(view: View?) {
        if (view == null) {
            return
        }

        try {
            val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (!(imm.isActive)) {
                return
            }
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}