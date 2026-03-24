package com.example.demo.dto;

import com.example.demo.model.Author;
import com.example.demo.model.Book;

import java.util.List;

public class BookWithAuthorsDto {

    public static class AuthorLink {
        private final Author author;
        private final Long authorsBooksId;

        public AuthorLink(Author author, Long authorsBooksId) {
            this.author = author;
            this.authorsBooksId = authorsBooksId;
        }

        public Author getAuthor() {
            return author;
        }

        public Long getAuthorsBooksId() {
            return authorsBooksId;
        }
    }

    private final Book book;
    private final List<AuthorLink> authorLinks;

    public BookWithAuthorsDto(Book book, List<AuthorLink> authorLinks) {
        this.book = book;
        this.authorLinks = authorLinks;
    }

    public Book getBook() {
        return book;
    }

    public List<AuthorLink> getAuthorLinks() {
        return authorLinks;
    }
}
