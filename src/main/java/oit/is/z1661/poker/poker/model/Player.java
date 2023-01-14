package oit.is.z1661.poker.poker.model;

import java.util.ArrayList;

public class Player {
  int id;
  String playername;
  String handname;
  int score = 0;
  ArrayList<Integer> hand = new ArrayList<Integer>();

  // エラー
  static final int ERROR = 0;
  // ポーカーの役の定数を定義しています。
  // ロイヤルフラッシュ（ロイヤルストレートフラッシュ）
  static final int ROYAL_FLUSH = 1;
  // ストレートフラッシュ
  static final int STRAIGHT_FLUSH = 2;
  // フォーカード
  static final int FOUR_OF_A_KIND = 3;
  // フルハウス
  static final int FULL_HOUSE = 4;
  // フラッシュ
  static final int FLUSH = 5;
  // ストレート
  static final int STRAIGHT = 6;
  // スリーカード
  static final int THREE_OF_A_KIND = 7;
  // ツーペア
  static final int TWO_PAIR = 8;
  // ワンペア
  static final int ONE_PAIR = 9;
  // ノーペア
  static final int NO_PAIR = 10;

  public Player() {
  }

  public void swap(ArrayList<Integer> list, int index1, int index2) {
    int tmp = list.get(index1);
    list.set(index1, list.get(index2));
    list.set(index2, tmp);
  }

  public void sort(ArrayList<Integer> list) {
    for (int i = 0; i < list.size() - 1; i++) {
      int min = i;

      for (int j = i + 1; j < list.size(); j++) {
        if (list.get(j) % 100 <= list.get(min) % 100) {
          if (list.get(j) % 100 == list.get(min) % 100) {
            if (list.get(j) / 100 <= list.get(min) / 100) {
              swap(list, j, min);
            }
          } else {
            swap(list, j, min);
          }
        }
      }
    }
  }

  public int getPokerHand(ArrayList<Integer> cards) {
    // 配列がnullだったらエラー
    if (null == cards)
      return ERROR;

    // 配列の個数が5でなければエラー
    if (5 != cards.size())
      return ERROR;

    // トランプのマークの個数を格納する配列
    int[] suit = new int[4];

    // トランプのマークの個数に0を代入（初期化）
    for (int i = 0; i < suit.length; i++)
      suit[i] = 0;

    // トランプの番号(1-13)の個数を格納する配列
    // number[ 0 ]とnumber[13]の両方にAの個数を格納
    int[] number = new int[14];

    // トランプのマークの個数に0を代入（初期化）
    for (int i = 0; i < number.length; i++)
      number[i] = 0;

    // ５枚のカードのマークと番号の個数を格納
    for (int i = 0; i < cards.size(); i++) {
      // マーク
      int mark = cards.get(i) / 100;
      // 番号
      int num = cards.get(i) % 100;
      // markが1から4の範囲外であればエラー
      if ((1 > mark) || (4 < mark)) {
        return ERROR;
      }

      // numが1から13の範囲外であればエラー
      if ((1 > num) || (13 < num)) {
        return ERROR;
      }

      // マークの個数に１を足す
      ++suit[mark - 1];

      // 番号の個数に１を足す
      ++number[num - 1];
    }
    // number[ 13 ]にAの個数を代入
    number[13] = number[0];
    // 番号の個数の最大値を取得
    int number_max = 0;
    for (int i = 0; i < number.length - 1; i++) {
      if (number_max < number[i])
        number_max = number[i];
    }

    // ここから判定処理

    // 個数の最大が４の場合、フォーカード確定
    if (4 == number_max)
      return FOUR_OF_A_KIND;

    // マークの個数の最大値を取得
    // ５枚のカードが同じマークの場合、suit_max=5となる
    int suit_max = 0;
    for (int i = 0; i < suit.length; i++) {
      if (suit_max < suit[i])
        suit_max = suit[i];
    }

    // ストレートの判定
    boolean isStraight = false;
    int continuous1 = 0;
    int firstnum = 0;
    for (int i = 0; i < number.length; ++i) {
      if (1 != number[i]) {
        continuous1 = 0;
        firstnum = 0;
      } else {
        ++continuous1;
        // ストレートの最初の番号を格納
        if (1 == continuous1)
          firstnum = i + 1;

        // ５回連続だったら
        if (5 == continuous1) {
          // ストレートは確定
          isStraight = true;
          break;
        }
      }
    }

    // マークが全て同じで、ストレートだったら
    if ((5 == suit_max) && isStraight) {
      // ストレートの先頭の番号が10だったら
      if (10 == firstnum) {
        // ロイヤルフラッシュ確定
        return ROYAL_FLUSH;
      }
      // ストレートフラッシュ確定
      return STRAIGHT_FLUSH;
    }

    // 個数の最大が３で、もう１つペアが存在したら
    if (3 == number_max) {
      for (int i = 0; i < number.length - 1; ++i) {
        if (2 == number[i]) {
          // フルハウス確定
          return FULL_HOUSE;
        }
      }
    }

    // マークが全て同じだったら
    if (5 == suit_max) {
      // フラッシュ確定
      return FLUSH;
    }

    // ストレートだったら
    if (isStraight) {
      // ストレート確定
      return STRAIGHT;
    }

    // 個数の最大が３の場合、スリーカード確定
    if (3 == number_max)
      return THREE_OF_A_KIND;

    // ペアの個数
    int pair_num = 0;
    for (int i = 0; i < number.length - 1; ++i) {
      if (2 == number[i])
        ++pair_num;
    }

    // ペアが２つであれば
    if (2 == pair_num)
      return TWO_PAIR;
    // ペアが１つであれば
    if (1 == pair_num)
      return ONE_PAIR;

    // ノーペア
    return NO_PAIR;
  }

