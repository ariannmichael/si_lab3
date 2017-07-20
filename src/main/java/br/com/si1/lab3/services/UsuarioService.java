package br.com.si1.lab3.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.si1.lab3.entities.Usuario;
import br.com.si1.lab3.repositories.UsuarioRepository;

@Service
public class UsuarioService {
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Usuario cadastrar(Usuario usuario) {
		List<Usuario> usuarios = usuarioRepository.findAll();
		for (Usuario usuarioB : usuarios) {
			if (usuarioB.getEmail().equals(usuario.getEmail())) {
				return null;
			}
		}
		return usuarioRepository.save(usuario);
	}
	
	public Usuario logar(Usuario usuario) {
		return logar(usuario.getEmail(), usuario.getSenha());
	}
	
	public Usuario logar(String email, String senha) {
		List<Usuario> usuarios = usuarioRepository.findAll();
		for (Usuario usuario : usuarios) {
			if (usuario.getEmail().equals(email)) {
				if(usuario.getSenha().equals(senha)) {
					return usuario;
				}
			}
		}
		return null;
	}

}
