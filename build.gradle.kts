plugins {
    id("java")
    id("application")
    id("org.openjfx.javafxplugin") version "0.0.13"
    id("org.beryx.jlink") version "2.25.0"
}

group = "com.alazeprt"
version = "1.0"

repositories {
    mavenCentral()
}

application {
    mainModule = "com.alazeprt"
    mainClass = "com.alazeprt.MCSDMain"
}

javafx {
    version = "17.0.6"
    modules = listOf("javafx.controls", "javafx.fxml")
}

dependencies {
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("org.jsoup:jsoup:1.15.3")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType(JavaCompile::class) {
    options.encoding = "UTF-8"
}

jlink {
    launcher {
        name = "MCServerDownload-GUI"
    }

    jpackage {
        installerOutputDir = file("$buildDir/installer")
        if(org.gradle.internal.os.OperatingSystem.current().isWindows) {
            installerOptions.add("--win-dir-chooser")
            installerOptions.add("--win-per-user-install")
            installerOptions.add("--win-menu")
            installerOptions.add("--win-shortcut")
        }
    }
}