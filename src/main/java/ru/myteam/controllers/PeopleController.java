package ru.myteam.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.myteam.dao.PersonDAO;
import ru.myteam.models.Person;

import javax.validation.Valid;
import java.sql.SQLException;


@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO personDAO;

    @Autowired
    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model) throws SQLException {
        try {
            model.addAttribute("people", personDAO.index());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDAO.show(id));
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "people/new";

        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personDAO.show(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "people/edit";

        personDAO.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personDAO.delete(id);
        return "redirect:/people";
    }

    @PostMapping ("/{id}/roll")
    public String roll(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                       @PathVariable("id") int id, Model model) {
        if (bindingResult.hasErrors())
            return "people/show";
        personDAO.updateAfterRoll(id, person);

        // Проверка на окончание игры
        if (personDAO.checkAfterRoll(id) >= 48) {
            personDAO.setEndGame(id); // Установить номер ячейки 48 для окончания игры
            return "redirect:/people/endgame/" + id; // Перенаправление на страницу "Конец игры"
        }

        return "redirect:/people";
    }

    @GetMapping("/endgame/{id}")
    public String endGame(@PathVariable("id") int id, Model model) {
        Person person = personDAO.getPersonById(id);
        model.addAttribute("person", person);
        return "people/endgame";
    }
}
