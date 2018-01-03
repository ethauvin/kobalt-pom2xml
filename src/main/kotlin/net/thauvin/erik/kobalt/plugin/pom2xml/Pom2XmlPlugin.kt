/*
 * VersionEyePlugin.kt
 *
 * Copyright (c) 2018, Erik C. Thauvin (erik@thauvin.net)
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *   Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 *   Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 *   Neither the name of this project nor the names of its contributors may be
 *   used to endorse or promote products derived from this software without
 *   specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.thauvin.erik.kobalt.plugin.pom2xml

import com.beust.kobalt.Plugins
import com.beust.kobalt.TaskResult
import com.beust.kobalt.api.*
import com.beust.kobalt.api.annotation.Directive
import com.beust.kobalt.api.annotation.Task
import com.beust.kobalt.misc.warn
import com.google.inject.Inject
import com.google.inject.Singleton

import java.io.File

@Singleton
class Pom2XmlPlugin @Inject constructor(private val configActor: ConfigActor<Pom2XmlConfig>,
                                        private val taskContributor: TaskContributor) :
        BasePlugin(), ITaskContributor, IConfigActor<Pom2XmlConfig> by configActor {

    // ITaskContributor
    override fun tasksFor(project: Project, context: KobaltContext): List<DynamicTask> = taskContributor.dynamicTasks

    companion object {
        const val NAME: String = "pom2xml"
    }

    override val name = NAME

    override fun apply(project: Project, context: KobaltContext) {
        super.apply(project, context)
        taskContributor.addVariantTasks(this, project, context, "pom2xml", group = "publish",
                runTask = { pom2xml(project) })
    }

    @Suppress("MemberVisibilityCanPrivate")
    @Task(name = "pom2xml", description = "Generate Project Object Model XML file")
    fun pom2xml(project: Project): TaskResult {

        // Load configuration
        configurationFor(project)?.let { config ->
            if (config.name.isNotBlank()) {
                val loc = File(config.loc + if (config.loc.endsWith(File.separator)) "" else File.separator)
                if (loc.isDirectory) {
                    // Write POM
                    File(loc, config.name).writeText(context.generatePom(project))
                } else {
                    warn("Invalid POM file location: ${loc.absolutePath}")
                }
            } else {
                warn("Invalid POM file name: ${config.name}")
            }
        }

        return TaskResult()
    }
}

@Directive
class Pom2XmlConfig {
    var loc = "."
    var name = "pom.xml"
}

@Suppress("unused")
@Directive
fun Project.pom2xml(init: Pom2XmlConfig.() -> Unit) {
    Pom2XmlConfig().let { config ->
        config.init()
        (Plugins.findPlugin(Pom2XmlPlugin.NAME) as Pom2XmlPlugin).addConfiguration(this, config)
    }
}
