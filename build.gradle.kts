import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm").version("1.8.21")
}

group = "com.oykdn.mc"
version = "1.1-SNAPSHOT"

val bungeecordVersion = "1.19-R0.1-SNAPSHOT"
val retrofitVersion = "2.9.0"
val okhttpVersion = "4.11.0"

repositories {
    mavenCentral()
    maven {
        url = uri("https://oss.sonatype.org/content/repositories/snapshots")
    }
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")

    compileOnly("net.md-5:bungeecord-api:$bungeecordVersion")

    // okhttp
    implementation("com.squareup.okhttp3:okhttp:$okhttpVersion")
    implementation("com.squareup.okhttp3:logging-interceptor:$okhttpVersion")

    // retrofit
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-moshi:$retrofitVersion")
}

java.targetCompatibility = JavaVersion.VERSION_17
tasks.compileKotlin {
    compilerOptions.jvmTarget.set(JvmTarget.JVM_17)
}

tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    from(sourceSets.main.get().output)

    // add Kotlin runtime
    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}
