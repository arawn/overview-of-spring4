package jco.conference.oxquiz.model;

import java.security.Principal;

public class Player implements Principal {

    private final String name;
    private Boolean lastAnswer;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public Boolean getLastAnswer() { return lastAnswer; }

    public void answerIsYes() { this.lastAnswer = true; }
    public void answerIsNo() { this.lastAnswer = false; }
    public void answerIsNothing() { this.lastAnswer = null; }

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
