package com.sowa.project.service;

import com.sowa.project.model.dto.NoteRequest;
import com.sowa.project.model.Note;
import com.sowa.project.model.User;
import com.sowa.project.repository.NoteRepository;
import com.sowa.project.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    public NoteService(NoteRepository noteRepository,
                       UserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }

    private User getCurrentUser() {
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("User not found"));
    }

    public List<Note> getMyNotes() {
        return noteRepository.findAllByUser(getCurrentUser());
    }

    public void create(NoteRequest request) {
        Note note = new Note();
        note.setTitle(request.getTitle());
        note.setContent(request.getContent());
        note.setUser(getCurrentUser());

        noteRepository.save(note);
    }

    public void delete(Long noteId) {
        User user = getCurrentUser();

        Note note = noteRepository.findByIdAndUser(noteId, user)
                .orElseThrow(() -> new IllegalArgumentException("Note not found"));

        noteRepository.delete(note);
    }
}
