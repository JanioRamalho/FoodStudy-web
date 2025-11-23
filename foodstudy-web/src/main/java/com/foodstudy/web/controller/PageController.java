// src/main/java/com/foodstudy/web/controller/PageController.java
package com.foodstudy.web.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    // ✅ PRIMEIRA PÁGINA: INDEX (PÚBLICA)
    // Quando acessar http://localhost:9090/ vai cair aqui
    @GetMapping("/")
    public String index() {
        return "index";   // templates/index.html
    }

    // ✅ DASHBOARD (PROTEGIDO)
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session) {

        // Se NÃO estiver logado, redireciona para /login
        if (session.getAttribute("usuarioLogado") == null) {
            return "redirect:/login";
        }

        // Se estiver logado, mostra o dashboard
        return "dashboard";   // templates/dashboard.html
    }
}