  // 番号を文字に変換
  public String getStringNumber(int num) {
    switch (num) {
      case 1:
        return "A";
      case 2:
        return "2";
      case 3:
        return "3";
      case 4:
        return "4";
      case 5:
        return "5";
      case 6:
        return "6";
      case 7:
        return "7";
      case 8:
        return "8";
      case 9:
        return "9";
      case 10:
        return "10";
      case 11:
        return "J";
      case 12:
        return "Q";
      case 13:
        return "K";
    }

    return "";
  }

  public String HandName(int result) {
    if (result == 1) {
      score += 1000;
      return "ロイヤルストレートフラッシュ";
    } else if (result == 2) {
      score += 100;
      return "ストレートフラッシュ";
    } else if (result == 3) {
      score += 50;
      return "フォーカード";
    } else if (result == 4) {
      score += 40;
      return "フルハウス";
    } else if (result == 5) {
      score += 30;
      return "フラッシュ";
    } else if (result == 6) {
      score += 25;
      return "ストレート";
    } else if (result == 7) {
      score += 20;
      return "スリーカード";
    } else if (result == 8) {
      score += 10;
      return "ツーペア";
    } else if (result == 9) {
      score += 5;
      return "ワンペア";
    } else if (result == 10) {
      score += 0;
      return "ノーペア";
    } else {
      return "ERROR";
    }
  }

  public void Distribute(SDeckMapper deck) {

    if (hand.size() < 5) {
      for (int i = 0; i < 5; i++) {
        int tmp = deck.selectBynumber1();
        this.hand.add(tmp);
        deck.deleteBynumber1(tmp);
      }
      sort(this.hand);
    }
  }

  public void Exchange(int index, SDeckMapper deck) {

    int tmp = deck.selectBynumber1();
    this.hand.remove(index);
    this.hand.add(index, tmp);
    deck.deleteBynumber1(tmp);

  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getplayername() {
    return playername;
  }

  public void setplayername(String name) {
    this.playername = name;
  }

  public String getHandname() {
    return handname;
  }

  public void setHandname(String handname) {
    this.handname = handname;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public ArrayList<Integer> getHand() {
    return hand;
  }

  public void setHand(ArrayList<Integer> hand) {
    this.hand = hand;
  }
}
