package systems.monitor.manager.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.java.Log;

// This class is a service component and is a simple Kafka Producer.
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

    private String mapToJson(Map<String, String> object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }
    
    public void sendMessage(String topicName, String message) {
        kafkaTemplate.send(topicName, message);
        log.info(String.format("Message sent to Apache Kafka on topic [%s].", topicName));
    }

    public void sendMessage(String topicName, Map<String, String> message) throws JsonProcessingException {
        String parsedMessage = this.mapToJson(message);
        this.sendMessage(topicName, parsedMessage);
    }
}