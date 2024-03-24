package systems.monitor.server.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.java.Log;

// Utility class providing various helper methods.
// @author Antonio Scardace
// @version 1.0

@Log
public class Utils {
 
    private Utils() {

    }

    // Converts a LocalDateTime object to a string representation of timestamp.
    // The format of the timestamp string follows the pattern "yyyy-MM-dd HH:mm:ss".
    
    public static String datetimeToString(LocalDateTime timestamp) {
        return timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    // Parses a string representing a timestamp to a LocalDateTime object.
    // The string representation of the timestamp must have format "yyyy-MM-dd HH:mm:ss".

    public static LocalDateTime stringToTimestamp(String datetime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(datetime, formatter);
    }

    // Converts an object to its JSON representation.
    // The "object" is generic. If it cannot be converted to JSON the method returns null.

    public static <T> String objectToJson(T object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(object);
        }
        catch (JsonProcessingException e) {
            log.warning(e.getMessage());
            return null;
        }
    }
}