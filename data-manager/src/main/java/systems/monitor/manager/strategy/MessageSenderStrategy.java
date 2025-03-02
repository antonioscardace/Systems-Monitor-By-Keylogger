package systems.monitor.manager.strategy;

import com.fasterxml.jackson.core.JsonProcessingException;
import systems.monitor.manager.requests.RecordRequest;

// Strategy interface for sending a record to a message queue.
// @author Antonio Scardace
// @version 1.0

public interface MessageSenderStrategy {
    void sendMessage(RecordRequest req) throws JsonProcessingException;
}