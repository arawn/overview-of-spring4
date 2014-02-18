package jco.conference.oxquiz.handler;

import jco.conference.oxquiz.model.Question;
import jco.conference.oxquiz.model.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collections;

@Controller
public class QuestionController {

    private SimpMessageSendingOperations messagingTemplate;
    private QuestionRepository questionRepository;

    @Autowired
    public QuestionController(SimpMessageSendingOperations messagingTemplate, QuestionRepository questionRepository) {
        this.messagingTemplate = messagingTemplate;
        this.questionRepository = questionRepository;
    }

    @SubscribeMapping("/question/reception")
    public Question reception() {
        return questionRepository.findOneByLastCreatedDateTime();
    }

    @RequestMapping(value = "/question/send", method = RequestMethod.POST)
    public ResponseEntity next(Question question) {
        questionRepository.save(question);

        messagingTemplate.convertAndSend("/quiz/question/reception", question);

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/question/close", method = RequestMethod.POST)
    public ResponseEntity close() {
        messagingTemplate.convertAndSend("/quiz/question/close", Collections.emptyMap());

        return new ResponseEntity(HttpStatus.OK);
    }

}
