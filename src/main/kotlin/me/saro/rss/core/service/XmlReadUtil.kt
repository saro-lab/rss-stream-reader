package me.saro.rss.core.service

import javax.xml.stream.XMLStreamConstants
import javax.xml.stream.XMLStreamReader

class XmlReadUtil {
    companion object {
        fun moveNextTag(reader: XMLStreamReader): String {
            while (reader.hasNext()) {
                reader.next()
                if (reader.isStartElement) {
                    return reader.name.localPart
                }
            }
            return ""
        }

        fun moveTagEnd(reader: XMLStreamReader, tagName: String) {
            while (reader.hasNext()) {
                reader.next()
                if (reader.isEndElement && reader.name.localPart == tagName) {
                    return
                }
            }
        }

        fun readText(reader: XMLStreamReader): String {
            val tag = reader.name.localPart
            val sb = StringBuilder(200)
            while (reader.hasNext()) {
                reader.next()
                when (reader.eventType) {
                    XMLStreamConstants.END_ELEMENT -> {
                        if (reader.name.localPart == tag) {
                            return sb.toString().trim()
                        }
                    }
                    XMLStreamConstants.CHARACTERS,
                    XMLStreamConstants.CDATA -> {
                        sb.append(reader.text)
                    }
                }
            }
            return ""
        }

        // Experimental
        fun readXmlText(reader: XMLStreamReader): String {
            val tag = reader.name.localPart
            val sb = StringBuilder(200)
            while (reader.hasNext()) {
                reader.next()
                when (reader.eventType) {
                    XMLStreamConstants.START_ELEMENT -> {
                        sb.append('<').append(reader.name.localPart).append('>')
                    }
                    XMLStreamConstants.END_ELEMENT -> {
                        val tagName = reader.name.localPart
                        if (tagName == tag) {
                            return sb.toString()
                        } else {
                            sb.append("</").append(tagName).append('>')
                        }
                    }
                    XMLStreamConstants.CHARACTERS,
                    XMLStreamConstants.CDATA -> {
                        sb.append(escapeXml(reader.text.trim()))
                    }
                }
            }
            return ""
        }

        fun skipUntilTag(reader: XMLStreamReader, name: String): String {
            while (reader.hasNext()) {
                reader.next()
                if (reader.isStartElement && reader.name.localPart == name) {
                    return name
                }
            }
            return ""
        }

        private fun escapeXml(text: String): String {
            val result = StringBuilder()
            for (element in text) {
                when (element) {
                    '<' -> result.append("&lt;")
                    '>' -> result.append("&gt;")
                    '&' -> result.append("&amp;")
                    '\'' -> result.append("&apos;")
                    '\"' -> result.append("&quot;")
                    else -> result.append(element)
                }
            }
            return result.toString()
        }
    }
}