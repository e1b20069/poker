package oit.is.z1661.poker.poker.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.z1661.poker.poker.model.Deck;
import oit.is.z1661.poker.poker.model.DeckMapper;
import oit.is.z1661.poker.poker.model.PlayerMapper;
import oit.is.z1661.poker.poker.model.SDeckMapper;
import oit.is.z1661.poker.poker.service.AsyncPokerService;
import oit.is.z1661.poker.poker.model.Player;

@Controller
public class PokerController {
  Player user1 = new Player();
  Player user2 = new Player();
  Player user3 = new Player();
  Player user4 = new Player();
  ArrayList<Deck> ids = new ArrayList<Deck>();

  @Autowired
  DeckMapper DeckMapper;

  @Autowired
  SDeckMapper sDeckMapper;

  @Autowired
  PlayerMapper playerMapper;

  @Autowired
  AsyncPokerService pDeck;

  @PostMapping("/start")
  public String lobby(@RequestParam String name, ModelMap model) {
    playerMapper.insertPlayerName(name);
    int loginid = playerMapper.selectPlayerId(name);
    if (loginid == 1) {
      user1.setId(loginid);
    }
    if (loginid == 2) {
      user2.setId(loginid);
    }
    if (loginid == 3) {
      user3.setId(loginid);
    }
    if (loginid == 4) {
      user4.setId(loginid);
    }
    ArrayList<Player> nops = playerMapper.selectAllPlayer();
    model.addAttribute("playerid1", nops.get(0).getId());
    model.addAttribute("playername1", nops.get(0).getplayername());
    if (nops.size() >= 2) {
      model.addAttribute("playerid2", nops.get(1).getId());
      model.addAttribute("playername2", nops.get(1).getplayername());
    }
    if (nops.size() >= 3) {
      model.addAttribute("playerid3", nops.get(2).getId());
      model.addAttribute("playername3", nops.get(2).getplayername());
    }
    if (nops.size() >= 4) {
      model.addAttribute("playerid4", nops.get(3).getId());
      model.addAttribute("playername4", nops.get(3).getplayername());
    }
    model.addAttribute("name", name);
    return "start.html";
  }

  @PostMapping("/poker4")
  public String poker41(@RequestParam String id, ModelMap model) {
    Deck d = new Deck(1);
    this.ids.add(d);
    if (ids.size() == 1) {
      this.ids.remove(0);
      this.ids = DeckMapper.selectAllByDeckid();
      System.out.printf("%d\n", this.ids.size());
      for (int i = 0; i < this.ids.size(); ++i) {
        int rnd = (int) (Math.random() * (double) this.ids.size());
        int w = this.ids.get(i).getDeckid();
        this.ids.get(i).setDeckid(this.ids.get(rnd).getDeckid());
        this.ids.get(rnd).setDeckid(w);
      }

      for (int j = 0; j < ids.size(); ++j) {
        sDeckMapper.insertsdecknumber(this.ids.get(j).getDeckid());
      }
    }

    if (id.equals("1")) {
      user1.Distribute(sDeckMapper);
      model.addAttribute("Hand1", user1.getHand().get(0));
      model.addAttribute("Hand2", user1.getHand().get(1));
      model.addAttribute("Hand3", user1.getHand().get(2));
      model.addAttribute("Hand4", user1.getHand().get(3));
      model.addAttribute("Hand5", user1.getHand().get(4));
    } else if (id.equals("2")) {
      user2.Distribute(sDeckMapper);
      model.addAttribute("Hand1", user2.getHand().get(0));
      model.addAttribute("Hand2", user2.getHand().get(1));
      model.addAttribute("Hand3", user2.getHand().get(2));
      model.addAttribute("Hand4", user2.getHand().get(3));
      model.addAttribute("Hand5", user2.getHand().get(4));
    } else if (id.equals("3")) {
      user3.Distribute(sDeckMapper);
      model.addAttribute("Hand1", user3.getHand().get(0));
      model.addAttribute("Hand2", user3.getHand().get(1));
      model.addAttribute("Hand3", user3.getHand().get(2));
      model.addAttribute("Hand4", user3.getHand().get(3));
      model.addAttribute("Hand5", user3.getHand().get(4));
    } else if (id.equals("4")) {
      user4.Distribute(sDeckMapper);
      model.addAttribute("Hand1", user4.getHand().get(0));
      model.addAttribute("Hand2", user4.getHand().get(1));
      model.addAttribute("Hand3", user4.getHand().get(2));
      model.addAttribute("Hand4", user4.getHand().get(3));
      model.addAttribute("Hand5", user4.getHand().get(4));
    }
    model.addAttribute("id", id);

    return "poker4.html";
  }

