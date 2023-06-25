package top.mmtech.ssinaction.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

  @GetMapping("/main")
  public String home() {
    return "aaaaaaa";
  }


  @GetMapping("/hello")
  public String hello() {
    return "hello";
  }


  @GetMapping("/ciao")
  public String ciao() {
    return "ciao";
  }
}
