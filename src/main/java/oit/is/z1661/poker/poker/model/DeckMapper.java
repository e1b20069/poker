package oit.is.z1661.poker.poker.model;

import org.apache.ibatis.annotations.Mapper;
import java.util.ArrayList;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DeckMapper {

  @Select("SELECT id from poker4")
  ArrayList<Deck> selectAllByDeckid();
}
