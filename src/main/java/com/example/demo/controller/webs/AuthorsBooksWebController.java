package com.example.demo.controller.webs;

import com.example.demo.model.*;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.AuthorsBooksRepository;
import com.example.demo.repository.BookRepository;
import com.example.demo.dto.BookWithAuthorsDto;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorsBooksWebController {
    @Autowired
    private AuthorsBooksRepository authorsBooksRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping("/authorsBooks")
    public String authorsBooksMain(Model model) {
        List<AuthorsBooks> authorsBookss = (List<AuthorsBooks>) authorsBooksRepository.findAll();

        Map<Book, List<AuthorsBooks>> byBook = authorsBookss.stream()
                .collect(Collectors.groupingBy(AuthorsBooks::getBook));

        List<BookWithAuthorsDto> booksWithAuthors = byBook.entrySet().stream()
                .map(entry -> {
                    Book book = entry.getKey();
                    List<AuthorsBooks> links = entry.getValue();

                    List<BookWithAuthorsDto.AuthorLink> authorLinks = links.stream()
                            .map(link -> new BookWithAuthorsDto.AuthorLink(link.getAuthor(), link.getIdAuthorsBooks()))
                            .collect(Collectors.toList());

                    return new BookWithAuthorsDto(book, authorLinks);
                })
                .toList();

        model.addAttribute("booksWithAuthors", booksWithAuthors);
        return "authorsBooks-main";
    }

    @GetMapping("/authorsBooks/add")
    public String authorsBooksAdd(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        model.addAttribute("authors", authorRepository.findAll());
        return "authorsBooks-add";
    }

    @GetMapping("/authorsBooksAnon")
    public String authorsBooksMainAnon(Model model) {
        model.addAttribute("title", "Связки");
        Iterable<AuthorsBooks> authorsBookss = authorsBooksRepository.findAll();
        model.addAttribute("authorsBookss", authorsBookss);
        return "authorsBooksAnon";
    }

    @PostMapping("/authorsBooks/add")
    public String addBook(@RequestParam Long bookId,
                          @RequestParam Long authorId, @RequestParam String authorRole, Model model) {

        Book book = bookRepository.findById(bookId).orElseThrow();
        Author author = authorRepository.findById(authorId).orElseThrow();

        AuthorsBooks authorsBooks = new AuthorsBooks(book, author, authorRole);
        authorsBooksRepository.save(authorsBooks);

        return "redirect:/authorsBooks";
    }

    @GetMapping("/authorsBooks/{id}")
    public String authorsBooksDetails(@PathVariable(value = "id") long id, Model model) {
        AuthorsBooks authorsBooks = authorsBooksRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Неверный идентификатор связки: " + id));
        model.addAttribute("authorsBooks", authorsBooks);
        return "authorsBooks-details";
    }

    @GetMapping("/authorsBooks/{id}/edit")
    public String authorsBooksEdit(@PathVariable(value = "id") long idauthorsBooks, Model model) {
        AuthorsBooks authorsBooks = authorsBooksRepository.findById(idauthorsBooks).orElseThrow();
        model.addAttribute("authorsBooks", authorsBooks);
        model.addAttribute("books", bookRepository.findAll());
        model.addAttribute("authors", authorRepository.findAll());
        return "authorsBooks-edit";
    }

    @PostMapping("/authorsBooks/{id}/edit")
    public String bookUpdate(@PathVariable(value = "id") long idauthorsBooks,
                             @RequestParam Long bookId,
                             @RequestParam Long authorId,
                             @RequestParam String authorRole) {
        AuthorsBooks authorsBooks = authorsBooksRepository.findById(idauthorsBooks).orElseThrow();
        authorsBooks.setAuthorRole(authorRole);

        Book book = bookRepository.findById(bookId).orElseThrow();
        Author author = authorRepository.findById(authorId).orElseThrow();

        authorsBooks.setBook(book);
        authorsBooks.setAuthor(author);

        authorsBooksRepository.save(authorsBooks);

        return "redirect:/authorsBooks/" + idauthorsBooks;
    }

    @PostMapping("/authorsBooks/{id}/delete")
    public String authorsBooksDelete(@PathVariable(value = "id") long idauthorsBooks) {

        AuthorsBooks authorsBooks = authorsBooksRepository.findById(idauthorsBooks).orElseThrow();

        authorsBooksRepository.delete(authorsBooks);

        return "redirect:/authorsBooks";
    }
}
