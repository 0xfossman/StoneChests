plugins {
    java
}

group = "de.ungarnet"
version = "1.0.5"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:26.3.build.244-stable")
    implementation("org.xerial:sqlite-jdbc:3.51.1.0")
}

configurations.compileClasspath {
    attributes { attribute(TargetJvmVersion.TARGET_JVM_VERSION_ATTRIBUTE, 25) }
}

java { toolchain { languageVersion.set(JavaLanguageVersion.of(25)) } }
// Shadow's bytecode relocator currently supports class-file version 68; build on JDK 25
// while emitting Java 24-compatible bytecode, which Paper 26.3 accepts on its Java 25 runtime.
tasks.withType<JavaCompile>().configureEach { options.release.set(24); options.encoding = "UTF-8" }
tasks.processResources { filesMatching("plugin.yml") { expand("version" to project.version) } }
// sqlite-jdbc is bundled directly. It contains native libraries selected by the JDBC driver.
tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(configurations.runtimeClasspath.map { files -> files.map { if (it.isDirectory) it else zipTree(it) } })
}
