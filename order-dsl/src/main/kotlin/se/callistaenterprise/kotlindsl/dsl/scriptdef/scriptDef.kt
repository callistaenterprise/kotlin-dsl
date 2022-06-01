package se.callistaenterprise.kotlindsl.dsl.scriptdef

import se.callistaenterprise.kotlindsl.dsl.OrderDsl
import kotlin.script.experimental.annotations.KotlinScript
import kotlin.script.experimental.api.*
import kotlin.script.experimental.jvm.dependenciesFromCurrentContext
import kotlin.script.experimental.jvm.jvm

const val ORDER_DSL_SUFFIX = "orderdsl.kts"

/* This is where the association between a file with suffix "orderdsl.kts" and the below
 configuration is made. When this file is used by the Kotlin compiler (and also by IntelliJ
 IDEA), all files ending with "orderdsl.kts" will be handled as containing a lambda with
 implicit receiver OrderDsl.
 */
@KotlinScript(
    displayName = "Order data script",
    fileExtension = ORDER_DSL_SUFFIX,
    compilationConfiguration = OrderDslScriptDefinition::class,
)
abstract class OrderDslScript

object OrderDslScriptDefinition : ScriptCompilationConfiguration(
    {
        jvm {
            dependenciesFromCurrentContext(wholeClasspath = true)
            defaultImports(
                "kotlin.random.Random",
                "se.callistaenterprise.kotlindsl.dsl.*",
            )
            implicitReceivers(OrderDsl::class)
        }

        ide {
            acceptedLocations(ScriptAcceptedLocation.Everywhere)
        }
    }
)
