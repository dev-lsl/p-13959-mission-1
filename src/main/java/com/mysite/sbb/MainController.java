package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
    @GetMapping("/sbb")
    @ResponseBody
    public String index() {
        return "sbb에 오신 것을 환영합니다. 안녕하쇼";
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/question/list";
    }

}
