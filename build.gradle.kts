group = "de.sealcore"
version = "1.0-SNAPSHOT"
val lwjglVersion = "3.3.6"
val lwjglNatives = "natives-windows"

plugins {
    id("java")
    application
}

application {
    mainClass = "de.sealcore.Main"
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
    enableAssertions = true
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.lwjgl:lwjgl-bom:$lwjglVersion"))
    implementation("org.lwjgl", "lwjgl")
    implementation("org.lwjgl", "lwjgl-glfw")
    implementation("org.lwjgl", "lwjgl-openal")
    implementation("org.lwjgl", "lwjgl-opengl")
    implementation("org.lwjgl", "lwjgl-stb")
    runtimeOnly("org.lwjgl", "lwjgl", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-glfw", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-openal", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-opengl", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-stb", classifier = lwjglNatives)

    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.0")

    implementation("org.joml:joml:1.10.8")

    implementation("com.formdev:flatlaf:3.4")

}