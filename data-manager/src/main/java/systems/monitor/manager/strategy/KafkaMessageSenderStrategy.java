package systems.monitor.manager.strategy;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;

import systems.monitor.manager.requests.RecordRequest;
import systems.monitor.manager.services.KafkaProducerService;
import systems.monitor.manager.utils.DateUtils;

// Concrete Strategy that implements the interface for sending a record to Kafka.
// Takes the request data, creates the JSON message, and sends it.
// @author Antonio Scardace
// @version 1.0

@Service
public class KafkaMessageSenderStrategy implements MessageSenderStrategy {

    private final KafkaProducerService kafkaProducerService;
    private final String topicName = Optional.ofNullable(System.getenv("KAFKA_TOPIC")).orElse("unknown");

    @Autowired
    public KafkaMessageSenderStrategy(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    @Override
    public void sendMessage(RecordRequest req) throws JsonProcessingException {
        Map<String, String> map = new HashMap<>();
        map.put("uuid", req.getUuid());
        map.put("window_title", req.getWindowTitle());
        map.put("ip_address", req.getIpAddress());
        map.put("timestamp_begin", DateUtils.dateTimeToString(req.getTimestampBegin()));
        map.put("timestamp_end", DateUtils.dateTimeToString(req.getTimestampEnd()));
        kafkaProducerService.sendMessage(topicName, map);
    }
}