import com.beust.kobalt.buildScript
import com.beust.kobalt.localMaven
import com.beust.kobalt.plugin.packaging.assemble
import com.beust.kobalt.plugin.publish.autoGitTag
import com.beust.kobalt.plugin.publish.bintray
import com.beust.kobalt.profile
import com.beust.kobalt.project
import org.apache.maven.model.Developer
import org.apache.maven.model.License
import org.apache.maven.model.Model
import org.apache.maven.model.Scm

val bs = buildScript {
    repos(localMaven())
    plugins("net.thauvin.erik:kobalt-maven-local:")
}

val dev by profile()
val kobaltDependency = if (dev) "kobalt" else "kobalt-plugin-api"

val p = project {

    name = "kobalt-pom2xml"
    group = "net.thauvin.erik"
    artifactId = name
    version = "0.1.0"

    pom = Model().apply {
        description = "pom2xml plug-in for the Kobalt build system."
        url = "https://github.com/ethauvin/kobalt-pom2xml"
        licenses = listOf(License().apply {
            name = "BSD 3-Clause"
            url = "https://opensource.org/licenses/BSD-3-Clause"
        })
        scm = Scm().apply {
            url = "https://github.com/ethauvin/kobalt-pom2xml"
            connection = "https://github.com/ethauvin/kobalt-pom2xml.git"
            developerConnection = "git@github.com:ethauvin/kobalt-pom2xml.git"
        }
        developers = listOf(Developer().apply {
            id = "ethauvin"
            name = "Erik C. Thauvin"
            email = "erik@thauvin.net"
        })
    }

    dependencies {
        compileOnly("com.beust:$kobaltDependency:")
        compile("org.jetbrains.kotlin:kotlin-stdlib:1.2.10")
    }

    dependenciesTest {
        compile("org.testng:testng:6.12")
    }

    assemble {
        jar {
            fatJar = true
        }

        mavenJars {
            fatJar = true
        }
    }

    autoGitTag {
        enabled = true
        push = false
        message = "Version $version"
    }

    bintray {
        publish = true
        description = "Release version $version"
        vcsTag = version
    }
}