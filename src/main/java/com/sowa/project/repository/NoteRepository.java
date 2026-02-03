package com.sowa.project.repository;

import com.sowa.project.model.Note;
import com.sowa.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findAllByUser(User user);

    Optional<Note> findByIdAndUser(Long id, User user);
}
