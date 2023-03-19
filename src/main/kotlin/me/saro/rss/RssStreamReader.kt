package me.saro.rss

import me.saro.rss.core.RssChannel
import me.saro.rss.core.RssItem
import me.saro.rss.core.service.ReadChannel
import me.saro.rss.core.service.ReadItems
import java.io.InputStream
import java.io.Reader
import java.io.StringReader
import java.net.URL
import javax.xml.stream.XMLInputFactory
import javax.xml.stream.XMLStreamReader

/**
 * this class thread-safe
 * https://validator.w3.org/feed/docs/rss2.html
 */
class RssStreamReader(
    private val includeNonStandard: Boolean
) {
    fun url(url: String): RssChannel =
        URL(url).openStream().use { stream(it, "UTF-8") }
    fun url(url: String, continues: (RssItem, RssChannel) -> Boolean): RssChannel =
        URL(url).openStream().use { stream(it, "UTF-8", continues) }

    fun url(url: URL): RssChannel =
        url.openStream().use { stream(it, "UTF-8") }

    fun url(url: URL, continues: (RssItem, RssChannel) -> Boolean): RssChannel =
        url.openStream().use { stream(it, "UTF-8", continues) }

    fun text(text: String): RssChannel =
        read(XMLInputFactory.newFactory().createXMLStreamReader(StringReader(text)))

    fun text(text: String, continues: (RssItem, RssChannel) -> Boolean = { _, _ -> true }): RssChannel =
        read(XMLInputFactory.newFactory().createXMLStreamReader(StringReader(text)), continues)

    fun reader(reader: Reader): RssChannel =
        read(XMLInputFactory.newFactory().createXMLStreamReader(reader))

    fun reader(reader: Reader, continues: (RssItem, RssChannel) -> Boolean = { _, _ -> true }): RssChannel =
        read(XMLInputFactory.newFactory().createXMLStreamReader(reader), continues)

    fun stream(inputStream: InputStream, charset: String): RssChannel =
        read(XMLInputFactory.newFactory().createXMLStreamReader(inputStream, charset))

    fun stream(inputStream: InputStream, charset: String, continues: (RssItem, RssChannel) -> Boolean): RssChannel =
        read(XMLInputFactory.newFactory().createXMLStreamReader(inputStream, charset), continues)

    private fun read(xmlStreamReader: XMLStreamReader, continues: (RssItem, RssChannel) -> Boolean = { _, _ -> true }) =
        RssChannel().apply {
            ReadChannel.handle(this, xmlStreamReader, includeNonStandard)
            ReadItems.handle(this, xmlStreamReader, includeNonStandard, continues)
        }

    class Builder {
        private var includeNonStandard: Boolean = false
        fun includeNonStandard(includeNonStandard: Boolean) =
            apply { this.includeNonStandard = includeNonStandard }
        fun build() = RssStreamReader(includeNonStandard)
    }
}


