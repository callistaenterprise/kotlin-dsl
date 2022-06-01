package se.callistaenterprise.kotlindsl.data

import org.jetbrains.kotlin.cli.common.repl.KotlinJsr223JvmScriptEngineFactoryBase
import org.jetbrains.kotlin.cli.common.repl.ScriptArgsWithTypes
import org.jetbrains.kotlin.script.jsr223.KotlinJsr223JvmLocalScriptEngine
import org.jetbrains.kotlin.script.jsr223.KotlinStandardJsr223ScriptTemplate
import se.callistaenterprise.kotlindsl.dsl.OrderDsl
import java.io.File
import java.nio.file.FileSystemNotFoundException
import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Collectors
import javax.script.Bindings
import javax.script.ScriptContext
import javax.script.ScriptEngine
import kotlin.script.experimental.jvm.util.scriptCompilationClasspathFromContext

/*
 The Kotlin script compiler requires that it finds its dependencies (i.e. jar files) on
 disk, and not within the type of Fat JAR that Spring produces. This configuration ensures that
 those Jars are added to the classpath.
 */
class SpringBootKotlinScriptEngineFactory : KotlinJsr223JvmScriptEngineFactoryBase() {

    override fun getScriptEngine(): ScriptEngine {
        val extractedJarLocation = OrderDsl::class.java.protectionDomain.codeSource.location.toURI()
        val jarDirectory = try {
            Paths.get(extractedJarLocation).parent
        } catch (e: FileSystemNotFoundException) {
            throw e
        }

        val classpath = Files.list(jarDirectory)
            .filter { it.fileName.endsWith(".jar") }
            .map { it.toFile() }
            .collect(Collectors.toCollection { mutableListOf<File>() })

        classpath += scriptCompilationClasspathFromContext("kotlin-script-util.jar", wholeClasspath = true)

        return KotlinJsr223JvmLocalScriptEngine(
            this,
            classpath,
            KotlinStandardJsr223ScriptTemplate::class.qualifiedName!!,
            { ctx, types ->
                ScriptArgsWithTypes(arrayOf(ctx.getBindings(ScriptContext.ENGINE_SCOPE)), types ?: emptyArray())
            },
            arrayOf(Bindings::class)
        )
    }
}
