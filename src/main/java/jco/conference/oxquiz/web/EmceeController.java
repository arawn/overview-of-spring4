package jco.conference.oxquiz.web;

import jco.conference.oxquiz.model.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/emcee")
public class EmceeController {

    private SimpMessageSendingOperations messagingTemplate;
    private PlayerRepository playerRepository;

    @Autowired
    public EmceeController(SimpMessageSendingOperations messagingTemplate, PlayerRepository playerRepository) {
        this.messagingTemplate = messagingTemplate;
        this.playerRepository = playerRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String emcee(Model model) {
        model.addAttribute("players", playerRepository.findAll());

       return "/emcee";
    }

    @RequestMapping(value = "/question/send", method = RequestMethod.POST)
    public ResponseEntity nextQuestion(String question) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("question", question);

        messagingTemplate.convertAndSend("/quiz/question/reception", payload);

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/question/close", method = RequestMethod.POST)
    public ResponseEntity closeQuestion() {
        messagingTemplate.convertAndSend("/quiz/question/close", Collections.emptyMap());

        return new ResponseEntity(HttpStatus.OK);
    }

}
