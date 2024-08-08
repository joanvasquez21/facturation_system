package com.springboot.app.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.springboot.app.models.dao.IClientDao;
import com.springboot.app.models.entity.Client;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ClientController {

	@Autowired
	@Qualifier("clientDaoJpa")
	private IClientDao clientDao;
		
	@RequestMapping(value="/list", method= RequestMethod.GET)
	public String listClients(Model model) {
		model.addAttribute("title", "Client list");
		model.addAttribute("clients", clientDao.findAll());
		return "listClients";
	}

	@RequestMapping(value="/form", method=RequestMethod.GET)
	public String create(Map<String, Object> model ) {
		
		Client client = new Client();
		model.put("client", client);
		model.put("title", "Customer form");
		return "form";
	}

	@RequestMapping(value="/form", method=RequestMethod.POST)
	public String save(@Valid Client client, BindingResult result, Model model) {
		if(result.hasErrors()) {
			model.addAttribute("titulo", "Form client");
			return "form";
		}
		clientDao.save(client);
		return "redirect:/list";
	}


	
	
	
	
	
}
