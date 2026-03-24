package com.example.demo.controller.webs;

import com.example.demo.model.Author;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.AuthorsBooksRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthorWebController {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorsBooksRepository authorsBooksRepository;

    @GetMapping("/author")
    public String authorMain(Model model) {
        Iterable<Author> authors = authorRepository.findAll();
        model.addAttribute("authors", authors);
        return "author-main";
    }

    @GetMapping("/author/add")
    public String authorAdd(Model model) {
        model.addAttribute("author", new Author());
        return "author-add";
    }

    @PostMapping("/author/add")
    public String addAuthor(@Valid @ModelAttribute("author") Author author,
                            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "author-add";
        }

        if (authorRepository.existsByAuthorEmail(author.getAuthorEmail())) {
            bindingResult.rejectValue(
                    "authorEmail",
                    "authorEmail.duplicate",
                    "Автор с такой почтой уже существует"
            );
            return "author-add";
        }

        authorRepository.save(author);
        return "redirect:/author";
    }

    @GetMapping("/author/{id}")
    public String authorDetails(@PathVariable("id") long id, Model model) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Неверный идентификатор автора: " + id));
        model.addAttribute("author", author);
        return "author-details";
    }

    @GetMapping("/author/{id}/edit")
    public String authorEdit(@PathVariable("id") long idauthor, Model model) {
        Author author = authorRepository.findById(idauthor).orElseThrow();
        model.addAttribute("author", author);
        return "author-edit";
    }

    @PostMapping("/author/{id}/edit")
    public String authorUpdate(@PathVariable("id") long idauthor,
                               @Valid @ModelAttribute("author") Author author,
                               BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "author-edit";
        }

        authorRepository.findByAuthorEmail(author.getAuthorEmail())
                .ifPresent(existing -> {
                    if (!existing.getIdAuthor().equals(idauthor)) {
                        bindingResult.rejectValue(
                                "authorEmail",
                                "authorEmail.duplicate",
                                "Автор с такой почтой уже существует"
                        );
                    }
                });

        if (bindingResult.hasErrors()) {
            return "author-edit";
        }

        author.setIdAuthor(idauthor);
        authorRepository.save(author);

        return "redirect:/author/" + idauthor;
    }

    @Transactional
    @PostMapping("/author/{id}/delete")
    public String authorDelete(@PathVariable("id") long idauthor) {
        Author author = authorRepository.findById(idauthor).orElseThrow();
        authorsBooksRepository.deleteAllByAuthor(author);
        authorRepository.delete(author);
        return "redirect:/author";
    }
}
