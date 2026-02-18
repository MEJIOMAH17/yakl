kotlin {
    jvm()
}
dependencies {
    commonMainApi(project(":api"))
    commonTestImplementation(libs.awaitility)
    commonTestImplementation(libs.mockk)
}
