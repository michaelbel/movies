package org.michaelbel.movies.platform.update

interface UpdateService {

    fun setUpdateAvailableListener(listener: UpdateListener) {}

    fun startUpdate(activity: Any) {}
}