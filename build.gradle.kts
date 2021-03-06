/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java project to get you started.
 * For more details take a look at the Java Quickstart chapter in the Gradle
 * User Manual available at https://docs.gradle.org/6.1.1/userguide/tutorial_java_projects.html
 */

plugins {
    // Apply the java plugin to add support for Java
    java

    // Apply the application plugin to add support for building a CLI application.
    application
}

repositories {
    // Use jcenter for resolving dependencies.
    // You can declare any Maven/Ivy/file repository here.
    mavenCentral()
}

dependencies {
    // This dependency is used by the application.
    implementation("com.google.guava:guava:28.1-jre")

    // Use JUnit test framework
    testImplementation("junit:junit:4.12")

    implementation("org.postgresql:postgresql:42.2.10")
    implementation("org.eclipse.jetty:jetty-util-ajax:9.4.27.v20200227")
    implementation("org.json:json:20190722")
    implementation("org.eclipse.jetty:jetty-server:9.4.26.v20200117")
}

application {
    // Define the main class for the application.
    mainClassName = "funProject.Index"
}


tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "funProject.Index"
    }

    from(sourceSets.main.get().output)
    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter {
            it.name.endsWith("jar") }.map { zipTree(it) }
    })
}
