package se.callistaenterprise.kotlindsl.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.callistaenterprise.kotlindsl.dsl.OrderDsl;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.lang.invoke.MethodHandles;

/*
  This is where the magic happens. The OrderDsl script is interpreted by Java's script engine (configured for interpreting
  Kotlin scripts). It returns an instance of OrderDsl configured by the script. No imports are necessary in the actual
  script file, as those imports are added through this class.

 Compare this file to the corresponding file in the Java version.
 */
public class DslUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static OrderDsl evalScript(String content, String fileName) {
        var dsl = new OrderDsl();
        try {
            evalScript(content, dsl);
        } catch (ScriptException e) {
            LOGGER.error("File " + fileName + "could not be parsed.");
            throw new RuntimeException(e);
        }
        return dsl;
    }

    private static void evalScript(String script, OrderDsl dsl) throws ScriptException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByExtension("kts");
        engine.put("dsl", dsl);
        String fullScript = "import kotlin.random.Random\n"
                + "import se.callistaenterprise.kotlindsl.dsl.*\n"
                + "dsl.apply { " + script + " }";
        engine.eval(fullScript);
    }
}

