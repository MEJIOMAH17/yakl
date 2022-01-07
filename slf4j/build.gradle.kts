kotlin {
    explicitApi = org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode.Strict
}
dependencies {
    api(project(":core"))
    implementation("org.slf4j:slf4j-api:1.7.32")

    testImplementation("io.mockk:mockk:1.12.2")
}
