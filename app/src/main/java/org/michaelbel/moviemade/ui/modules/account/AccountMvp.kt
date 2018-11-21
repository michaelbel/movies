package org.michaelbel.moviemade.ui.modules.account

import com.arellomobile.mvp.MvpView
import org.michaelbel.moviemade.data.dao.Account
import org.michaelbel.moviemade.utils.Error

interface AccountMvp : MvpView {

    fun startBrowserAuth(token: String)

    fun setAccount(account: Account)

    fun sessionChanged(state: Boolean)

    fun setError(@Error error: Int)
}