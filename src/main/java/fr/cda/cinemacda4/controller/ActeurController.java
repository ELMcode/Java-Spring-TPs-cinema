package fr.cda.cinemacda4.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.cda.cinemacda4.dto.ActeurReduitDto;
import fr.cda.cinemacda4.dto.ActeurSansFilmDto;
import fr.cda.cinemacda4.dto.FilmSansActeurDto;
import fr.cda.cinemacda4.entity.Acteur;
import fr.cda.cinemacda4.service.ActeurService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/acteurs")
public class ActeurController {
    private final ActeurService acteurService;

    private final ObjectMapper objectMapper;


    public ActeurController(
            ActeurService acteurService,
            ObjectMapper objectMapper
    ) {
        this.acteurService = acteurService;
        this.objectMapper = objectMapper;
    }

    @PostMapping
    public Acteur save(@RequestBody Acteur entity) {
        return acteurService.save(entity);
    }

    @GetMapping("/{id}")
    public ActeurReduitDto findById(@PathVariable Integer id) {

        Acteur acteur = acteurService.findById(id);

        ActeurReduitDto acteurReduitDto = new ActeurReduitDto();

        acteurReduitDto.setId(acteur.getId());
        acteurReduitDto.setNom(acteur.getNom());
        acteurReduitDto.setPrenom(acteur.getPrenom());

        acteurReduitDto.setFilms(
                acteur.getFilms().stream().map(
                        film -> objectMapper.convertValue(film, FilmSansActeurDto.class)
                ).toList()
        );

        return acteurReduitDto;
    }

    @DeleteMapping("/{id}")
    public void delete(@RequestBody Acteur acteur) {
        acteurService.delete(acteur);
    }

    @GetMapping
    public List<ActeurSansFilmDto> findAll() {

        List<Acteur> acteurs = acteurService.findAll();

        return acteurs.stream().map(
                acteur -> objectMapper.convertValue(acteur, ActeurSansFilmDto.class)
        ).toList();
    }
}