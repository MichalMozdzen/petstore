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

    public enum Status {AVAILABLE, PENDING, SOLD}

    //REQUIRED: name, photoURLs
    private Long id;
    private String name;
    private Category category;
    private List<String> photoUrls;
    private List<Tag> tags;
    private Status status;

}
