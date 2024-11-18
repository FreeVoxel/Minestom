plugins {
    `java-library`
    `application`
//    `maven-publish`
}

group = "dev.polv.testing"
// version declared by root project

dependencies {
    implementation(project(":server"))
}

//publishing {
//    publications {
//        create<MavenPublication>("maven") {
//            groupId = "net.minestom.testing"
//            artifactId = "testing"
//            version = "1.0"
//
//            from(components["java"])
//        }
//    }
//}

application {
    mainClass.set("dev.polv.example.Main")
}