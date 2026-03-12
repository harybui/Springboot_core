package com.example.bookstore_api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateAuthorDto {
    @NotBlank(message = "Tên tác giả không được để trống")
    @Size(min = 2, max = 100)
    private String name;
}
