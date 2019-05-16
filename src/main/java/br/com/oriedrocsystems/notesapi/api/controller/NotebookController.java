package br.com.oriedrocsystems.notesapi.api.controller;

import br.com.oriedrocsystems.notesapi.api.dto.NotebookDTO;
import br.com.oriedrocsystems.notesapi.model.Notebook;
import br.com.oriedrocsystems.notesapi.repository.NotebookRepository;
import br.com.oriedrocsystems.notesapi.utils.Mapper;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notebooks")
@CrossOrigin
public class NotebookController {

    private NotebookRepository notebookRepository;
    private Mapper mapper;

    public NotebookController(NotebookRepository notebookRepository, Mapper mapper) {
        this.notebookRepository = notebookRepository;
        this.mapper = mapper;
    }

    @GetMapping("/all")
    public List<Notebook> all() {
        List<Notebook> allCategories = this.notebookRepository.findAll();
        return allCategories;
    }

    @PostMapping
    public Notebook save(@RequestBody NotebookDTO notebookDTO,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }

        Notebook notebookEntity = this.mapper.convertToNotebookEntity(notebookDTO);

        // save notebookEntity instance to db
        this.notebookRepository.save(notebookEntity);

        return notebookEntity;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        this.notebookRepository.deleteById(UUID.fromString(id));
    }
}
