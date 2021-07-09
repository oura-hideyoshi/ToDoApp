package jp.kobespiral.oura.todo.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class LoginForm {
    @NotBlank
    String title;

}
