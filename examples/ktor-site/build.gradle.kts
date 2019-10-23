import io.kotless.plugin.gradle.dsl.Webapp.Route53
import io.kotless.plugin.gradle.dsl.kotless
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile

group = "io.kotless"
version = "0.1.1"

plugins {
    id("tanvd.kosogor") version "1.0.7" apply true

    kotlin("jvm") version "1.3.50" apply true

    id("io.kotless") version "0.1.3-SNAPSHOT" apply true
}

repositories {
    mavenLocal()
    //artifacts are located at JCenter
    jcenter()
}

dependencies {
    implementation("io.kotless", "ktor-lang", "0.1.3-SNAPSHOT")
    implementation("io.kotless", "ktor-lang-parser", "0.1.3-SNAPSHOT")
    implementation("org.jetbrains.kotlinx", "kotlinx-html-jvm", "0.6.11")
}

tasks.withType<KotlinJvmCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
        languageVersion = "1.3"
        apiVersion = "1.3"
    }
}

kotless {
    config {
        bucket = "eu.ktor-site.s3.ktls.aws.intellij.net"
        prefix = "ktor-site"

        workDirectory = file("src/main/static")

        terraform {
            profile = "kotless-jetbrains"
            region = "eu-west-1"
        }
    }

    webapp {
        packages = setOf("io.kotless.examples")
        route53 = Route53("ktor-site", "kotless.io")
    }
}
