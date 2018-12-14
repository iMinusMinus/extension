package ml.iamwhatiam;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
public class AbstractEntity implements Serializable {

    protected Long id;

    protected String creator;

    protected Date creationDate;

    protected String revisor;

    protected Date revisionDate;
}
