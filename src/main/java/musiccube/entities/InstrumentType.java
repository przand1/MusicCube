package musiccube.entities;


import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "instrument_type")
public class InstrumentType {

    @Id
    @Column
    @GeneratedValue
    private int id;

    @Column(unique = true)
    private String instrumentTypeName;

    @Column(length = 1024)
    private String aboutType;

    public InstrumentType() {
    }

    public InstrumentType(String typeName) {
        this.instrumentTypeName = typeName;
    }

    public int getId() {
        return id;
    }

    public String getInstrumentTypeName() {
        return instrumentTypeName;
    }

    public String getAboutType() {
        return aboutType;
    }

    public void setInstrumentTypeName(String instrumentTypeName) {
        this.instrumentTypeName = instrumentTypeName;
    }

    public void setAboutType(String aboutType) {
        this.aboutType = aboutType;
    }

}
