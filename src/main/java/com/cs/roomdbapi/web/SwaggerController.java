package com.cs.roomdbapi.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SwaggerController {

    @RequestMapping("/swagger")
    public String home() {
        return "redirect:/swagger-ui.html";
    }

}
