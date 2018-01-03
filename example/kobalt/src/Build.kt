import com.beust.kobalt.*
import com.beust.kobalt.plugin.application.*
import com.beust.kobalt.plugin.packaging.*
import net.thauvin.erik.kobalt.plugin.pom2xml.*

// ./kobaltw pom2xml

val bs = buildScript {
    repos(localMaven())
    plugins("net.thauvin.erik:kobalt-pom2xml:0.1.0")
}

val p = project {

    name = "example"
    group = "com.example"
    artifactId = name
    version = "0.1"

    dependencies {
        compile("com.beust:jcommander:1.47")
        //compile("org.slf4j:slf4j-api:")
        compile("ch.qos.logback:logback-core:0.5")
        compile("ch.qos.logback:logback-classic:1.1.7")
        compile("commons-httpclient:commons-httpclient:3.1")
        compile("com.beust:kobalt-plugin-api:0.878")
    }

    dependenciesTest {
        compile("org.testng:testng:")
    }

    assemble {
        jar {
        }
    }

    application {
        mainClass = "com.example.MainKt"
    }

    pom2xml {
        // loc = "foo/bar"
        // filename = "pom-example.xml"
    }
}