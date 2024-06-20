package systems.monitor.manager.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// It represents a composite Primary Key for the entity {@link Record} of the table "records".
// It is composed by "uuid", and "timestamp_begin" fields. The first one is a Foreign Key.
// @author Antonio Scardace
// @version 1.0

@NoArgsConstructor
@AllArgsConstructor
@Data
@Embeddable
public class RecordId {

    @Column(name = "uuid", nullable = false)
    private String uuid;

    @Column(name = "timestamp_begin", nullable = false)
    private LocalDateTime timestampBegin;
}