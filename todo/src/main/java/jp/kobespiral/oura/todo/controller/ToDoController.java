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
    System.out.println("login");
    String mid = new String();
    model.addAttribute("mid", mid); // login.html で ${ }が使えるようになる
    return "/login";
  }

  /**
   * ログイン -> ここ(midが正しいか判断) ->
   * 
   * @param model
   * @param mid
   * @return
   */
  @GetMapping("/login-tmp")
  String login(@ModelAttribute(name = "mid") String mid, Model model) {
    System.out.println("login check mid : " + mid);
    // TODO midの処理

    return "redirect:/member/" + mid + "/todos";
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
    ToDoForm form = new ToDoForm();
    model.addAttribute("todo_list", todo_list);
    model.addAttribute("done_list", done_list);
    model.addAttribute("form", form);
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
  String checkToDoForm(@ModelAttribute(name = "form") ToDoForm form, Model model, @PathVariable String mid) {
    System.out.println("<check> titel : " + form.getTitle());
    model.addAttribute("form", form);
    return "todo_check";
  }

  @PostMapping("/{mid}/todos")
  String createToDo(@ModelAttribute(name = "title") String title, Model model, @PathVariable String mid) {
    ToDoForm form = new ToDoForm();
    form.setTitle(title);
    tService.createToDo(mid, form);
    return "redirect:/member/" + mid + "/todos";
  }

  @GetMapping("/{mid}/done/{seq}")
  String doneToDo(@PathVariable String mid, @PathVariable String seq, Model model) {
    tService.setDone(Long.parseLong(seq));
    return "redirect:/member/" + mid + "/todos";
  }

  @GetMapping("/{mid}/everyone")
  String goEveryone(Model model, @PathVariable String mid) {
    List<ToDo> todo_list = tService.getToDoList();
    List<ToDo> done_list = tService.getDoneList();
    List<Member> member_list = mService.getAllMembers();
    model.addAttribute("todo_list_e", todo_list);
    model.addAttribute("done_list_e", done_list);
    model.addAttribute("member_list", member_list);
    return "/everyone";
  }
}