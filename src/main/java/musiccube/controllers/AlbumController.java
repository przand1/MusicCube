package musiccube.controllers;

import musiccube.entities.Band;
import musiccube.entities.Song;
import musiccube.services.album.AlbumService;
import musiccube.entities.Album;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "${serverAddress}")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    /************************ GET ********************************/
    @GetMapping(path = "/album/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Album> getById(@PathVariable("id") int id) {
        Optional<Album> album = albumService.getById(id);
        if (album.isPresent()) {
            return ResponseEntity.ok(album.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(path = "/album/all",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Album> getAll() {
        return albumService.getAll();
    }

    // --- Get all albums with paging ---
    @GetMapping(path = "album/page/{page}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Album> getAllPaging(@PathVariable("page") Integer pageNr, @RequestParam("size") Optional<Integer> perPage) {
        return albumService.getAllPaging(pageNr,perPage.orElse(10));
    }

    @GetMapping(path = "/album/{id}/bands",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Band> getAlbumBands(@PathVariable("id") int albumId) {
        return albumService.getAlbumBands(albumId);
    }

    @GetMapping(path = "/album/name/{name}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Album> getByAlbumName(@PathVariable("name") String albumName) {
        return albumService.getByAlbumName(albumName);
    }

    @GetMapping(path = "/album/{id}/songs",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Song> getAlbumSongs(@PathVariable("id") int albumId) {
        return albumService.getAlbumSongs(albumId);
    }

    /*************************************************************/


    @PostMapping("/album")
    public ResponseEntity<Album> create(@RequestBody @Valid @NotNull Album album) {
        albumService.save(album);
        return ResponseEntity.ok().body(album);
    }

    @PutMapping("/album")
    public ResponseEntity<Void> edit(@RequestBody @Valid @NotNull Album album) {
        Optional<Album> album1 = albumService.getById(album.getId());
        if (Objects.nonNull(album1)) {
            albumService.save(album);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ApiIgnore
    @DeleteMapping(path = "/albums",produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Album> redirect(Model model) {
        return albumService.getAll();
    }

    @DeleteMapping("/album/{id}")
    public RedirectView delete(@PathVariable Integer id) {
        albumService.delete(id);
        return new RedirectView("/api/albums",true);
    }
}
