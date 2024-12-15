package org.michaelbel.movies.work

import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.persistence.database.entity.pojo.ImagePojo

interface WorkManagerInteractor {

    fun downloadImage(image: ImagePojo): Flow<WorkInfoState>

    fun prepopulateDatabase()

    fun updateAccountDetails()
}