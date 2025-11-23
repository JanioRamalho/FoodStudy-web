package com.foodstudy.web.controller;

import com.foodstudy.web.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProdutoController {

    @Autowired
    private ProdutoRepository repo;

    @GetMapping("/produtos-lista")   // <<< ALTERADO PARA ACABAR COM ERRO
    public String listar(Model model) {
        model.addAttribute("produtos", repo.findAll());
        return "produtos";  // produtos.html
    }
}
