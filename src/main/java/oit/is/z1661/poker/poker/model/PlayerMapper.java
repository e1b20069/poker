package oit.is.z1661.poker.poker.model;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface PlayerMapper {
  @Insert("INSERT INTO player (Playername) VALUES (#{name});")
  void insertPlayerName(String name);

  @Update("UPDATE player SET hand = #{result}, score = #{score} WHERE ")
  void updateResult(int result, int score);
}
