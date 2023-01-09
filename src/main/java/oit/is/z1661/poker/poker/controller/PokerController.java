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
import oit.is.z1661.poker.poker.model.Player;

@Controller
public class PokerController {
  Player user1 = new Player();
  ArrayList<Deck> ids;

  @Autowired
  DeckMapper DeckMapper;

  @GetMapping("/start")
  public String lobby() {
    return "start.html";
  }

  @PostMapping("/start")
  public String lobby2(@RequestParam String name, ModelMap model) {

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

    user1.Distribute(ids);
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
      user1.Exchange(0, ids);
    }
    if (h2) {
      user1.Exchange(1, ids);
    }
    if (h3) {
      user1.Exchange(2, ids);
    }
    if (h4) {
      user1.Exchange(3, ids);
    }
    if (h5) {
      user1.Exchange(4, ids);
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
