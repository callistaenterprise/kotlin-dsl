import nu.studer.gradle.jooq.JooqGenerate
import org.jooq.meta.jaxb.ForcedType
import org.jooq.meta.jaxb.Logging
import org.springframework.boot.gradle.tasks.bundling.BootJar
import org.testcontainers.containers.PostgreSQLContainer

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.flyway)
    alias(libs.plugins.jooq)

    java
}

buildscript {
    dependencies {
        classpath(libs.testcontainers.postgres)
    }
}

dependencies {
    implementation(libs.bundles.kotlin.extra)
    implementation(libs.bundles.spring.boot)
    implementation(libs.bundles.kotlin.script.runtime)
    implementation(libs.bundles.jooq)
    implementation(libs.flyway)

    implementation(project(":order-dsl"))

    runtimeOnly(libs.postgres)
    runtimeOnly(libs.kotlin.script.jsr223)

    testImplementation(libs.spring.boot.test)
    testImplementation(libs.junit.params)
    testImplementation(libs.bundles.testcontainers)

    testRuntimeOnly(libs.junit.full)
    testRuntimeOnly(libs.kotlin.script.jsr223)

    jooqGenerator(libs.postgres)
}

val generatedPath = "$projectDir/src/main/generated"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    sourceSets["main"].java.srcDir(generatedPath)
}

// The DSL monitoring requires some dependencies (jars) to be extracted to the file system at runtime.
tasks.named<BootJar>("bootJar") {
    requiresUnpack("**/kotlin-*.jar", "**/order-dsl-*.jar")
}

// A test container is used for jOOQ code generation.
val pgContainer = PostgreSQLContainer<Nothing>("postgres:14").apply {
    withUsername("jooq")
    withDatabaseName("jooq")
    withPassword("jooq")
    start()
}

val stopPgContainer = tasks.register("stopPgContainer") {
    doLast {
        pgContainer.stop()
    }
}

afterEvaluate {
    tasks.forEach { task ->
        if (task.name != stopPgContainer.name) {
            task.finalizedBy(stopPgContainer)
        }
    }
}

flyway {
    url = pgContainer.jdbcUrl
    user = "jooq"
    password = "jooq"
}

jooq {
    version.set(libs.versions.jooq.get())
    configurations {
        create("main") {
            jooqConfiguration.apply {
                logging = Logging.WARN
                jdbc.apply {
                    driver = "org.postgresql.Driver"
                    url = pgContainer.jdbcUrl
                    user = "jooq"
                    password = "jooq"
                }
                generator.apply {
                    name = "org.jooq.codegen.JavaGenerator"
                    database.apply {
                        inputSchema = "public"
                        excludes = "flyway_schema_history"
                        isIncludeExcludePackageRoutines = true
                        schemaVersionProvider = "none"
                        target.apply {
                            packageName = "se.callistaenterprise.kotlindsl.db"
                            directory = generatedPath
                        }
                        forcedTypes.addAll(
                            listOf(
                                ForcedType().apply {
                                    isEnumConverter = true
                                    includeTypes = "CUSTOMER_STATUS"
                                    userType = "se.callistaenterprise.kotlindsl.model.CustomerStatus"
                                },
                            )
                        )
                    }
                }
            }
        }
    }
}

val generateJooq: JooqGenerate by tasks
generateJooq.dependsOn(tasks.getByName("flywayMigrate"))

tasks.withType<Test> {
    useJUnitPlatform()
}
