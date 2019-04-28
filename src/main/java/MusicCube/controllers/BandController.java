package MusicCube.controllers;

import MusicCube.entities.Band;
import MusicCube.services.band.BandService;
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
@CrossOrigin(origins = "http://localhost:4200")
public class BandController {

    @Autowired
    private BandService bandService;

    @RequestMapping(value = "/band{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<Band> getById(int id) {
        return bandService.getById(id);
    }

    @RequestMapping(value = "/bands",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Band> getAll() {
        return bandService.getAll();
    }

    @RequestMapping(value = "/band",method = RequestMethod.POST)
    public ResponseEntity<Band> create(@RequestBody @Valid @NotNull Band band) {
        bandService.save(band);
        return ResponseEntity.ok().body(band);
    }

    @RequestMapping(value = "/band",method = RequestMethod.PUT)
    public ResponseEntity<Void> edit(@RequestBody @Valid @NotNull Band band) {
        Optional<Band> band1 = bandService.getById(band.getId());
        if (Objects.nonNull(band1)) {
            bandService.save(band);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ApiIgnore
    @RequestMapping(value = "/bands",method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Band> redirect(Model model) {
        return bandService.getAll();
    }

    @RequestMapping(value = "/band/{id}", method = RequestMethod.DELETE)
    public RedirectView delete(@PathVariable Integer id) {
        bandService.delete(id);
        return new RedirectView("/api/bands",true);
    }



}