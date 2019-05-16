package br.com.oriedrocsystems.notesapi.api.controller;

import br.com.oriedrocsystems.notesapi.api.dto.NoteDTO;
import br.com.oriedrocsystems.notesapi.model.Note;
import br.com.oriedrocsystems.notesapi.model.Notebook;
import br.com.oriedrocsystems.notesapi.repository.NoteRepository;
import br.com.oriedrocsystems.notesapi.repository.NotebookRepository;
import br.com.oriedrocsystems.notesapi.utils.Mapper;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notes")
@CrossOrigin
public class NoteController {
    private NoteRepository noteRepository;
    private NotebookRepository notebookRepository;
    private Mapper mapper;

    public NoteController(NoteRepository noteRepository, NotebookRepository notebookRepository, Mapper mapper) {
        this.noteRepository = noteRepository;
        this.notebookRepository = notebookRepository;
        this.mapper = mapper;
    }

    @GetMapping("/all")
    public List<NoteDTO> all() {
        List<Note> notes = this.noteRepository.findAll();

        // map from entity to view model
        List notesDTO = notes.stream()
                .map(note -> this.mapper.convertToNoteDTO(note))
                .collect(Collectors.toList());

        return notesDTO;
    }

    @GetMapping("/byId/{id}")
    public NoteDTO byId(@PathVariable String id) {
        Note note = this.noteRepository.findById(UUID.fromString(id)).orElse(null);

        if (note == null) {
            throw new EntityNotFoundException();
        }

        NoteDTO noteDTO = this.mapper.convertToNoteDTO(note);

        return noteDTO;
    }

    @GetMapping("/byNotebook/{notebookId}")
    public List<NoteDTO> byNotebook(@PathVariable String notebookId) {
        List<Note> notes = new ArrayList<>();

        Notebook notebook = this.notebookRepository.findById(UUID.fromString(notebookId)).orElse(null);

        if (notebook != null) {
            notes = this.noteRepository.findAllByNotebook(notebook);
        }

        // map to note view model
        List<NoteDTO> notesDTO = notes.stream()
                .map(note -> this.mapper.convertToNoteDTO(note))
                .collect(Collectors.toList());

        return notesDTO;
    }

    @PostMapping
    public Note save(@RequestBody NoteDTO noteDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }

        Note noteEntity = this.mapper.convertToNoteEntity(noteDTO);

        // save note instance to db
        this.noteRepository.save(noteEntity);

        return noteEntity;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        this.noteRepository.deleteById(UUID.fromString(id));
    }
}
