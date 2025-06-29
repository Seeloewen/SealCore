plugins {
    id("java")
    application
}

application {
    mainClass = "Main"
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}

group = "me.seeloewen"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

}