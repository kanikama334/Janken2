package oit.is.z0932.kaizi.janken.Controller;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z0932.kaizi.janken.model.Matches;
import oit.is.z0932.kaizi.janken.model.MatchesMapper;
import oit.is.z0932.kaizi.janken.model.UserMapper;
import oit.is.z0932.kaizi.janken.model.Users;

/**
 * Sample21Controller
 *
 * クラスの前に@Controllerをつけていると，HTTPリクエスト（GET/POSTなど）があったときに，このクラスが呼び出される
 */
@Controller
public class JankenController {

  // @GetMapping("/janken/{name}")
  // public String janken(@PathVariable String name, ModelMap model) {
  // model.addAttribute("name", name);
  // return "janken.html";
  // }
  @Autowired
  UserMapper usermapper;
  @Autowired
  MatchesMapper matchesmapper;

  @GetMapping("/janken")
  public String janken(Principal prin, ModelMap model) {
    String loginUser = prin.getName(); // ログインユーザ情報
    ArrayList<Matches> matches3 = matchesmapper.selectAll();
    ArrayList<Users> user3 = usermapper.selectAll();
    model.addAttribute("users3", user3);
    model.addAttribute("matches3", matches3);
    model.addAttribute("UserName", loginUser);
    return "janken.html";
  }

  @GetMapping("/match")
  public String match(@RequestParam Integer id, Principal prin, ModelMap model) {
    String loginUser = prin.getName(); // ログインユーザ情報
    String hoge = usermapper.selectById(id);
    model.addAttribute("UserName", loginUser);
    model.addAttribute("hogeName", hoge);
    return "match.html";
  }

  @GetMapping("/fight")
  public String fight(@RequestParam String name, String hand, Principal prin, ModelMap model) {
    String loginUser = prin.getName(); // ログインユーザ情報
    int hoge = usermapper.selectByName(name);
    int myId = usermapper.selectByName(loginUser);
    Matches match = new Matches();
    match.setUser1(myId);
    match.setUser2(hoge);
    match.setUser1Hand(hand);
    match.setUser2Hand("グー");
    String result = "";
    if (hand.equals("グー")) {
      result = "引き分け";
    } else if (hand.equals("チョキ")) {
      result = "You Lose";
    } else if (hand.equals("パー")) {
      result = "You Win";
    } else {
      result = hand;
    }
    matchesmapper.insertMatchInfo(match);
    model.addAttribute("result", result);
    model.addAttribute("UserName", loginUser);
    model.addAttribute("hogeName", name);
    return "wait.html";
  }

  @GetMapping("/JankenGame/{param1}")
  public String JankenGame(@PathVariable String param1, ModelMap model) {
    String result = "";
    if (param1.equals("グー")) {
      result = "引き分け";
    } else if (param1.equals("チョキ")) {
      result = "You Lose";
    } else if (param1.equals("パー")) {
      result = "You Win";
    } else {
      result = param1;
    }
    // ModelMap型変数のmodelにtasuResult1という名前の変数で，tasuResultの値を登録する．
    // ここで値を登録するとthymeleafが受け取り，htmlで処理することができるようになる
    model.addAttribute("param1", param1);
    model.addAttribute("result", result);
    return "jankenGame.html";

  }

  // /*

  // // @GetMapping("/janken")
  // // public String janken() {
  // // return "janken.html";
  // // }

  // /**
  // * sample21というGETリクエストがあったら sample21()を呼び出し，sample21.htmlを返す
  // */
  // @GetMapping("/sample21")
  // public String sample21() {
  // return "sample21.html";
  // }

  // @GetMapping("/sample24")
  // public String sample24() {
  // return "sample24.html";
  // }

  // /**
  // * パスパラメータ2つをGETで受け付ける 1つ目の変数をparam1という名前で，2つ目の変数をparam2という名前で受け取る
  // * GETで受け取った2つの変数とsample22()の引数の名前が同じなため， 引数の前に @PathVariable
  // と付けるだけで，パスパラメータの値を
  // * javaで処理できるようになる ModelMapはthymeleafに渡すためのMapと呼ばれるデータ構造を持つ変数
  // * Mapはkeyとvalueの組み合わせで値を保持する
  // *
  // * @param param1
  // * @param param2
  // * @param model
  // * @return
  // */
  // @GetMapping("/sample22/{param1}/{param2}")
  // public String sample22(@PathVariable String param1, @PathVariable String
  // param2, ModelMap model) {
  // int tasu = Integer.parseInt(param1);// param1が文字列なので，parseIntでint型の数値に変換する
  // int tasareru = Integer.parseInt(param2);
  // int tasuResult = tasu + tasareru;

  // // ModelMap型変数のmodelにtasuResult1という名前の変数で，tasuResultの値を登録する．
  // // ここで値を登録するとthymeleafが受け取り，htmlで処理することができるようになる
  // model.addAttribute("tasuResult1", tasuResult);
  // return "sample21.html";

  // }

  // /**
  // * クエリパラメータの引数2つを受け付ける URLでの?のあとのパラメータ名とjavaメソッドの引数名は同じであることが望ましい(別にする方法は一応ある)
  // *
  // 引数をStringとして受け取ってparseIntする以外にもInteger(intのラッパークラス)クラスの変数として受け取ってそのまま加算する方法もある
  // *
  // * @param tasu1
  // * @param tasu2
  // * @param model
  // * @return
  // */
  // @GetMapping("/sample23")
  // public String sample23(@RequestParam Integer tasu1, @RequestParam Integer
  // tasu2, ModelMap model) {
  // int tasuResult = tasu1 + tasu2;
  // model.addAttribute("tasuResult2", tasuResult);
  // // ModelMap型変数のmodelにtasuResult2という名前の変数で，tasuResultの値を登録する．
  // // ここで値を登録するとthymeleafが受け取り，htmlで処理することができるようになる
  // return "sample21.html";

  // }

  // /**
  // * POSTを受け付ける場合は@PostMappingを利用する
  // /sample25へのPOSTを受け付けて，FormParamで指定された変数(input
  // * name)をsample25()メソッドの引数として受け取ることができる
  // *
  // * @param kakeru1
  // * @param kakeru2
  // * @param model
  // * @return
  // */
  // @PostMapping("/sample25")
  // public String sample25(@RequestParam Integer kakeru1, @RequestParam Integer
  // kakeru2, ModelMap model) {
  // int kakeruResult = kakeru1 * kakeru2;
  // model.addAttribute("kakeruResult", kakeruResult);
  // return "sample24.html";
  // }

}
