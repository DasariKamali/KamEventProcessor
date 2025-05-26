package com.example.eventhub1;

import com.azure.messaging.eventhubs.models.EventContext;
import com.azure.messaging.eventhubs.models.ErrorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class EventHubProcessor {

    private static final Logger logger = LoggerFactory.getLogger(EventHubProcessor.class);

    public void processEvent(EventContext eventContext) {
        try {
            String eventData = eventContext.getEventData().getBodyAsString();
            logger.info("Partition {}: Event received: {}", eventContext.getPartitionContext().getPartitionId(), eventData);
            eventContext.updateCheckpoint();
        } catch (Exception e) {
            logger.error("Error processing event", e);
        }
    }

    public void processError(ErrorContext errorContext) {
        logger.error("Error in EventProcessorClient on partition {}: {}",
                errorContext.getPartitionContext() != null
                        ? errorContext.getPartitionContext().getPartitionId()
                        : "N/A",
                errorContext.getThrowable().getMessage(), errorContext.getThrowable());
    }
}
