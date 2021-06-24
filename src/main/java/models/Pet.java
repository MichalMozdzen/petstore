package models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(chain = true)
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Pet {

    public enum Status {
        AVAILABLE("available"),
        PENDING("pending"),
        SOLD("sold");

        public final String label;

        Status(String label) {
            this.label = label;
        }
    }

    private Long id;
    private String name;
    private Category category;
    private List<String> photoUrls;
    private List<Tag> tags;
    private String status;

}
