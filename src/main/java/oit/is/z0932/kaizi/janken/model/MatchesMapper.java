package oit.is.z0932.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.fasterxml.jackson.databind.deser.DataFormatReaders.Match;

@Mapper
public interface MatchesMapper {

  @Select("SELECT * from matches")
  ArrayList<Matches> selectAll();

  @Insert("INSERT INTO matches (user1,user2,user1Hand,user2Hand) VALUES (#{user1},#{user2},#{user1Hand},#{user2Hand});")
  void insertMatchInfo(Matches match);
  /**
   * #{userName}などはinsertの引数にあるChamberクラスのフィールドを表しています 引数に直接String
   * userNameなどと書いてもいけるはず
   * 下記のOptionsを指定すると，insert実行時にAuto incrementされたIDの情報を取得できるようになる useGeneratedKeys
   * = true -> Keyは自動生成されることを表す keyColumn : keyになるテーブルのカラム名 keyProperty :
   * keyになるJavaクラスのフィールド名
   *
   * @param Matches
   */
}
