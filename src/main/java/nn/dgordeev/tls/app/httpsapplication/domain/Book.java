package nn.dgordeev.tls.app.httpsapplication.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class Book extends BaseEntity implements Serializable {
    private String name;
    private Integer year;
    private String author;
    private float rate;
    private Integer pages;
    private Genre genre;

    public enum Genre {
        CLASSIC, NONFICTION, FANTASY
    }
}
