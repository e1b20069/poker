package oit.is.z1661.poker.poker.service;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.z1661.poker.poker.model.SDeck;
import oit.is.z1661.poker.poker.model.SDeckMapper;

@Service
public class AsyncPokerService {
  boolean dbUpdated = false;

  private final Logger logger = LoggerFactory.getLogger(AsyncPokerService.class);

  @Autowired
  SDeckMapper sDeckMapper;

  @Transactional
  public void syncdeletesDeck(int number) {
    sDeckMapper.deleteBynumber1(number);
    this.dbUpdated = true;
  }

  public ArrayList<SDeck> syncShowSDeckList() {
    return sDeckMapper.selectAllSDecks();
  }

  @Async
  public void asyncShowFruitsList(SseEmitter emitter) {
    dbUpdated = true;
    try {
      while (true) {
        if (false == dbUpdated) {
          TimeUnit.MILLISECONDS.sleep(500);
          continue;
        }
        ArrayList<SDeck> sDecks = this.syncShowSDeckList();
        emitter.send(sDecks);
        TimeUnit.MILLISECONDS.sleep(1000);
        dbUpdated = false;
      }
    } catch (Exception e) {
      logger.warn("Exception:" + e.getClass().getName() + ":" + e.getMessage());
    } finally {
      emitter.complete();
    }
    System.out.println("AsyncPokerService complete");
  }

}
