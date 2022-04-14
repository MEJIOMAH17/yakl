Simple html appender

Example of output could be found at src/test/resources/test.html

Usage

```kotlin
logging {
    html("html") {
        fileCreator = {
            // your code for file creation 
        }
        rollFileWhen = {
            // your code for determine, when file should be rolled
        }
    }
}
```