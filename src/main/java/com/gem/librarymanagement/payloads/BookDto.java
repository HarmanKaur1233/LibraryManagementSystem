package com.gem.librarymanagement.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    String authorName;
    String publisherName;
    String bookName;
    String publishDate;
}
