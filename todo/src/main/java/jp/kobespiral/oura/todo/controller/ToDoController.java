package jp.kobespiral.oura.todo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.kobespiral.oura.todo.dto.MemberForm;
import jp.kobespiral.oura.todo.dto.ToDoForm;
import jp.kobespiral.oura.todo.entity.Member;
import jp.kobespiral.oura.todo.entity.ToDo;
import jp.kobespiral.oura.todo.service.MemberService;
import jp.kobespiral.oura.todo.service.ToDoService;

@Controller
@RequestMapping("/member")
public class ToDoController {
  @Autowired
  ToDoService tService;
  @Autowired
  MemberService mService;

  /**
   * ログイン画面
   * 
   * @param model
   * @return
   */
  @GetMapping("/login") // ToDoApp/login とされたら
  String showLogin(Model model) {
    MemberForm form = new MemberForm();
    model.addAttribute("MemberForm", form); // login.html で ${MemberForm}が使えるようになる
    return "/login";
  }

  /**
   * 個人のToDo確認・登録ページ HTTP-GET /{mid}/todos
   * 
   * @param model
   * @param mid   パス・パラメータに含まれるID
   * @return
   */
  @GetMapping("/{mid}/todos")
  String showToDos(Model model, @PathVariable String mid) {

    Member member = mService.getMember(mid);
    List<ToDo> todo_list = tService.getToDoList(mid);
    List<ToDo> done_list = tService.getDoneList(mid);
    model.addAttribute("todo_list", todo_list);
    model.addAttribute("done_list", done_list);
    ToDoForm form = new ToDoForm();
    model.addAttribute("ToDoForm", form);
    model.addAttribute("name", member.getName());

    return "todos";
  }

  /**
   * メンバー用・ToDo登録確認ページを表示 HTTP-POST /member/{mid}/check
   * 
   * @param form
   * @param model
   * @param mid
   * @return
   */
  @PostMapping(value = "/{mid}/check")
  String checkToDoForm(@ModelAttribute(name = "ToDoForm") ToDoForm form, Model model, @PathVariable String mid) {
    ToDo t = tService.createToDo(mid, form);
    model.addAttribute("ToDoForm", t);
    return "todo_check";
  }

  @PostMapping("/{mid}/todos")
  String createToDo(@ModelAttribute(name = "ToDoForm") ToDoForm form, Model model, @PathVariable String mid) {
    tService.createToDo(mid, form);
    return showToDos(model, mid);
  }

  @GetMapping("/{mid}/done/{seq}")
   String doneToDo(@PathVariable String mid, @PathVariable String seq, Model model) {
       tService.setDone(Long.parseLong(seq));
       return showToDos(model, mid);
   }
}