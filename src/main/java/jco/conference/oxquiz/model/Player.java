package jco.conference.oxquiz.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Player implements Principal {

    private final String name;
    private Boolean lastAnswer;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastAnswerDateTime;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate lastConnectionDate;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public Boolean getLastAnswer() { return lastAnswer; }
    public LocalDateTime getLastAnswerDateTime() {
        return lastAnswerDateTime;
    }
    public LocalDate getLastConnectionDate() {
        return lastConnectionDate;
    }

    public Player connect() {
        this.lastConnectionDate = LocalDate.now();

        return this;
    }

    public Player disconnect() {
        this.answerIsNothing();

        return this;
    }

    public void answerIsYes() {
        this.lastAnswer = true;
        this.lastAnswerDateTime = LocalDateTime.now();
    }
    public void answerIsNo() {
        this.lastAnswer = false;
        this.lastAnswerDateTime = LocalDateTime.now();
    }
    public void answerIsNothing() {
        this.lastAnswer = null;
        this.lastAnswerDateTime = null;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;

        Player player = (Player) o;

        if (!name.equals(player.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Player{");
        sb.append("name='").append(name).append('\'').append('}');
        return sb.toString();
    }

}
