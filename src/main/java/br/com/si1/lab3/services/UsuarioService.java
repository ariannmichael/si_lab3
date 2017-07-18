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
	
	public Usuario adicionar(Usuario usuario) {
		if(existeUsuario(usuario)) return null;
		return usuarioRepository.save(usuario);
	}
	
	private boolean existeUsuario(Usuario usuario) {
		List<Usuario> usuarios = usuarioRepository.findAll();
		for(Usuario user : usuarios) {
			if(user.getEmail().equals(usuario.getEmail())) {
				return true;
			}
		}
		
		return false;
	}
	
	public Usuario logar(Usuario usuario) {
		return logar(usuario.getEmail(), usuario.getSenha());
	}

	private Usuario logar(String email, String senha) {
		List<Usuario> usuarios = usuarioRepository.findAll();
		for(Usuario user : usuarios) {
			if(user.getEmail().equals(email)) {
				if(user.getSenha().equals(senha)) {
					return user;
				}
			}
		}
		return null;
	}
	
	
}
