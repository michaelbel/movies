package org.michaelbel.core.adapter

abstract class ItemsManager {

    fun get(): ArrayList<ListItem> {
        val items = ArrayList<ListItem>()
        items.addAll(getItems())
        return items
    }

    protected open fun getItems(): ArrayList<ListItem> {
        return ArrayList()
    }
}