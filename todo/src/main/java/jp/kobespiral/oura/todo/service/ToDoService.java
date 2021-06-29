package jp.kobespiral.oura.todo.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.kobespiral.oura.todo.dto.ToDoForm;
import jp.kobespiral.oura.todo.entity.ToDo;
import jp.kobespiral.oura.todo.exception.ToDoAppException;
import jp.kobespiral.oura.todo.repository.ToDoRepository;

@Service
public class ToDoService {
   @Autowired
   ToDoRepository tRepo;
   /**
    * ToDoを作成する (C)
    * @param mid
    * @param form
    * @return
    */
   public ToDo createToDo(String mid, ToDoForm form) {
       ToDo t = form.toEntity();
       t.setMid(mid);
       t.setDone(false);
       t.setCreatedAt(new Date());
       return tRepo.save(t);
   }
   /**
    * seqを指定してToDoを取得する (R)
    * @param mid
    * @return
    */
   public ToDo getToDo(Long seq) {
       ToDo t = tRepo.findById(seq).orElseThrow(
               () -> new ToDoAppException(ToDoAppException.NO_SUCH_MEMBER_EXISTS, seq + ": No such seq ToDo exists"));
       return t;
   }
   /**
    * seqを指定してToDoをDoneにする (U)
    * @param mid
    * @return
    */
    public void setDone(Long seq){
        System.out.println(tRepo.findById(seq).get());
        ToDo t = tRepo.findById(seq).get();
        tRepo.delete(t);
        t.setDone(true);
        t.setDoneAt(new Date());
        tRepo.save(t);
        // System.out.println(tRepo.findById(seq).get());
        // おそらく
    }

   /**
    * 特定のメンバーの全ToDoを取得する (R)
    * @return
    */
   public List<ToDo> getToDoList(String mid) {
       return tRepo.findByMidAndDone(mid, false);
   }
   /**
    * 特定のメンバーの全ToDo Doneを取得する (R)
    */
   public List<ToDo> getDoneList(String mid) {
       return tRepo.findByMidAndDone(mid, true);
   }
   /**
    * 全員のToDoリストを取得
    * @return
    */
   public List<ToDo> getToDoList(){
       return tRepo.findByDone(false);
   }

   /**
    * 全員のDoneリストを取得
    */
   public List<ToDo> getDoneList(){
       return tRepo.findByDone(true);
   }
   
}