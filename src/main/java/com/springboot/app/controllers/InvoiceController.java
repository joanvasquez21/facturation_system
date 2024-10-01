package com.springboot.app.controllers;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboot.app.models.entity.Client;
import com.springboot.app.models.entity.Invoice;
import com.springboot.app.models.entity.ItemInvoice;
import com.springboot.app.models.entity.Product;
import com.springboot.app.models.service.IClientService;


@Controller
@RequestMapping("/invoice")
@SessionAttributes("invoice")
public class InvoiceController {

	@Autowired
	private IClientService clientServiceImpl; 

	private final Logger log = LoggerFactory.getLogger(InvoiceController.class);
	
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

	@GetMapping(value= "/upload-products", produces={"application/json"})
	public @ResponseBody List<Product> uploadProducts(@RequestParam String term) {
		return clientServiceImpl.findByName(term);
	}

	@PostMapping("/form")
	public String save(Invoice invoice, 
					@RequestParam(name="item_id[]", required=false) Long[] itemId, 
					@RequestParam(name="quantity[]", required=false) Integer[] quantity,
					RedirectAttributes flash,
					SessionStatus status){
		for(int i = 0; i<itemId.length; i++){
			Product productId = clientServiceImpl.findProductById(itemId[i]);
			ItemInvoice line = new ItemInvoice();
			line.setQuantity(quantity[i]);
			line.setProduct(productId);
			invoice.addItemInvoice(line);

			log.info("Id:" + itemId[i].toString() + "quantity:" + quantity[i]);
		}
		clientServiceImpl.saveInvoice(invoice);
		status.setComplete();
		flash.addFlashAttribute("success", "Invoice create successfully");
	
		return "redirect:/see/" + invoice.getClient().getId();
	}

	

}
