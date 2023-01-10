package oit.is.z1661.poker.poker.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import oit.is.z1661.poker.poker.model.Deck;
import oit.is.z1661.poker.poker.model.DeckMapper;
import oit.is.z1661.poker.poker.model.PlayerMapper;
import oit.is.z1661.poker.poker.model.SDeckMapper;
import oit.is.z1661.poker.poker.model.Player;

@Controller
public class PokerController {
  Player user1 = new Player();
  ArrayList<Deck> ids;

  @Autowired
  DeckMapper DeckMapper;

  @Autowired
  SDeckMapper SDeckMapper;

  @Autowired
  PlayerMapper playerMapper;

  @PostMapping("/start")
  public String lobby(@RequestParam String name, ModelMap model) {
    playerMapper.insertPlayerName(name);
    model.addAttribute("name", name);
    return "start.html";
  }

  @GetMapping("/poker4")
  public String poker41(ModelMap model) {
    ids = DeckMapper.selectAllByDeckid();
    System.out.printf("%d\n", ids.size());
    // user1.getHand().clear();
    for (int i = 0; i < ids.size(); ++i) {
      int rnd = (int) (Math.random() * (double) ids.size());
      int w = ids.get(i).getDeckid();
      ids.get(i).setDeckid(ids.get(rnd).getDeckid());
      ids.get(rnd).setDeckid(w);
    }

    for (int j = 0; j < ids.size(); ++j) {
      SDeckMapper.insertsdecknumber(ids.get(j).getDeckid());
    }

    user1.Distribute(SDeckMapper);
    model.addAttribute("Hand1", user1.getHand().get(0));
    model.addAttribute("Hand2", user1.getHand().get(1));
    model.addAttribute("Hand3", user1.getHand().get(2));
    model.addAttribute("Hand4", user1.getHand().get(3));
    model.addAttribute("Hand5", user1.getHand().get(4));
    return "poker4.html";
  }

  @PostMapping("/poker4")
  public String poker42(ModelMap model) {
    int result;
    int score;

    result = user1.getPokerHand(user1.getHand());
    String handname = user1.HandName(result);
    model.addAttribute("handname", handname);
    score = user1.getScore();
    playerMapper.updateResult(result, score);
    model.addAttribute("score", score);
    return "poker4.html";
  }

  @RequestMapping("/exchange")
  public String exchange(ModelMap model,
      @RequestParam(value = "h1", required = false) boolean h1,
      @RequestParam(value = "h2", required = false) boolean h2,
      @RequestParam(value = "h3", required = false) boolean h3,
      @RequestParam(value = "h4", required = false) boolean h4,
      @RequestParam(value = "h5", required = false) boolean h5) {

    if (h1) {
      user1.Exchange(0, SDeckMapper);
    }
    if (h2) {
      user1.Exchange(1, SDeckMapper);
    }
    if (h3) {
      user1.Exchange(2, SDeckMapper);
    }
    if (h4) {
      user1.Exchange(3, SDeckMapper);
    }
    if (h5) {
      user1.Exchange(4, SDeckMapper);
    }

    user1.sort(user1.getHand());

    model.addAttribute("Hand1", user1.getHand().get(0));
    model.addAttribute("Hand2", user1.getHand().get(1));
    model.addAttribute("Hand3", user1.getHand().get(2));
    model.addAttribute("Hand4", user1.getHand().get(3));
    model.addAttribute("Hand5", user1.getHand().get(4));
    return "poker4.html";
  }
}
