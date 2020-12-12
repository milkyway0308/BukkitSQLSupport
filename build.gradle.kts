plugins {
    id("java")
    id("maven-publish")
}

buildscript {
    repositories {
        mavenCentral()
    }
}


group = "skywolf46"
version = properties["version"] as String

repositories {
    mavenCentral()
    maven("https://repo.yttp.ourmc.space/repository/maven-public/"){
        credentials{
            username = properties["maven.username"] as String
            password = properties["maven.password"] as String
        }
    }

    maven("https://maven.pkg.github.com/milkyway0308/CommandAnnotation") {
        credentials {
            username = properties["gpr.user"] as String
            password = properties["gpr.key"] as String
        }
    }

    maven("https://maven.pkg.github.com/milkyway0308/BungeeSwitchListener-Rebirth") {
        credentials {
            username = properties["gpr.user"] as String
            password = properties["gpr.key"] as String
        }
    }

    maven("https://maven.pkg.github.com/milkyway0308/BungeePlayerSync") {
        credentials {
            username = properties["gpr.user"] as String
            password = properties["gpr.key"] as String
        }
    }
}

dependencies {
    implementation("skywolf46:commandannotation:2.0.6")
    implementation("mysql:mysql-connector-java:5.1.6")
    compileOnly(files("V:/API/Java/Minecraft/Bukkit/Spigot/Spigot 1.12.2.jar"))
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
    }
    publications {
        create<MavenPublication>("jar") {
            from(components["java"])
            groupId = "skywolf46"
            artifactId = "bps"
            version = properties["version"] as String
            pom {
                url.set("https://github.com/milkyway0308/BukkitSQLSupport.git")
            }
        }
    }
}
