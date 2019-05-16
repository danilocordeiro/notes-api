package br.com.oriedrocsystems.notesapi.utils;

import br.com.oriedrocsystems.notesapi.api.dto.NoteDTO;
import br.com.oriedrocsystems.notesapi.api.dto.NotebookDTO;
import br.com.oriedrocsystems.notesapi.model.Note;
import br.com.oriedrocsystems.notesapi.model.Notebook;
import br.com.oriedrocsystems.notesapi.repository.NotebookRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Mapper {

    private NotebookRepository notebookRepository;

    public Mapper(NotebookRepository notebookRepository) {
        this.notebookRepository = notebookRepository;
    }

    public NoteDTO convertToNoteDTO(Note entity) {
        NoteDTO dto = new NoteDTO();
        dto.setTitle(entity.getTitle());
        dto.setId(entity.getId().toString());
        dto.setLastModifiedOn(entity.getLastModifiedOn());
        dto.setText(entity.getText());
        dto.setNotebookId(entity.getNotebook().getId().toString());

        return dto;
    }

    public Note convertToNoteEntity(NoteDTO dto) {
        Notebook notebook = this.notebookRepository.findById(UUID.fromString(dto.getNotebookId())).get();
        Note entity = new Note(dto.getId(), dto.getTitle(), dto.getText(), notebook);

        return entity;
    }

    public NotebookDTO convertToNotebookDTO(Notebook entity) {
        NotebookDTO dto = new NotebookDTO();
        dto.setId(entity.getId().toString());
        dto.setName(entity.getName());
        dto.setNbNotes(entity.getNotes().size());

        return dto;
    }

    public Notebook convertToNotebookEntity(NotebookDTO dto) {
        Notebook entity = new Notebook(dto.getId(), dto.getName());

        return entity;
    }
}
