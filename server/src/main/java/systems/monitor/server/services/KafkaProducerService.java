package systems.monitor.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;

// This class is a simple Kafka Producer.
// @author Antonio Scardace
// @version 1.0

@Log
@Service
public class KafkaProducerService {

    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    
    public Boolean sendMessage(String topicName, String message) {
        try {
            kafkaTemplate.send(topicName, message);
            return true;
        }
        catch (Exception e) {
            log.warning(e.getMessage());
            return false;
        }
    }
}