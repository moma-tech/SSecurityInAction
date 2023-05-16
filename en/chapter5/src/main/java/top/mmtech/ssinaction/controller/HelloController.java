package top.mmtech.ssinaction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.mmtech.ssinaction.service.BackLogic;

@RestController
public class HelloController {
  @Autowired BackLogic backLogic;

  @GetMapping("/hello")
  public String hello() {
    return "Hello!";
  }

  @GetMapping("/hello2")
  public String hello2(Authentication a) {
    return "Hello!" + a.getName();
  }

  @GetMapping("/hello3")
  public String hello3(Authentication a) {
    System.out.println("Hello!" + a.getName());
    backLogic.checkAsyncContextHolder();
    return "Hello!" + a.getName();
  }
}
