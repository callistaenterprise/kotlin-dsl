@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
}

allprojects {
    group = "se.callistaenterprise.kotlindsl"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}