  @PostMapping("/result")
  public String poker42(@RequestParam String id, ModelMap model) {

    if ((id.equals("1"))) {
      int result = user1.getPokerHand(user1.getHand());
      String handname = user1.HandName(result);
      model.addAttribute("handname", handname);
      int score = 0;
      score = user1.getScore();
      playerMapper.updateResult(Integer.parseInt(id), handname, score);
      model.addAttribute("score", score);
    }
    if (id.equals("2")) {
      int result = user2.getPokerHand(user2.getHand());
      String handname = user2.HandName(result);
      model.addAttribute("handname", handname);
      int score = 0;
      score = user2.getScore();
      playerMapper.updateResult(Integer.parseInt(id), handname, score);
      model.addAttribute("score", score);
    }
    if (id.equals("3")) {
      int result = user3.getPokerHand(user3.getHand());
      String handname = user3.HandName(result);
      model.addAttribute("handname", handname);
      int score = 0;
      score = user3.getScore();
      playerMapper.updateResult(Integer.parseInt(id), handname, score);
      model.addAttribute("score", score);
    }
    if (id.equals("4")) {
      int result = user4.getPokerHand(user4.getHand());
      String handname = user4.HandName(result);
      model.addAttribute("handname", handname);
      int score = 0;
      score = user4.getScore();
      playerMapper.updateResult(Integer.parseInt(id), handname, score);
      model.addAttribute("score", score);
    }
    model.addAttribute("id", id);

    ArrayList<Player> nops = playerMapper.selectAllPlayer();
    model.addAttribute("ID1", nops.get(0).getId());
    model.addAttribute("name1", nops.get(0).getplayername());
    model.addAttribute("handname1", nops.get(0).getHandname());
    model.addAttribute("score1", nops.get(0).getScore());
    if (nops.size() >= 2) {
      model.addAttribute("ID2", nops.get(1).getId());
      model.addAttribute("name2", nops.get(1).getplayername());
      model.addAttribute("handname2", nops.get(1).getHandname());
      model.addAttribute("score2", nops.get(1).getScore());
    }
    if (nops.size() >= 3) {
      model.addAttribute("ID3", nops.get(2).getId());
      model.addAttribute("name3", nops.get(2).getplayername());
      model.addAttribute("handname3", nops.get(2).getHandname());
      model.addAttribute("score3", nops.get(2).getScore());
    }
    if (nops.size() >= 4) {
      model.addAttribute("ID4", nops.get(3).getId());
      model.addAttribute("name4", nops.get(3).getplayername());
      model.addAttribute("handname4", nops.get(3).getHandname());
      model.addAttribute("score4", nops.get(3).getScore());
    }

    return "result.html";
  }

  @RequestMapping("/exchange")
  public String exchange(@RequestParam String id, ModelMap model,
      @RequestParam(value = "h1", required = false) boolean h1,
      @RequestParam(value = "h2", required = false) boolean h2,
      @RequestParam(value = "h3", required = false) boolean h3,
      @RequestParam(value = "h4", required = false) boolean h4,
      @RequestParam(value = "h5", required = false) boolean h5) {

    if (id.equals("1")) {
      if (h1) {
        user1.Exchange(0, sDeckMapper);
      }
      if (h2) {
        user1.Exchange(1, sDeckMapper);
      }
      if (h3) {
        user1.Exchange(2, sDeckMapper);
      }
      if (h4) {
        user1.Exchange(3, sDeckMapper);
      }
      if (h5) {
        user1.Exchange(4, sDeckMapper);
      }
      user1.sort(user1.getHand());

      model.addAttribute("Hand1", user1.getHand().get(0));
      model.addAttribute("Hand2", user1.getHand().get(1));
      model.addAttribute("Hand3", user1.getHand().get(2));
      model.addAttribute("Hand4", user1.getHand().get(3));
      model.addAttribute("Hand5", user1.getHand().get(4));
    }
    if (id.equals("2")) {
      if (h1) {
        user2.Exchange(0, sDeckMapper);
      }
      if (h2) {
        user2.Exchange(1, sDeckMapper);
      }
      if (h3) {
        user2.Exchange(2, sDeckMapper);
      }
      if (h4) {
        user2.Exchange(3, sDeckMapper);
      }
      if (h5) {
        user2.Exchange(4, sDeckMapper);
      }
      user2.sort(user2.getHand());

      model.addAttribute("Hand1", user2.getHand().get(0));
      model.addAttribute("Hand2", user2.getHand().get(1));
      model.addAttribute("Hand3", user2.getHand().get(2));
      model.addAttribute("Hand4", user2.getHand().get(3));
      model.addAttribute("Hand5", user2.getHand().get(4));
    }
    if (id.equals("3")) {
      if (h1) {
        user3.Exchange(0, sDeckMapper);
      }
      if (h2) {
        user3.Exchange(1, sDeckMapper);
      }
      if (h3) {
        user3.Exchange(2, sDeckMapper);
      }
      if (h4) {
        user3.Exchange(3, sDeckMapper);
      }
      if (h5) {
        user3.Exchange(4, sDeckMapper);
      }
      user3.sort(user3.getHand());

      model.addAttribute("Hand1", user3.getHand().get(0));
      model.addAttribute("Hand2", user3.getHand().get(1));
      model.addAttribute("Hand3", user3.getHand().get(2));
      model.addAttribute("Hand4", user3.getHand().get(3));
      model.addAttribute("Hand5", user3.getHand().get(4));
    }
    if (id.equals("4")) {
      if (h1) {
        user4.Exchange(0, sDeckMapper);
      }
      if (h2) {
        user4.Exchange(1, sDeckMapper);
      }
      if (h3) {
        user4.Exchange(2, sDeckMapper);
      }
      if (h4) {
        user4.Exchange(3, sDeckMapper);
      }
      if (h5) {
        user4.Exchange(4, sDeckMapper);
      }
      user4.sort(user4.getHand());

      model.addAttribute("Hand1", user4.getHand().get(0));
      model.addAttribute("Hand2", user4.getHand().get(1));
      model.addAttribute("Hand3", user4.getHand().get(2));
      model.addAttribute("Hand4", user4.getHand().get(3));
      model.addAttribute("Hand5", user4.getHand().get(4));
    }
    model.addAttribute("id", id);

    return "poker4.html";
  }

  @GetMapping("/push")
  public SseEmitter sample59() {
    final SseEmitter sseEmitter = new SseEmitter();
    this.pDeck.asyncShowSDeckList(sseEmitter);
    return sseEmitter;
  }
}
