package se.callistaenterprise.kotlindsl.data

import se.callistaenterprise.kotlindsl.dsl.OrderDsl
import se.callistaenterprise.kotlindsl.dsl.scriptdef.OrderDslScript
import kotlin.script.experimental.api.EvaluationResult
import kotlin.script.experimental.api.ResultWithDiagnostics
import kotlin.script.experimental.api.ScriptDiagnostic
import kotlin.script.experimental.api.ScriptEvaluationConfiguration
import kotlin.script.experimental.api.implicitReceivers
import kotlin.script.experimental.host.StringScriptSource
import kotlin.script.experimental.jvmhost.BasicJvmScriptingHost
import kotlin.script.experimental.jvmhost.createJvmCompilationConfigurationFromTemplate

/*
  This is where the magic happens. The OrderDsl script is interpreted by Kotlin's script logic.
 It returns an instance of OrderDsl configured by the script. No imports are necessary in the actual
  script file, as those imports are added through this class.

 Compare this file to the corresponding file in the Java version.
 */
class DslUtil {

    companion object {

        private val compilationConfiguration = createJvmCompilationConfigurationFromTemplate<OrderDslScript>()

        fun evalScript(content: String, fileName: String): OrderDsl {
            val dsl = OrderDsl()
            val res = evalScript(content, dsl)
            if (res is ResultWithDiagnostics.Failure) {
                res.reports.filter { it.severity == ScriptDiagnostic.Severity.FATAL || it.severity == ScriptDiagnostic.Severity.ERROR }
                    .map {
                        println("line ${it.location?.start?.line}, column ${it.location?.start?.col}: ${it.message}")
                    }
                throw RuntimeException("Order DSL script $fileName could not be compiled.")
            }
            return dsl
        }

        private fun evalScript(script: String, dsl: OrderDsl): ResultWithDiagnostics<EvaluationResult> {
            return BasicJvmScriptingHost().eval(
                StringScriptSource(script),
                compilationConfiguration,
                ScriptEvaluationConfiguration {
                    implicitReceivers(dsl)
                }
            )
        }
    }
}
