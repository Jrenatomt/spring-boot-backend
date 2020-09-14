package com.projeto.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.demo.domain.Estado;
import com.projeto.demo.repositories.EstadoRepository;

@Service
public class EstadoService {
	
	@Autowired
	private EstadoRepository repository;
	
	
	public List<Estado> findAll() {
	return repository.findAllByOrderByNome();
	}
}
