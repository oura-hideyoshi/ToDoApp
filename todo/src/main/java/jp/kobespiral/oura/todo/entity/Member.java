package jp.kobespiral.oura.todo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor  // 本来java構文ではコンストラクターを作らねばクラスに引数が渡せないが、これが代わりをしている.
@Entity
public class Member {
    @Id
    String mid;   //メンバーID
    String name;  //氏名
}