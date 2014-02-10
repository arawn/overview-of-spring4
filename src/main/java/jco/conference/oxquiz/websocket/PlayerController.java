package jco.conference.oxquiz.websocket;

import jco.conference.oxquiz.model.Player;
import jco.conference.oxquiz.model.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
public class PlayerController {

    private SimpMessageSendingOperations messagingTemplate;
    private Repository<Player> playerRepository;

    @Autowired
    public PlayerController(SimpMessageSendingOperations messagingTemplate, Repository<Player> playerRepository) {
        this.messagingTemplate = messagingTemplate;
        this.playerRepository = playerRepository;
    }

    @SubscribeMapping("/players")
    public Iterable<Player> players() {
        return playerRepository.findAll();
    }

    @MessageMapping("/answer")
    public void answer(AnswerMessage message, Player player) {
        if(message.isAnswer()) {
            player.answerIsYes();
        } else  {
            player.answerIsNo();
        }

        messagingTemplate.convertAndSend("/quiz/players/answer", player);
    }

    static class AnswerMessage {

        private boolean answer;

        public boolean isAnswer() { return answer; }
        public void setAnswer(boolean answer) { this.answer = answer; }

    }

}
