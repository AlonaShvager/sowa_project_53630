package com.sowa.project.controller;

import com.sowa.project.model.dto.NoteRequest;
import com.sowa.project.service.NoteService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public String notes(Model model) {
        model.addAttribute("notes", noteService.getMyNotes());
        model.addAttribute("noteRequest", new NoteRequest());
        return "notes";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute NoteRequest noteRequest,
                         BindingResult result,
                         Model model) {

        if (result.hasErrors()) {
            model.addAttribute("notes", noteService.getMyNotes());
            return "notes";
        }

        noteService.create(noteRequest);
        return "redirect:/notes";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        noteService.delete(id);
        return "redirect:/notes";
    }
}
