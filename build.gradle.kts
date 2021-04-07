plugins {
    id("java")
    kotlin("jvm") version "1.4.30"
    id("maven-publish")
    id("com.github.johnrengelman.shadow") version "2.0.4"
}

buildscript {
    repositories {
        mavenCentral()
    }
}


group = "skywolf46"
version = properties["version"] as String

repositories {
    jcenter()
    maven(properties["reposilite.release"] as String)
    maven(properties["reposilite.spigot"] as String)
}

dependencies {
    compileOnly("skywolf46:commandannotation:2.7.0")
    compileOnly("org.spigotmc:spigot:1.12.2")
    implementation("mysql:mysql-connector-java:5.1.6")
}

tasks {
    jar {
        dependsOn(shadowJar)
    }

    shadowJar {
        archiveClassifier.set("shaded")
    }
}



publishing {
    repositories {
        maven {
            name = "Github"
            url = uri("https://maven.pkg.github.com/milkyway0308/BukkitSQLSupport")
            credentials {
                username = properties["gpr.user"] as String
                password = properties["gpr.key"] as String
            }
        }

        maven {
            name = "Reposilite"
            url = uri(properties["reposilite.release"] as String)
            credentials {
                username = properties["reposilite.user"] as String
                password = properties["reposilite.token"] as String
            }
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
    publications {
        create<MavenPublication>("jar") {
            groupId = "skywolf46"
            artifactId = "bss"
            version = properties["version"] as String
            artifact(tasks["shadowJar"]) {
                classifier = null
            }
            artifact(tasks["jar"]) {
                classifier = "pure"
            }
        }
    }
}
