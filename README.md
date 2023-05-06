### RSS Stream Reader
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/me.saro/rss-stream-reader/badge.svg)](https://maven-badges.herokuapp.com/maven-central/me.saro/rss-stream-reader)
[![GitHub license](https://img.shields.io/github/license/saro-lab/rss-stream-reader.svg)](https://github.com/saro-lab/rss-stream-reader/blob/master/LICENSE)

The RSS Stream Reader is very fast and performs real-time processing through the stream. Therefore, it can handle RSS without reading all of them. For example, by adding code to stop at duplicate items, it can end the stream without reading unnecessary duplicate items.

# QUICK START

## gradle kts
```
implementation("me.saro:rss-stream-reader:1.1")
```

## gradle
```
compile "me.saro:rss-stream-reader:1.1"
```

## maven
``` xml
<dependency>
  <groupId>me.saro</groupId>
  <artifactId>rss-stream-reader</artifactId>
  <version>1.1</version>
</dependency>
```

## Kotlin Example
```
// reader is thread-safe
val reader = RssStreamReader.Builder().build()
```
```
// normal
val rss = reader.url("https://test/rss.xml")

// print
println(rss)
println("- items")
rss.items.forEach(::println)
```
```
// stream stop
val rss = reader.url("https://test/rss.xml") { item, channel ->
    if (lastLink == item.link) {
        // The RSS feed stream has been stopped because the RSS feed items are duplicated.
        return@url false
    }
    true
}
```

## Java Example
```
// reader is thread-safe
RssStreamReader reader = new RssStreamReader.Builder().build();
```
```
// normal
var rss = reader.url("https://test/rss.xml");

// print
System.out.println(rss);
System.out.println("- items");
rss.getItems().stream().forEach(System.out::println);
```
```
// stream stop
var rss = reader.url("https://test/rss.xml", (item, channel) -> {
    if (lastLink.equals(item.getLink())) {
        // The RSS feed stream has been stopped because the RSS feed items are duplicated.
        return false;
    }
    return true;
});
```

## test code
- [Kotlin](https://github.com/saro-lab/rss-stream-reader/blob/master/src/test/kotlin/me/saro/rss/ktest/RssStreamReaderTest.kt)
- [Java](https://github.com/saro-lab/rss-stream-reader/blob/master/src/test/java/me/saro/rss/jtest/RssStreamReaderTest.java) 

## repository
- https://repo1.maven.org/maven2/me/saro/rss-stream-reader/
- https://mvnrepository.com/artifact/me.saro/rss-stream-reader

## see
- [가리사니 개발자공간](https://gs.saro.me)
