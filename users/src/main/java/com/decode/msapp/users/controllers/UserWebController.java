package com.decode.msapp.users.controllers;

import com.decode.msapp.users.DTO.UserRegisterDTO;
import com.decode.msapp.users.models.User;
import com.decode.msapp.users.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usersweb")
@AllArgsConstructor
@Slf4j
public class UserWebController {

    private final UserService peopleService;

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("users", peopleService.findAll());
        return "users/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleService.findById(id));
        return "users/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") User user) {
        return "users/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid UserRegisterDTO personRegisterDTO,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "people/new";

        peopleService.save(personRegisterDTO);
        return "redirect:/users";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", peopleService.findById(id));
        return "users/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid UserRegisterDTO personDTO, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "users/edit";

        peopleService.update(id, personDTO);
        return "redirect:/users";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        peopleService.delete(id);
        return "redirect:/users";
    }
}
