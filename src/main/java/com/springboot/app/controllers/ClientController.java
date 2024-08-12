package com.springboot.app.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.springboot.app.models.entity.Client;
import com.springboot.app.models.service.IClientService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@SessionAttributes("client")
public class ClientController {

	@Autowired
	private IClientService clientService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String listClients(@RequestParam(name="page", defaultValue= "0") int page, Model model) {
		Pageable pageRequest = PageRequest.of(page, 4);
		Page<Client> clients = clientService.findAll(pageRequest);
	
		model.addAttribute("title", "Client list");
		model.addAttribute("clients", clients);
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
	public String edit(@PathVariable(value="id") Long id, Map<String, Object> model) {
		Client client = null;
		if(id >0){
			client = clientService.findOneClient(id);
		}else{
			return "redirect:/list";
		}
		model.put("client", client);
		model.put("title", "Edit client");
		return "form";
	}

	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String save(@Valid Client client, BindingResult result, Model model, SessionStatus status) {
		if (result.hasErrors()) {
			model.addAttribute("title", "Form client");
			return "form";
		}
		clientService.save(client);
		status.setComplete();
		return "redirect:/list";
	}

	 @RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	 public String delete(@PathVariable("id") Long id) {
		if(id > 0){
			clientService.deleteClient(id);
		}
		return "redirect:/list";

	}
}