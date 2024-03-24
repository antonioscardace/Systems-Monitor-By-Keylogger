package systems.monitor.server.requests;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import systems.monitor.server.services.AesCryptService;
import systems.monitor.server.utils.Utils;

// This class represents a request body for Log information, with encrypted fields.
// This class is used to receive POST requests.
// @author Antonio Scardace
// @version 1.0

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LogRequest {

    private String uuid;
    private String ipAddress;
    private String logText;
    private String windowName;
    private String timestampBegin;
    private String timestampEnd;

    public String getUuid() {
        return AesCryptService.decrypt(this.uuid);
    }
    
    public String getIpAddress() {
        return AesCryptService.decrypt(this.ipAddress);
    }

    public String getLogText() {
        return AesCryptService.decrypt(this.logText);
    }

    public String getWindowName() {
        return AesCryptService.decrypt(this.windowName);
    }

    public LocalDateTime getTimestampBegin() {
        return Utils.stringToTimestamp(AesCryptService.decrypt(this.timestampBegin));
    }

    public LocalDateTime getTimestampEnd() {
        return Utils.stringToTimestamp(AesCryptService.decrypt(this.timestampEnd));
    }
}