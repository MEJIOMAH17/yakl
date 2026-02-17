kotlin {
    jvm()
}
dependencies {
    commonMainApi(project(":core"))
    commonTestImplementation(libs.awaitility)
}
