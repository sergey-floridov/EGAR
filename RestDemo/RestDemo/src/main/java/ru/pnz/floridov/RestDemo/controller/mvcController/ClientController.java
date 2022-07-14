package ru.pnz.floridov.RestDemo.controller.mvcController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.pnz.floridov.RestDemo.model.Client;
import ru.pnz.floridov.RestDemo.service.ClientService;
import ru.pnz.floridov.RestDemo.util.ClientErrorResponse;
import ru.pnz.floridov.RestDemo.util.ClientNotFoundException;

import javax.validation.Valid;

@Controller
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;


    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }



    @GetMapping()
    public String index(Model model) {
        model.addAttribute("clients",clientService.findAll());
        return "clients/index";
    }



    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("client", clientService.findOne(id));
        model.addAttribute("credits", clientService.getCreditProductsByClientId(id));
        return "clients/show";
    }



    @GetMapping("/new")
    public String newClient(@ModelAttribute("client")Client client) {
        return "clients/new";
    }



    @PostMapping()
    public String create(@ModelAttribute("client") @Valid Client client,
                         BindingResult bindingResult) {
//        personValidator.validate(client, bindingResult);   //// доделать
        if (bindingResult.hasErrors())
            return "clients/new";
        clientService.save(client);
        return "redirect:/clients";
    }



    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("client", clientService.findOne(id));
        return "clients/edit";
    }



    @PatchMapping("/{id}")
    public String update(@ModelAttribute("client") @Valid Client client, BindingResult bindingResult,
                         @PathVariable("id") Long id) {
        if (bindingResult.hasErrors())
            return "clients/edit";

        clientService.update(id, client);
        return "redirect:/clients";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        clientService.delete(id);
        return "redirect:/clients";
    }

    @ExceptionHandler
    private ResponseEntity<ClientErrorResponse> HandleException (ClientNotFoundException e){
        ClientErrorResponse response = new ClientErrorResponse(
                "Client with this id wasn't found",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);  //
    }
}