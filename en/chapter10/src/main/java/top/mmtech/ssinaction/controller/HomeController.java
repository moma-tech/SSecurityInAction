package top.mmtech.ssinaction.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

  @GetMapping("/main")
  @ResponseBody
  public String home() {
    return "aaaaaaa";
  }

  @GetMapping("/ivan")
  public String main() {
    return "main.html";
  }

  @GetMapping("/hello")
  @ResponseBody
  public String hello() {
    return "hello";
  }

  @GetMapping("/ciao")
  @ResponseBody
  public String ciao() {
    return "ciao";
  }

  @PostMapping("/test")
  @ResponseBody
  @CrossOrigin("http://localhost:8080")
  public String test() {
    return "testtest";
  }
}
