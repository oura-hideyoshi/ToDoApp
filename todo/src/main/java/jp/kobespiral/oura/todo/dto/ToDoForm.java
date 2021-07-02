package jp.kobespiral.oura.todo.dto;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import jp.kobespiral.oura.todo.entity.ToDo;
import lombok.Data;


@Data
public class ToDoForm {

    @NotBlank
    @Size(min = 1, max = 32)
    String title;

    public ToDo toEntity() {
        ToDo t = new ToDo();
        t.setTitle(title);
        return t;
    }
}