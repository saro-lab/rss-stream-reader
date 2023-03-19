package me.saro.rss.core.service

import me.saro.rss.core.RssChannel
import javax.xml.stream.XMLStreamReader

/**
 * This class reads the 'channel' data until the 'item' is encountered.
 * https://validator.w3.org/feed/docs/rss2.html
 */
class ReadChannel {
    companion object {
        fun handle(channel: RssChannel, reader: XMLStreamReader, includeNonStandard: Boolean) {
            // it skips the element in the RSS feed until the 'channel' is encountered.
            if (XmlReadUtil.skipUntilTag(reader, "channel").isEmpty()) {
                throw IllegalArgumentException("cannot find the 'channel' element in the current RSS format")
            }

            do {
                when (val name = XmlReadUtil.moveNextTag(reader)) {
                    "", "item" -> return
                    "category" -> channel.categories.add(XmlReadUtil.readText(reader))
                    "title" -> channel.title = XmlReadUtil.readText(reader)
                    "link" -> channel.link = XmlReadUtil.readText(reader)
                    "description" -> channel.description = XmlReadUtil.readText(reader)
                    "pubDate" -> channel.pubDate = XmlReadUtil.readText(reader)
                    "lastBuildDate" -> channel.lastBuildDate = XmlReadUtil.readText(reader)
                    "language", "copyright", "managingEditor", "webMaster", "generator", "docs", "cloud", "ttl", "image", "rating", "textInput", "kipHours", "skipDays"
                        -> channel.other[name] = XmlReadUtil.readText(reader)
                    else -> if (includeNonStandard) {
                        channel.other[name] = XmlReadUtil.readXmlText(reader)
                    } else {
                        XmlReadUtil.moveTagEnd(reader, name)
                    }
                }
            } while (reader.hasNext())
        }
    }
}

