package systems.monitor.manager.requests;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import systems.monitor.manager.crypt.AesCrypt;
import systems.monitor.manager.utils.DateUtils;

// This class represents a request body for Device information, with encrypted fields.
// This class is used as the body to receive POST requests.
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
        return AesCrypt.decrypt(this.uuid);
    }

    public String getCpuDesc() {
        return AesCrypt.decrypt(this.cpuDesc);
    }

    public String getDeviceName() {
        return AesCrypt.decrypt(this.deviceName);
    }

    public String getDeviceType() {
        return AesCrypt.decrypt(this.deviceType);
    }

    public String getKeyboardLayout() {
        return AesCrypt.decrypt(this.keyboardLayout);
    }

    public String getOsName() {
        return AesCrypt.decrypt(this.osName);
    }

    public String getUsername() {
        return AesCrypt.decrypt(this.username);
    }

    public LocalDateTime getRegisteredOn() {
        return DateUtils.stringToDateTime(AesCrypt.decrypt(this.registeredOn));
    }
}