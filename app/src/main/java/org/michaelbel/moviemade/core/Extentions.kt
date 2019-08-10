package org.michaelbel.moviemade.core

import android.Manifest.permission.ACCESS_NETWORK_STATE
import android.app.Activity
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresPermission
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import com.squareup.picasso.Transformation
import org.michaelbel.moviemade.presentation.common.base.BaseViewModelFactory

inline fun <reified T: Any> Activity.startActivity(requestCode: Int = -1, options: Bundle? = null, noinline init: Intent.() -> Unit = {}) {
    val intent = newIntent<T>(this)
    intent.init()

    startActivityForResult(intent, requestCode, options)
}

inline fun <reified T: Any> Activity.startActivity(options: Bundle? = null, noinline init: Intent.() -> Unit = {}) {
    val intent = newIntent<T>(this)
    intent.init()

    startActivity(intent, options)
}

inline fun <reified T: Any> newIntent(context: Context): Intent = Intent(context, T::class.java)

@RequiresPermission(ACCESS_NETWORK_STATE)
fun Context.isNetworkAvailable(): Boolean {
    val connectivityManager = this.getSystemService(CONNECTIVITY_SERVICE) as? ConnectivityManager
    connectivityManager?.let {
        val netInfo = it.activeNetworkInfo
        netInfo?.let { info ->
            if (info.isConnected) return true
        }
    }
    return false
}

/**
 * Отписаться до того, как будет подписан идентичный наблюдатель, чтобы избежать дублирования.
 * Удаление и добавление обратно одного и того же наблюдателя будет эффективно сбрасывать его состояние,
 * чтобы LiveData автоматически доставлять последний результат во время onStart(), если таковые имеются.
 */
fun <T> LiveData<T>.reObserve(owner: LifecycleOwner, observer: Observer<T>) {
    removeObserver(observer)
    observe(owner, observer)
}

inline fun <reified T : ViewModel> Fragment.getViewModel(noinline creator: (() -> T)? = null): T {
    return if (creator == null) ViewModelProviders.of(this).get(T::class.java) else ViewModelProviders.of(this, BaseViewModelFactory(creator)).get(T::class.java)
}

inline fun <reified T : ViewModel> FragmentActivity.getViewModel(noinline creator: (() -> T)? = null): T {
    return if (creator == null) ViewModelProviders.of(this).get(T::class.java) else ViewModelProviders.of(this, BaseViewModelFactory(creator)).get(T::class.java)
}

fun ImageView.loadImage(url: String, resize: Pair<Int, Int> = Pair(0, 0), resizeDimen: Pair<Int, Int> = Pair(0, 0),
                        @DrawableRes placeholder: Int = 0, @DrawableRes error: Int = 0, transformation: Transformation? = null) {
    val picasso: RequestCreator = Picasso.get().load(url)

    if (resize != Pair(0, 0)) {
        picasso.resize(resize.first, resize.second)
    }

    if (resizeDimen != Pair(0, 0)) {
        picasso.resizeDimen(resizeDimen.first, resizeDimen.second)
    }

    if (placeholder != 0) {
        picasso.placeholder(placeholder)
    }

    if (error != 0) {
        picasso.error(error)
    }

    if (transformation != null) {
        picasso.transform(transformation)
    }

    picasso.into(this)
}