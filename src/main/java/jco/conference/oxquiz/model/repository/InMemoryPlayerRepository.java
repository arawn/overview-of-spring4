package jco.conference.oxquiz.model.repository;

import jco.conference.oxquiz.model.Player;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Repository
public class InMemoryPlayerRepository implements PlayerRepository {

    private Set<Player> entries = Collections.synchronizedSet(new HashSet<>());

    @Override
    public void save(final Player player) {
        entries.add(player);
    }

    @Override
    public void remove(Player player) {
        entries.remove(player);
    }

    @Override
    public Iterable<Player> findAll() {
        return Collections.unmodifiableSet(entries);
    }

}
