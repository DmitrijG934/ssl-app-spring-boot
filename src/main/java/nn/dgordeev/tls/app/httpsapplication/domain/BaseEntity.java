package nn.dgordeev.tls.app.httpsapplication.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(exclude = "id")
public abstract class BaseEntity {
    protected UUID id = UUID.randomUUID();
}
