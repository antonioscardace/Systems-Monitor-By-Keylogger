package systems.monitor.manager.requests;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import systems.monitor.manager.crypt.AesCrypt;
import systems.monitor.manager.utils.DateUtils;

// This class represents a request body for Record information, with encrypted fields.
// This class is used as the body to receive POST requests.
// @author Antonio Scardace
// @version 1.0

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RecordRequest {

    private String uuid;
    private String ipAddress;
    private String recordText;
    private String windowTitle;
    private String timestampBegin;
    private String timestampEnd;

    public String getUuid() {
        return AesCrypt.decrypt(this.uuid);
    }
    
    public String getIpAddress() {
        return AesCrypt.decrypt(this.ipAddress);
    }

    public String getRecordText() {
        return AesCrypt.decrypt(this.recordText);
    }

    public String getWindowTitle() {
        return AesCrypt.decrypt(this.windowTitle);
    }

    public LocalDateTime getTimestampBegin() {
        return DateUtils.stringToDateTime(AesCrypt.decrypt(this.timestampBegin));
    }

    public LocalDateTime getTimestampEnd() {
        return DateUtils.stringToDateTime(AesCrypt.decrypt(this.timestampEnd));
    }
}