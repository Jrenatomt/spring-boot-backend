package com.projeto.demo.services;

import org.springframework.mail.SimpleMailMessage;

import com.projeto.demo.domain.Cliente;
import com.projeto.demo.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);

	void sendEmail(SimpleMailMessage msg);
	
	void sendNewPasswordEmail(Cliente cliente, String newPass);

}
