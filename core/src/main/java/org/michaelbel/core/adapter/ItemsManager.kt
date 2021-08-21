package org.michaelbel.core.adapter

abstract class ItemsManager {

    fun get(): List<ListItem> {
        val items = mutableListOf<ListItem>()
        items.addAll(getItems())
        return items
    }

    protected open fun getItems(): List<ListItem> {
        return ArrayList()
    }
}