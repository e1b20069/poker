package oit.is.z1661.poker.poker.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SDeckMapper {
  @Select("SELECT num from sdeck LIMIT 1")
  int selectBynumber1();

  @Insert("INSERT INTO sdeck (num) VALUES (#{number});")
  void insertsdecknumber(int number);

  @Delete("DELETE FROM sdeck WHERE num =#{number}")
  boolean deleteBynumber1(int number);

  @Select("SELECT num from sdeck")
  ArrayList<SDeck> selectAllSDecks();
}
