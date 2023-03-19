package me.saro.rss.ktest

import me.saro.rss.RssStreamReader
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class RssStreamReaderTest {
    private val reader = RssStreamReader.Builder().build()

    // ins = include non-standard
    private val readerIns = RssStreamReader.Builder().includeNonStandard(true).build()

    private val rss = """<?xml version="1.0" encoding="UTF-8"?>
            <rss version="2.0">
                <channel>
                    <title>Test Channel</title>
                    <link>http://example.com</link>
                    <description>Test Channel Description</description>
                    <item>
                        <title>Test Item 1</title>
                        <description>Test Item 1 Description</description>
                        <link>http://example.com/item1</link>
                        <non-standard>http://example.com/item1</non-standard>
                    </item>
                    <item>
                        <title>Test Item 2</title>
                        <description>Test Item 2 Description</description>
                        <link>http://example.com/item2</link>
                    </item>
                </channel>
            </rss>"""

    @Test
    fun item0() {
        val result = reader.text(rss) { item, channel -> false }
        Assertions.assertEquals(result.items.size, 0)
    }

    @Test
    fun item1() {
        val i = intArrayOf(0)
        val result = reader.text(rss) { item, channel -> i[0]++ == 0 }
        Assertions.assertEquals(result.items.size, 1)
    }

    @Test
    fun normal() {
        val result = reader.text(rss)
        Assertions.assertEquals(result.items.size, 2)
    }
}