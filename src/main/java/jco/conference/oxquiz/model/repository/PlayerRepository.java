package jco.conference.oxquiz.model.repository;

import jco.conference.oxquiz.model.Player;

public interface PlayerRepository {

    void save(Player player);

    void remove(Player player);

    Iterable<Player> findAll();

}
