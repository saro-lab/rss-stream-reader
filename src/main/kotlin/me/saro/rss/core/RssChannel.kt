package me.saro.rss.core

import java.util.*
import java.util.concurrent.LinkedBlockingDeque

/**
 * https://validator.w3.org/feed/docs/rss2.html
 */
class RssChannel(
    var title: String = "",
    var link: String = "",
    var description: String = "",
    var lastBuildDate: String = "",
    var pubDate: String = "",
    val categories: MutableList<String> = mutableListOf(),
    val other: MutableMap<String, String> = mutableMapOf(),
    val items: Deque<RssItem> = LinkedBlockingDeque()
) {
    val language: String get() = other["language"] ?: ""
    val copyright: String get() = other["copyright"] ?: ""
    val managingEditor: String get() = other["managingEditor"] ?: ""
    val webMaster: String get() = other["webMaster"] ?: ""
    val generator: String get() = other["generator"] ?: ""
    val docs: String get() = other["docs"] ?: ""
    val cloud: String get() = other["cloud"] ?: ""
    val ttl: String get() = other["ttl"] ?: ""
    val image: String get() = other["image"] ?: ""
    val rating: String get() = other["rating"] ?: ""
    val textInput: String get() = other["textInput"] ?: ""
    val skipHours: String get() = other["skipHours"] ?: ""
    val skipDays: String get() = other["skipDays"] ?: ""

    fun filter(filter: (RssItem) -> Boolean) = this.apply {
        val iterator = items.iterator()
        while (iterator.hasNext()) {
            if (!filter(iterator.next())) {
                iterator.remove()
            }
        }
    }

    override fun toString(): String {
        return "Rss(title='$title', link='$link', description='$description', categories=$categories, other=$other, itemSize=${items.size})"
    }
}