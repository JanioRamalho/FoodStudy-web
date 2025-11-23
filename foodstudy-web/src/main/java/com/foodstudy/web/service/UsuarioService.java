package com.foodstudy.web.service;

import com.foodstudy.web.model.Pedido;
import com.foodstudy.web.model.Usuario;
import com.foodstudy.web.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // ========= MÉTODOS USADOS PELO UsuarioController =========

    // 1. Listar todos os usuários
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    // 2. Buscar usuário por ID
    public Usuario buscarPorId(Long id) {
        Optional<Usuario> opt = usuarioRepository.findById(id);
        return opt.orElse(null);
    }

    // 3. Cadastrar novo usuário (API)
    public Usuario cadastrar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // 4. Adicionar saldo ao FoodCash do usuário
    public void adicionarFoodCash(Long usuarioId, float valor) {
        Usuario usuario = buscarPorId(usuarioId);
        if (usuario != null) {
            usuario.adicionarFoodCash(valor); // usa o método da própria entidade
            usuarioRepository.save(usuario);
        }
    }

    // 5. Visualizar pedidos de um usuário
    public List<Pedido> visualizarPedidos(Long usuarioId) {
        Usuario usuario = buscarPorId(usuarioId);
        if (usuario != null) {
            return usuario.getPedidos();
        }
        return List.of();
    }

    // ========= MÉTODOS USADOS PELO AuthController (LOGIN / CADASTRO TELA INDEX) =========

    // usado na tela de cadastro (/register)
    public Usuario registrar(Usuario usuario) {
        // aqui poderia validar se o email já existe, se quiser
        return usuarioRepository.save(usuario);
    }

    // usado no login (/login)
    public Usuario autenticar(String email, String senha) {
        Usuario user = usuarioRepository.findByEmail(email);
        if (user != null && user.getSenha() != null && user.getSenha().equals(senha)) {
            return user;
        }
        return null; // login inválido
    }
}
