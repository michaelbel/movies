package org.michaelbel.movies.work.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.michaelbel.movies.persistence.database.entity.pojo.ImagePojo
import org.michaelbel.movies.work.WorkInfoState
import org.michaelbel.movies.work.WorkManagerInteractor

class WorkManagerInteractorImpl: WorkManagerInteractor {

    override fun downloadImage(image: ImagePojo): Flow<WorkInfoState> {
        return flowOf(WorkInfoState.Success(""))
    }

    override fun prepopulateDatabase() {}

    override fun updateAccountDetails() {}
}