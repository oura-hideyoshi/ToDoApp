package jp.kobespiral.oura.todo.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import jp.kobespiral.oura.todo.entity.Member;//変える

@Repository
public interface MemberRepository extends CrudRepository<Member, String>{
    List<Member> findAll();
}
