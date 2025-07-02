group = "de.sealcore"
version = "1.0.1"

val lwjglVersion = "3.3.6"
var jacksonDatabindVersion = "2.19.1"
var jomlVersion = "1.10.8"
var flatlafVersion = "3.6"

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

    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonDatabindVersion")

    implementation("org.joml:joml:$jomlVersion")

    implementation("com.formdev:flatlaf:$flatlafVersion")

}

tasks {
    val fatJar = register<Jar>("fatJar") {
        dependsOn("compileJava", "processResources")
        archiveClassifier.set("standalone") //
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE

        manifest {
            attributes(mapOf("Main-Class" to application.mainClass.get()))
        }

        val sourcesMain = sourceSets.main.get()
        val contents = configurations.runtimeClasspath.get()
            .map { if (it.isDirectory) it else zipTree(it) } +
                sourcesMain.output

        from(contents)
    }

    build {
        dependsOn(fatJar)
    }
}
