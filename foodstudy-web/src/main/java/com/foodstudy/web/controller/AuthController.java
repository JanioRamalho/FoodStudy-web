package com.foodstudy.web.controller;

import com.foodstudy.web.model.Usuario;
import com.foodstudy.web.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UsuarioRepository usuarioRepository;

    public AuthController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // =============== LOGIN ===============

    // Tela de login
    @GetMapping("/login")
    public String telaLogin(Model model, HttpSession session) {

        // Se já estiver logado e tentar ir pro /login, manda pro dashboard
        if (session.getAttribute("usuarioLogado") != null) {
            return "redirect:/dashboard";
        }

        model.addAttribute("usuario", new Usuario());
        return "login";
    }

    // Processar login
    @PostMapping("/login")
    public String autenticar(@ModelAttribute("usuario") Usuario usuarioForm,
                             Model model,
                             HttpSession session) {

        Usuario usuario = usuarioRepository.findByEmail(usuarioForm.getEmail());

        if (usuario == null || !usuario.getSenha().equals(usuarioForm.getSenha())) {
            model.addAttribute("erro", "E-mail ou senha inválidos.");
            model.addAttribute("usuario", usuarioForm);
            return "login";
        }

        // Guarda o usuário na sessão
        session.setAttribute("usuarioLogado", usuario);

        // Vai para o dashboard
        return "redirect:/dashboard";
    }

    // =============== CADASTRO ===============

    // Tela de cadastro
    @GetMapping("/cadastro")
    public String telaCadastro(Model model, HttpSession session) {

        // Se já estiver logado, colocar cadastro não faz sentido → manda pro dashboard
        if (session.getAttribute("usuarioLogado") != null) {
            return "redirect:/dashboard";
        }

        model.addAttribute("usuario", new Usuario());
        return "cadastro";
    }

    // Processar cadastro
    @PostMapping("/cadastro")
    public String cadastrar(@ModelAttribute("usuario") Usuario usuarioForm,
                            Model model) {

        Usuario existente = usuarioRepository.findByEmail(usuarioForm.getEmail());
        if (existente != null) {
            model.addAttribute("erro", "Já existe um usuário cadastrado com esse e-mail.");
            model.addAttribute("usuario", usuarioForm);
            return "cadastro";
        }

        usuarioRepository.save(usuarioForm);

        // Depois de cadastrar, manda pro login
        return "redirect:/login";
    }

    // =============== LOGOUT ===============

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // limpa a sessão
        return "redirect:/";  // volta pra index
    }
}
