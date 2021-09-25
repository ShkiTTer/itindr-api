val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project
val koinVersion: String by project
val exposedVersion: String by project
val hikariVersion: String by project

plugins {
    kotlin("jvm") version "1.5.31"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.5.31"
    id("com.github.johnrengelman.shadow") version "7.0.0"
    id("org.ajoberstar.grgit") version "4.1.0"
    application
}

apply(from = "ci.gradle")

val readProperties = (ext["readProperties"] as groovy.lang.Closure<*>)
readProperties.call()

group = "com.shkitter"
version = ext["verName"].toString()

application {
    mainClass.set("com.shkitter.app.ApplicationKt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}

dependencies {
    // Ktor
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-auth:$ktorVersion")
    implementation("io.ktor:ktor-auth-jwt:$ktorVersion")
    implementation("io.ktor:ktor-server-host-common:$ktorVersion")
    implementation("io.ktor:ktor-serialization:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")

    // Logger
    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    // Swagger UI
    implementation("com.github.papsign:Ktor-OpenAPI-Generator:-SNAPSHOT")

    // Koin
    implementation("io.insert-koin:koin-ktor:$koinVersion")

    // Exposed
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposedVersion")

    // PostgreSql Connector
    implementation(group = "org.postgresql", name = "postgresql", version = "42.2.23")

    // Hikari
    implementation("com.zaxxer:HikariCP:$hikariVersion")

    // Tests
    testImplementation("io.ktor:ktor-server-tests:$ktorVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test:$kotlinVersion")
}

tasks {
    shadowJar {
        manifest {
            attributes(Pair("Main-Class", "com.shkitter.app.ApplicationKt"))
            archiveBaseName.set(ext["artifactName"].toString())
            archiveVersion.set("")
            archiveClassifier.set("")
        }
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "11"
    }
}