import org.gradle.internal.os.OperatingSystem
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    val kotlinVersion = "2.2.0"
    kotlin("multiplatform") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
    id("org.sirekanyan.version-checker") version "1.0.14"
    distribution
}

group = "org.sirekanyan"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    commonMainImplementation("com.github.ajalt.clikt:clikt-core:5.0.3")
    commonMainImplementation("org.jetbrains.kotlinx:kotlinx-datetime:0.7.1")
    commonMainImplementation("org.jetbrains.kotlinx:kotlinx-io-core:0.8.0")
}

kotlin {
    val os = OperatingSystem.current()
    buildList {
        add(linuxX64())
        add(linuxArm64())
        if (os.isMacOsX) {
            add(macosX64())
            add(macosArm64())
        }
    }.forEach {
        it.binaries {
            executable(listOf(if (hasProperty("release")) RELEASE else DEBUG))
        }
    }
}

private val kotlinTargetNames: List<String> =
    kotlin.targets.filterIsInstance<KotlinNativeTarget>().map { it.name }

distributions {
    kotlinTargetNames.forEach { targetName ->
        create(targetName) {
            distributionBaseName = "todo-${targetName.replace("X64", "-amd64").replace("Arm64", "-arm64")}"
            contents {
                from("build/bin/${targetName}/releaseExecutable/todo.kexe") {
                    rename { it.removeSuffix(".kexe") }
                }
            }
        }
    }
}

kotlinTargetNames.forEach { targetName ->
    tasks {
        getByName<Tar>("${targetName}DistTar") {
            filePermissions { unix("755") }
            compression = Compression.GZIP
            archiveExtension = "tar.gz"
        }
    }
}
