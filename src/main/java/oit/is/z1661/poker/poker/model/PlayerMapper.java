package oit.is.z1661.poker.poker.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface PlayerMapper {
  @Insert("INSERT INTO player (Playername) VALUES (#{name});")
  void insertPlayerName(String name);

  @Update("UPDATE player SET handname = #{handname}, score = #{score} WHERE id = #{id}")
  void updateResult(int id, String handname, int score);

  @Select("SELECT id FROM player WHERE playername = #{loginuser}")
  int selectPlayerId(String loginuser);

  @Select("SELECT * FROM player")
  ArrayList<Player> selectAllPlayer();
}
