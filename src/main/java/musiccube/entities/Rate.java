package musiccube.entities;

import javax.persistence.*;

@Entity
@Table(name = "rate")
public class Rate {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Song song;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Album album;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Band band;

    @ManyToOne
    @JoinColumn(name = "artist", referencedColumnName = "personid")
    private Artist artist;

    @Column
    private int rate;

    public Rate() {
    }

    private Rate(User user, int rate) {
        this.user = user;
        this.rate = rate;
    }

    public Rate(User user, Song song, int rate) {
        this(user, rate);
        this.song = song;
    }

    public Rate(User user, Album album, int rate) {
        this(user, rate);
        this.album = album;
    }

    public Rate(User user, Band band, int rate) {
        this(user, rate);
        this.band = band;
    }

    public Rate(User user, Artist artist, int rate) {
        this(user, rate);
        this.artist = artist;
    }


    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Band getBand() {
        return band;
    }

    public void setBand(Band band) {
        this.band = band;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}
