package org.michaelbel.movies.repository

import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before

class AccountRepositoryTest {

    private val testScope = TestScope(UnconfinedTestDispatcher())

    private lateinit var subject: AccountRepositoryImpl

    @Before
    fun setup() {
        /*subject = AccountRepositoryImpl(
            accountService = ,
            accountDao = ,
            preferences =
        )*/
    }
}