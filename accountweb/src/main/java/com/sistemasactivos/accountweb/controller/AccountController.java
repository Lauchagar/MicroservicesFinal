package com.sistemasactivos.accountweb.controller;

import com.sistemasactivos.accountweb.service.AccountService;
import com.sistemasactivos.accountweb.model.Account;
import com.sistemasactivos.accountweb.model.AuthCredentials;
import com.sistemasactivos.accountweb.model.PageResponse;
import com.sistemasactivos.accountweb.model.User;
import com.sistemasactivos.accountweb.model.User.Role;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path="/accounts")
public class AccountController {
    @Autowired
    AccountService accountService;
    
    private String token;
    
    @GetMapping("/main")
    public String main() {
        return "main";
    }
    
    @GetMapping("/login")
    public String showLogin(Model model) {
        model.addAttribute("login", new AuthCredentials());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute AuthCredentials authCredentials) {
        this.token = accountService.login(authCredentials).block();
        return "redirect:/accounts/main";
    }
    
    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, HttpServletRequest request) {
        String[] rolesSeleccionados = request.getParameterValues("roles");

        if (rolesSeleccionados != null) {
            Set<Role> roles = new HashSet<>();
            for (String rol : rolesSeleccionados) {
                roles.add(new Role(rol));
            }
            user.setRoles(roles);
        }

        accountService.register(user, token).block();
        return "redirect:/accounts/main";
    }
    
    @GetMapping("")
    public String findAll(Model model) throws Exception{       
        List<Account> accounts = accountService.findAll()
           .collectList()
           .block();
        model.addAttribute("accounts", accounts);
        return "accounts";
    }
    
    @GetMapping("/paged")
    public String showPaged() {
        return "pageable";
    }
    
    @GetMapping("/pagedResult")
    public String findAllPaged(Model model, @RequestParam Integer page,
                                                @RequestParam Integer size) {
        PageResponse paged = accountService.findAllPaged(page, size)
                .block();
        model.addAttribute("paged",paged);
        return "paged";
    }
        
    @GetMapping("/{id}")
    public String findById(Model model, @PathVariable("id") Integer id) throws Exception{
        Account account = accountService.findById(id)
            .block();
        model.addAttribute("account",account);
        return "account";
    }
    
    @GetMapping("/save")
    public String showSave(Model model) {
        model.addAttribute("account", new Account());
        return "save_account";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Account account) {
        accountService.save(account, token).block();
        return "redirect:/accounts";
    }
    
    @GetMapping("/update/{id}")
    public String showUpdate(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("account", new Account());
        model.addAttribute("id", id);
        return "update_account";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Integer id, @ModelAttribute Account account) {
        accountService.update(id, account, token).block(); 
        return "redirect:/accounts"; 
    }    
    
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) throws Exception{
        accountService.delete(id, token).block();
        return "redirect:/accounts";
    }
    
    @GetMapping("/search")
    public String showSearch() {
        return "search";
    }
    
    @GetMapping("/searchResult")
    public String findAllSearch(Model model, @RequestParam String filter) {
        List<Account> search = accountService.findAllSearch(filter)
                .collectList()
                .block();
        model.addAttribute("accounts",search);
        return "accounts";
    }
    
    @GetMapping("/searchPaged")
    public String showSearchPaged() {
        return "search_paged";
    }
    
    @GetMapping("/searchPagedResult")
    public String findAllSearchPaged(Model model,@RequestParam String filter,
                                        @RequestParam Integer page,
                                            @RequestParam Integer size) {
        PageResponse searchPaged = accountService.findAllSearchPaged(filter, page, size)
                .block();
        model.addAttribute("paged",searchPaged);
        return "paged";
    }
}
