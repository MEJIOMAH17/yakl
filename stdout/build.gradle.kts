plugins{
    `java-library`
    id( "me.champeau.jmh") version "0.6.6"
}
kotlin {
    explicitApi = org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode.Strict
}
dependencies {
    api(project(":core"))
    jmh("ch.qos.logback:logback-classic:1.2.10")
    jmh("org.apache.logging.log4j:log4j-core:2.17.1")
}

jmh {
    warmupIterations.set(5)
    iterations.set(10)
    fork.set(1)
    benchmarkMode.set(listOf("thrpt"))
    timeUnit.set("ms")

}