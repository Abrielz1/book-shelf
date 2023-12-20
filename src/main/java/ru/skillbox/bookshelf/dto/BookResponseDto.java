package ru.skillbox.bookshelf.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookResponseDto implements Serializable {

    private Long Id;

    private String nameAuthor;

    private String bookName;

    private String category;
}
