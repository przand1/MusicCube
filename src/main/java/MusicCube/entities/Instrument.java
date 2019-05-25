package MusicCube.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Instrument {

    @Id
    @GeneratedValue
    @Column
    private int id;

    @Column(unique = true)
    private String instrumentName;

    @Column
    private String type;

    public Instrument() {}

    public Instrument(String instrumentName, String type) {
        this.instrumentName = instrumentName;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return instrumentName;
    }

    public void setName(String instrumentName) {
        this.instrumentName = instrumentName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
