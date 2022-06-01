package se.callistaenterprise.kotlindsl.monitoring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import se.callistaenterprise.kotlindsl.data.DataRepositories;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchService;

import static se.callistaenterprise.kotlindsl.data.DslUtil.evalScript;
import static se.callistaenterprise.kotlindsl.dsl.scriptdef.ScriptDefKt.ORDER_DSL_SUFFIX;

/*
  This service gets notified whenever a new file is put in /tmp, and will interpret any dsl file (i.e.
  files ending with orderdsl.kts), persisting the contents to the database.
 */
@Service
@Profile("!test")
public class MonitoringService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final WatchService watchService;
    private final DataRepositories repos;

    MonitoringService(WatchService watchService, DataRepositories repos) {
        this.watchService = watchService;
        this.repos = repos;
    }

    @Async
    @Scheduled(fixedDelay = Long.MAX_VALUE)
    public void launchMonitoring() throws InterruptedException, IOException {
        if (watchService != null) {
            while (true) {
                var key = watchService.take();
                if (key != null) {
                    for (WatchEvent event: key.pollEvents()) {
                        var file = (Path) event.context();
                        if (file.getFileName().toString().endsWith(ORDER_DSL_SUFFIX)) {
                            LOGGER.info("Found file " + file.getFileName() + ", will persist it.");
                            var dsl = evalScript(Files.readString(Paths.get("/tmp", file.toString())), file.getFileName().toString());
                            repos.persist(dsl, file.getFileName().toString());
                        }
                    }
                    key.reset();
                }
            }
        }
    }

    @PreDestroy
    public void stopMonitoring() throws IOException {
        if (watchService != null) {
            watchService.close();
        }
    }
}
