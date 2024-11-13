package com.timkoar.tkserver.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FrontendController {
    @RequestMapping(value = { "/", "/contact", "/about", "/blog", "/**/{[path:[^\\.]*]}" })
    public String forward() {
        return "forward:/index.html";
    }
}