package com.example.eventhub1;

import com.azure.messaging.eventhubs.EventProcessorClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import jakarta.annotation.PreDestroy;

@Service
public class EventHubProcessorService implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(EventHubProcessorService.class);

    private final EventProcessorClient eventProcessorClient;

    public EventHubProcessorService(EventProcessorClient eventProcessorClient) {
        this.eventProcessorClient = eventProcessorClient;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("Starting EventProcessorClient...");
        eventProcessorClient.start();
    }

    @PreDestroy
    public void shutdown() {
        logger.info("Stopping EventProcessorClient...");
        eventProcessorClient.stop();
    }
}
