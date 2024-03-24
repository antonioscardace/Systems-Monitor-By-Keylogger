package systems.monitor.server.requests;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import systems.monitor.server.services.AesCryptService;
import systems.monitor.server.utils.Utils;

// This class represents a request body for Device information, with encrypted fields.
// This class is used to receive POST requests.
// @author Antonio Scardace
// @version 1.0

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DeviceRequest {

    private String uuid;
    private String cpuDesc;
    private String deviceName;
    private String deviceType;
    private String keyboardLayout;
    private String osName;
    private String username;
    private String registeredOn;

    public String getUuid() {
        return AesCryptService.decrypt(this.uuid);
    }

    public String getCpuDesc() {
        return AesCryptService.decrypt(this.cpuDesc);
    }

    public String getDeviceName() {
        return AesCryptService.decrypt(this.deviceName);
    }

    public String getDeviceType() {
        return AesCryptService.decrypt(this.deviceType);
    }

    public String getKeyboardLayout() {
        return AesCryptService.decrypt(this.keyboardLayout);
    }

    public String getOsName() {
        return AesCryptService.decrypt(this.osName);
    }

    public String getUsername() {
        return AesCryptService.decrypt(this.username);
    }

    public LocalDateTime getRegisteredOn() {
        return Utils.stringToTimestamp(AesCryptService.decrypt(this.registeredOn));
    }
}