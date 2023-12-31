package ru.myteam.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.myteam.models.Person;

import java.util.List;


@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM people", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id)  {
        return jdbcTemplate.query("SELECT * FROM people WHERE id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO people (name, cell, firstdice, seconddice, result) VALUES (?, ?, ?, ?, ?)",
                person.getName(), person.getCell(), person.getFirstDice(), person.getSecondDice(), person.getResult());
    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE people SET name = ?, cell = ?, firstdice = ?, seconddice = ?, result = ? WHERE id = ?",
                updatedPerson.getName(), updatedPerson.getCell(), updatedPerson.getFirstDice(),
                updatedPerson.getSecondDice(), updatedPerson.getResult(), id);
    }

    public Person getPersonById(int id) {
        String sql = "SELECT * FROM people WHERE id = ?";
        Object[] params = {id};
        RowMapper<Person> rowMapper = new BeanPropertyRowMapper<>(Person.class);
        List<Person> people = jdbcTemplate.query(sql, params, rowMapper);
        if (people.isEmpty()) {
            return null;
        } else {
            return people.get(0);
        }
    }


    public int checkAfterRoll(int id) {
        // Получение текущей ячейки игрока
        int currentCell = jdbcTemplate.queryForObject("SELECT cell FROM people WHERE id = ?", Integer.class, id);
        return currentCell;
    }

    public void updateAfterRoll(int id, Person updatePersonAfterRoll){
        // Получение текущей ячейки игрока
        int currentCell = jdbcTemplate.queryForObject("SELECT cell FROM people WHERE id = ?", Integer.class, id);

        // Выполнение броска кубиков и обновление значений
        updatePersonAfterRoll.setRollTheDice();

        // Увеличение значения ячейки на результат броска
        int newCell = currentCell + updatePersonAfterRoll.getResult();
        updatePersonAfterRoll.setCell(newCell);

        jdbcTemplate.update("UPDATE people SET cell = ?, firstdice = ?, seconddice = ?, result = ? WHERE id = ?",
                updatePersonAfterRoll.getCell(), updatePersonAfterRoll.getFirstDice(),
                updatePersonAfterRoll.getSecondDice(), updatePersonAfterRoll.getResult(), id);
    }

    public void setEndGame(int id) {
        jdbcTemplate.update("UPDATE people SET cell = 48 WHERE id = ?", id);
    }


    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM people WHERE id = ?", id);
    }

}
