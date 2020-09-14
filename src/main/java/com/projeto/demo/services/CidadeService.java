package com.projeto.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.demo.domain.Cidade;
import com.projeto.demo.repositories.CidadeRepository;

@Service
public class CidadeService {
	
	@Autowired
	private CidadeRepository repository;
	
	public List<Cidade> findByEstado(Integer estadoId){
		return repository.findCidades(estadoId);
		
	}
}
