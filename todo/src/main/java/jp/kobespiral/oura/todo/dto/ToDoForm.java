package jp.kobespiral.oura.todo.dto;
import jp.kobespiral.oura.todo.entity.ToDo;
import lombok.Data;


@Data
public class ToDoForm {
    String title;

    public ToDo toEntity() {
        ToDo t = new ToDo();
        t.setTitle(title);
        return t;
    }
}