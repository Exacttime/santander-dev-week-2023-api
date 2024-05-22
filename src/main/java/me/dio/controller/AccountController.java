package me.dio.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import me.dio.domain.model.Account;
import me.dio.service.AccountService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api")
@Tag(name = "Account Controller", description = "RESTful API for managing Accounts.")
public record AccountController(AccountService accountService) {
    @PostMapping("/conta/{number}/transfer/{destinationNumber}")
    public void transferMoney(@PathVariable String number, @PathVariable String destinationNumber, @RequestBody double amount) {
        Account sourceAccount = accountService.findById(number);
        Account destinationAccount = accountService.findById(destinationNumber);
        sourceAccount.transfer(amount, destinationAccount);
        accountService.update(number,sourceAccount);
        accountService.update(destinationNumber,destinationAccount);
    }
}
