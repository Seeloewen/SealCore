plugins {
    id("java")
    application
}

application {
    mainClass = "Main"
}

group = "me.seeloewen"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.0")
}