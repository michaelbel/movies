package org.michaelbel.sandbox

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    fun CoroutineScope.getUsers(): ReceiveChannel<String> = produce {
        val users = listOf("Tom", "Bob", "Sam")
        for (user in users) {
            send(user)
        }
    }

    getUsers().consumeEach {
        it
    }
}