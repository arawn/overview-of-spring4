package jco.conference.lambda;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = LambdasWithSpringTestConfig.class)
@Transactional
public class LambdasWithJdbcTemplateTest {

    @Autowired JdbcTemplate jdbcTemplate;

    @Test
    public void lambdasWithJdbcTemplate() {
        List<Person> persons = jdbcTemplate.query(
            "SELECT name, age FROM person where age = ?",
            ps -> ps.setInt(1, 35),
            (rs, rowNum) -> new Person(rs.getString(1), rs.getInt(2))
        );
        persons.forEach(System.out::println);

        jdbcTemplate.queryForObject(
            "SELECT name, age FROM person where name = ?", new Object[]{ "arawn" },
            (rs, rowNum) -> new Person(rs.getString("name"), rs.getInt("age"))
        );

        findAll();
    }


    private List<Person> findAll() {
        return jdbcTemplate.query("SELECT name, age FROM person", this::mapPerson);
    }

    private Person mapPerson(ResultSet rs, int rowNum) throws SQLException {
        return new Person(rs.getString("name"), rs.getInt("age"));
    }

}
