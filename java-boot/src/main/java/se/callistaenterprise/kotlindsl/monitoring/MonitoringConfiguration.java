package se.callistaenterprise.kotlindsl.monitoring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchService;

/*
  Register a file monitor which Monitors /tmp for all new files.
*/ 
@Configuration
@Profile("!test")
public class MonitoringConfiguration {

    @Bean
    public WatchService watchService() {
        try {
            var monitoringPath = Paths.get("/tmp");
            if (Files.isDirectory(monitoringPath)) {
                var watchService = FileSystems.getDefault().newWatchService();
                monitoringPath.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
                return watchService;
            }
            return null;
        } catch (IOException e) {
            throw new RuntimeException("Exception in watch service creation: ", e);
        }
    }
}
