package com.springboot.app.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.springboot.app.models.dao.IClientDao;
import com.springboot.app.models.entity.Client;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@SessionAttributes("client")
public class ClientController {

	@Autowired
	@Qualifier("clientDaoJpa")
	private IClientDao clientDao;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String listClients(Model model) {
		model.addAttribute("title", "Client list");
		model.addAttribute("clients", clientDao.findAll());
		return "listClients";
	}

	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public String create(Map<String, Object> model) {

		Client client = new Client();
		model.put("client", client);
		model.put("title", "Customer form");
		return "form";
	}

	@RequestMapping(value="/form/{id}", method=RequestMethod.GET)
	public String editar(@PathVariable(value="id") Long id, Map<String, Object> model) {
		Client client = null;
		if(id >0){
			client = clientDao.findOneClient(id);
		}else{
			return "redirect:/list";
		}
		model.put("client", client);
		model.put("titulo", "Edit client");
		return "form";
	}
	


	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String save(@Valid Client client, BindingResult result, Model model, SessionStatus status) {
		if (result.hasErrors()) {
			model.addAttribute("title", "Form client");
			return "form";
		}
		clientDao.save(client);
		status.setComplete();
		return "redirect:/list";
	}

	 @RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	 public String delete(@PathVariable("id") Long id) {
		if(id > 0){
			clientDao.deleteClient(id);
		}
		return "redirect:/list";

	}
}