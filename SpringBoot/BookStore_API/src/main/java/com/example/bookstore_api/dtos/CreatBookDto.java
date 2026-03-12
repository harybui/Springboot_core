package com.example.bookstore_api.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;
@Data
public class CreatBookDto {
    @NotBlank(message = "Tiêu đề không được bỏ trống")
    @Size(max = 255)
    private String title;

    @NotNull(message = "Giá bán không được để trống")
    @Min(value = 0)
    private Double price;

    @Size(max = 20000)
    private String description;

    @NotNull(message = "Thể loại không được để trống")
    private Long categoryId;

    private List<Long> authorIds;
}
