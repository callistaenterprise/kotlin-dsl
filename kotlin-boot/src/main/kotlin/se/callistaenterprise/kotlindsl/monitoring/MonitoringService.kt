package se.callistaenterprise.kotlindsl.monitoring

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import se.callistaenterprise.kotlindsl.data.DataRepositories
import se.callistaenterprise.kotlindsl.data.DslUtil.Companion.evalScript
import se.callistaenterprise.kotlindsl.dsl.scriptdef.ORDER_DSL_SUFFIX
import java.lang.invoke.MethodHandles
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.WatchService
import javax.annotation.PreDestroy
import kotlin.io.path.readText

/*
  This service gets notified whenever a new file is put in /tmp, and will interpret any dsl file (i.e.
  files ending with orderdsl.kts), persisting the contents to the database.
 */
@Service
@Profile("!test")
class MonitoringService(val watchService: WatchService?, val repos: DataRepositories) {

    private val logger: Logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass())

    @Async
    @Scheduled(fixedDelay = Long.MAX_VALUE)
    fun launchMonitoring() {
        if (watchService != null) {
            while (true) {
                val key = watchService!!.take()
                if (key != null) {
                    for (event in key.pollEvents()) {
                        val file = event.context() as Path
                        if (file.fileName.toString().endsWith(ORDER_DSL_SUFFIX)) {
                            logger.info("Found file ${file.fileName}, will persist it.")
                            val dsl = evalScript(Paths.get("/tmp", file.toString()).readText(), file.fileName.toString())
                            repos.persist(dsl, file.fileName.toString())
                        }
                    }
                    key.reset()
                }
            }
        }
    }

    @PreDestroy
    fun stopMonitoring() {
        watchService?.close()
    }
}
