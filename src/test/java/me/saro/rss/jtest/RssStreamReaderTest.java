package me.saro.rss.jtest;

import me.saro.rss.RssStreamReader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RssStreamReaderTest {

    RssStreamReader reader = new RssStreamReader.Builder().build();

    // ins = include non-standard
    RssStreamReader readerIns = new RssStreamReader.Builder().includeNonStandard(true).build();

    String rss = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "            <rss version=\"2.0\">\n" +
            "                <channel>\n" +
            "                    <title>Test Channel</title>\n" +
            "                    <link>http://example.com</link>\n" +
            "                    <description>Test Channel Description</description>\n" +
            "                    <item>\n" +
            "                        <title>Test Item 1</title>\n" +
            "                        <description>Test Item 1 Description</description>\n" +
            "                        <link>http://example.com/item1</link>\n" +
            "                        <non-standard>http://example.com/item1</non-standard>\n" +
            "                    </item>\n" +
            "                    <item>\n" +
            "                        <title>Test Item 2</title>\n" +
            "                        <description>Test Item 2 Description</description>\n" +
            "                        <link>http://example.com/item2</link>\n" +
            "                    </item>\n" +
            "                </channel>\n" +
            "            </rss>";

    @Test
    public void item0() {
        var result = reader.text(rss, (item, channel) -> false);
        assertEquals(result.getItems().size(), 0);
    }

    @Test
    public void item1() {
        var i = new int[]{0};
        var result = reader.text(rss, (item, channel) -> i[0]++ == 0);
        assertEquals(result.getItems().size(), 1);
    }

    @Test
    public void normal() {
        var result = reader.text(rss);
        assertEquals(result.getItems().size(), 2);
    }
}
