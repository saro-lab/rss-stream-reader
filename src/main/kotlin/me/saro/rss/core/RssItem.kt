package me.saro.rss.core

/**
 * https://validator.w3.org/feed/docs/rss2.html
 */
class RssItem(
    var title: String = "",
    var link: String = "",
    var description: String = "",
    var author: String = "",
    var comments: String = "",
    var pubDate: String = "",
    var source: String = "",
    val categories: MutableList<String> = mutableListOf(),
    val other: MutableMap<String, String> = mutableMapOf(),
) {

    override fun toString(): String {
        return "RssItem(title='$title', link='$link', description='$description', author='$author', comments='$comments', pubDate='$pubDate', source='$source', categories=$categories, other=$other)"
    }
}

