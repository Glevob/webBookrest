package com.example.demo.controller.webs;

import com.example.demo.model.*;
import com.example.demo.repository.AuthorsBooksRepository;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.GenreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;


@Controller
public class BooksMainController {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private AuthorsBooksRepository authorsBooksRepository;

    @GetMapping("/book")
    public String bookMain(Model model) {
        Iterable<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);
        return "book-main";
    }

    @GetMapping("/book/add")
    public String bookAdd(Model model) {
        model.addAttribute("genres", genreRepository.findAll());
        return "book-add";
    }

    @GetMapping("/bookAnon")
    public String booksMainAnon(Model model) {
        model.addAttribute("title", "КнигиКнигиКниги");
        Iterable<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);
        return "bookAnon";
    }

    @GetMapping("/genre/add")
    public String genreAdd(Model model){
        return "genre-add";
    }

    @PostMapping("/genre/add")
    public String addGenre (@RequestParam String genreName, Model model){
        Genre genre = new Genre(genreName);
        genreRepository.save(genre);
        return "redirect:/book/add";
    }

    @PostMapping("/book/add")
    public String addBook(@RequestParam Long genreId,
                              @RequestParam String bookTitle,
                              @RequestParam LocalDate datePublic,
                          @RequestParam Integer price,
                          @RequestParam Integer star,
                          @RequestParam String descriptBook,
                          @RequestParam("cover") MultipartFile cover, Model model) {

        String coverPath = "dif_path";

        if (!cover.isEmpty()) {
            try {
                String fileName = cover.getOriginalFilename();
                Path path = Paths.get("src/main/resources/static/uploads/" + fileName);
                Files.write(path, cover.getBytes());
                coverPath = fileName;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Genre genre = genreRepository.findById(genreId).orElseThrow();

        Book book = new Book(bookTitle, datePublic, price, star, descriptBook, coverPath, genre);
        bookRepository.save(book);

        return "redirect:/book";
    }

    @GetMapping("/book/{id}")
    public String bookDetails(@PathVariable(value = "id") long id, Model model) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Неверный идентификатор книги: " + id));
        model.addAttribute("book", book);

        List<AuthorsBooks> links = authorsBooksRepository.findAllByBook(book);
        model.addAttribute("bookAuthors", links);

        return "book-details";
    }

    @GetMapping("/book/{id}/edit")
    public String bookEdit(@PathVariable(value = "id") long idbook, Model model) {
        Book book = bookRepository.findById(idbook).orElseThrow();
        model.addAttribute("book", book);
        model.addAttribute("genres", genreRepository.findAll());
        return "book-edit";
    }

    @PostMapping("/book/{id}/edit")
    public String bookUpdate(@PathVariable("id") long idbook,
                             @RequestParam Long genreId,
                             @RequestParam String bookTitle,
                             @RequestParam LocalDate datePublic,
                             @RequestParam Integer price,
                             @RequestParam Integer star,
                             @RequestParam String descriptBook,
                             @RequestParam("cover") MultipartFile cover) throws IOException {

        Book book = bookRepository.findById(idbook).orElseThrow();

        book.setBookTitle(bookTitle);
        book.setDatePublic(datePublic);
        book.setPrice(price);
        book.setStar(star);
        book.setDescriptBook(descriptBook);

        // если файл выбран — сохраняем новый и обновляем поле
        if (cover != null && !cover.isEmpty()) {
            String fileName = cover.getOriginalFilename();
            Path path = Paths.get("src/main/resources/static/uploads/" + fileName);
            Files.write(path, cover.getBytes());
            book.setCover(fileName);
        }
        // если файл НЕ выбран — ничего не делаем, остаётся старая обложка

        Genre genre = genreRepository.findById(genreId).orElseThrow();
        book.setGenre(genre);

        bookRepository.save(book);

        return "redirect:/book/" + idbook;
    }

    private static String getExtension(String filename) {
        int index = filename.lastIndexOf('.');
        if (index > 0 && index < filename.length() - 1) {
            return filename.substring(index + 1);
        }
        return "";
    }

    @Transactional
    @PostMapping("/book/{id}/delete")
    public String bookDelete(@PathVariable(value = "id") long idBook) {
        Book book = bookRepository.findById(idBook).orElseThrow();

        authorsBooksRepository.deleteAllByBook(book);

        bookRepository.delete(book);

        return "redirect:/book";
    }

}
