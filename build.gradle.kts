plugins {
    java
    id("xyz.jpenilla.run-paper") version "2.3.1"
}

group = "soren.bunkies_2"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.8-R0.1-SNAPSHOT")
    compileOnly(fileTree("libs") { include("*.jar") })

}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

tasks {
    runServer {
        minecraftVersion("1.21.11")
    }
}