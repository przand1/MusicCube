package musiccube.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import springfox.documentation.annotations.ApiIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "mbId"})
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Artist extends Person {

    @Column
    private String stageName;

    private String mbId;

    public Artist() {}

    public Artist(City origin, String firstNames, String lastName, Date birthDate, Date deathDate, String stageName) {
        super(origin, firstNames, lastName, birthDate, deathDate);
        this.stageName = stageName;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public String getMbId() {
        return mbId;
    }

    public void setMbId(String mbId) {
        this.mbId = mbId;
    }
}