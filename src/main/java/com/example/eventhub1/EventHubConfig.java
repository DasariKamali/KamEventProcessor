package com.example.eventhub1;

import com.azure.messaging.eventhubs.EventProcessorClient;
import com.azure.messaging.eventhubs.EventProcessorClientBuilder;
import com.azure.messaging.eventhubs.checkpointstore.blob.BlobCheckpointStore;
import com.azure.storage.blob.BlobContainerAsyncClient;
import com.azure.storage.blob.BlobContainerClientBuilder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventHubConfig {

    @Value("${eventhub.connection-string}")
    private String eventHubConnectionString;

    @Value("${eventhub.name}")
    private String eventHubName;

    @Value("${eventhub.consumer-group}")
    private String consumerGroup;

    @Value("${azure.blob.connection-string}")
    private String blobStorageConnectionString;

    @Value("${azure.blob.container-name}")
    private String blobContainerName;

    @Bean
    public BlobContainerAsyncClient blobContainerAsyncClient() {
        return new BlobContainerClientBuilder()
                .connectionString(blobStorageConnectionString)
                .containerName(blobContainerName)
                .buildAsyncClient();
    }

    @Bean
    public EventProcessorClient eventProcessorClient(BlobContainerAsyncClient blobContainerAsyncClient,
                                                     EventHubProcessor eventHubProcessor) {

        return new EventProcessorClientBuilder()
                .connectionString(eventHubConnectionString, eventHubName)
                .consumerGroup(consumerGroup)
                .processEvent(eventHubProcessor::processEvent)
                .processError(eventHubProcessor::processError)
                .checkpointStore(new BlobCheckpointStore(blobContainerAsyncClient))
                .buildEventProcessorClient();
    }
}
