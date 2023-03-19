package me.saro.rss.core.service

import me.saro.rss.core.RssChannel
import me.saro.rss.core.RssItem
import javax.xml.stream.XMLStreamReader

/**
 * This class reads the 'channel' data until the 'item' is encountered.
 */
class ReadItems {
    companion object {
        fun handle(channel: RssChannel, reader: XMLStreamReader, includeNonStandard: Boolean, continues: (RssItem, RssChannel) -> Boolean) {
            var item = RssItem()
            do {
                when (val name = XmlReadUtil.moveNextTag(reader)) {
                    "", "item" -> {
                        if (continues(item, channel)) {
                            channel.items.addFirst(item)
                            item = RssItem()
                            continue
                        }
                        return
                    }
                    "category" -> item.categories.add(XmlReadUtil.readText(reader))
                    "title" -> item.title = XmlReadUtil.readText(reader)
                    "link" -> item.link = XmlReadUtil.readText(reader)
                    "description" -> item.description = XmlReadUtil.readText(reader)
                    "author" -> item.author = XmlReadUtil.readText(reader)
                    "comments" -> item.comments = XmlReadUtil.readText(reader)
                    "pubDate" -> item.pubDate = XmlReadUtil.readText(reader)
                    "source" -> item.source = XmlReadUtil.readText(reader)
                    else -> if (includeNonStandard) {
                        item.other[name] = XmlReadUtil.readXmlText(reader)
                    } else {
                        XmlReadUtil.moveTagEnd(reader, name)
                    }
                }
            } while (reader.hasNext())
        }
    }
}

