package systems.monitor.server.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// This class represents the "logs" entity in the database.
// The Primary Key is the composite field "id" defined by the Embedded class {@link LogId}. It includes the uuid, and the timestamp of begin.
// The "uuid" field is a Many-to-One relationship to the "uuid" column in the {@link Device} entity.
// @author Antonio Scardace
// @version 1.0

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "logs")
public class Log {

    @EmbeddedId
    private LogId id;

    @ManyToOne
	@JoinColumn(name = "uuid", referencedColumnName = "uuid", insertable = false, updatable = false)
    private Device device;

    @Column(name = "window_name", nullable = false)
    private String windowName;

    @Column(name = "log_text", nullable = false)
    private String logText;

    @Column(name = "ip_address", nullable = false)
    private String ipAddress;

    @Column(name = "timestamp_end", nullable = false)
    private LocalDateTime timestampEnd;
}