package ru.pnz.floridov.RestDemo.controller.restController;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pnz.floridov.RestDemo.model.Client;
import ru.pnz.floridov.RestDemo.service.ClientService;
import ru.pnz.floridov.RestDemo.util.ClientErrorResponse;
import ru.pnz.floridov.RestDemo.util.ClientNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientRestController {

    private final ClientService clientService;

    @Autowired
    public ClientRestController(ClientService clientService) {
        this.clientService = clientService;
    }


    @GetMapping()
    public List<Client> getClients() {
        return clientService.findAll(); // Jackson конвертирует эти объекты в JSON
    }



    @GetMapping("/{id}")
    public Client getClient(@PathVariable("id") Long id) {
        return clientService.findOne(id); // Jackson конвертирует в JSON
    }
}
