plugins {
    id("java")
    id("application")
    id("org.openjfx.javafxplugin") version "0.1.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.explorer"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.xerial:sqlite-jdbc:3.42.0.0")
    implementation("org.openjfx:javafx-controls:21")
    implementation("org.openjfx:javafx-web:21")
}

javafx {
    version = "21"
    modules = listOf("javafx.controls", "javafx.web")
}

application {
    mainClass.set("com.explorer.Launcher")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
    archiveBaseName.set("Explorer")
    archiveClassifier.set("")
    archiveVersion.set("1.0")

    mergeServiceFiles()

    manifest {
        attributes(
            "Main-Class" to "com.explorer.Launcher"
        )
    }
}