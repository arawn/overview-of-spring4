package jco.conference.oxquiz.websocket;

import jco.conference.oxquiz.model.Player;
import jco.conference.oxquiz.model.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
public class PlayerController implements ApplicationListener<PlayerEvent> {

    private SimpMessageSendingOperations messagingTemplate;
    private PlayerRepository playerRepository;

    @Autowired
    public PlayerController(SimpMessageSendingOperations messagingTemplate, PlayerRepository playerRepository) {
        this.messagingTemplate = messagingTemplate;
        this.playerRepository = playerRepository;
    }

    @SubscribeMapping("/players")
    public Iterable<Player> players() {
        return playerRepository.findAll();
    }

    @Override
    public void onApplicationEvent(PlayerEvent event) {
        if(PlayerEvent.PlayerEventState.IN.equals(event.getState())) {
            playerRepository.save(event.getPlayer());
            messagingTemplate.convertAndSend("/quiz/players/in", event.getPlayer());
        } else {
            playerRepository.remove(event.getPlayer());
            messagingTemplate.convertAndSend("/quiz/players/out", event.getPlayer());
        }
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
