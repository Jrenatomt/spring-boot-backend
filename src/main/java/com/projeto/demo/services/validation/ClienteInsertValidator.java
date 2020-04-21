package com.projeto.demo.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.projeto.demo.domain.Cliente;
import com.projeto.demo.domain.enums.TipoCliente;
import com.projeto.demo.dto.ClienteNewDTO;
import com.projeto.demo.repositories.ClienteRepository;
import com.projeto.demo.resources.exception.FieldMessage;
import com.projeto.demo.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>(); 
		
		if(objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj())){
			list.add(new FieldMessage("cpfOuCnpj", "CPF invalido"));
		}
		if(objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())){
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ invalido"));
		}
		
		Cliente cli = clienteRepository.findByEmail(objDto.getEmail());
		if(cli != null) {
			list.add(new FieldMessage("email", "Email Existente"));
		}
			
		
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getFieldMessage()).addPropertyNode(e.getFieldNome())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}