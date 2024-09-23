package com.springboot.app.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboot.app.models.entity.Client;
import com.springboot.app.models.entity.Invoice;
import com.springboot.app.models.service.IClientService;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/invoice")
@SessionAttributes("invoice")
public class InvoiceController {
	
	@Autowired
	private IClientService clientServiceImpl; 
	
	@GetMapping("/form/{clientId}")
	public String create(@PathVariable(value="clientId") Long clientId,
						 Map<String, Object> model, 
						 RedirectAttributes flash) {
	Client client = clientServiceImpl.findOneClient(clientId);
	if(client == null) {
		flash.addFlashAttribute("error", "Client is not exists in db");
		return "redirect:/listClients";
	}
	Invoice invoice = new Invoice();
	invoice.setClient(client);
	
	model.put("invoice", invoice);
	model.put("title", "Create invoice");
		return "invoice/form";
	}

	@GetMapping("/search-product/{term}")
	public String getMethodName(@RequestParam String param) {
		return new String();
	}
	

}
