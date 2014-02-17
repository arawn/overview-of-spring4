package jco.conference.example.web;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
public class JSR310Controller {

    @RequestMapping(value = "/jsr310", params = "localDate")
    public @ResponseBody String test(LocalDate localDate) {
        return "localDate: " + localDate;
    }

    @RequestMapping(value = "/jsr310", params = "localDateTime")
    public @ResponseBody String test(@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime localDateTime) {
        return "localDateTime: " + localDateTime;
    }

}
