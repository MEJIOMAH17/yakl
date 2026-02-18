kotlin {
    jvm()
}
dependencies {
    commonMainApi(project(":core"))
    commonMainImplementation(libs.slf4j)
    commonTestImplementation(libs.mockk)
}
