package musiccube.entities;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.Date;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "album", schema = "music_cube")
public class Album {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String albumName;

    @Column(name = "length")
    private int albumLengthSeconds;

    @Column(name = "release")
    private Date releaseDate;

    @Column(name = "company")
    private String company;

    @Column(name = "cover")
    private String coverArtLink;

    @Column(name = "type")
    private String type;

    @Column(name = "tracks")
    private int trackCount;

    public Album() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public int getAlbumLengthSeconds() {
        return albumLengthSeconds;
    }

    public void setAlbumLengthSeconds(int albumLengthSeconds) {
        this.albumLengthSeconds = albumLengthSeconds;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCoverArtLink() {
        return coverArtLink;
    }

    public void setCoverArtLink(String coverArtLink) {
        this.coverArtLink = coverArtLink;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTrackCount() {
        return trackCount;
    }

    public void setTrackCount(int trackCount) {
        this.trackCount = trackCount;
    }
}