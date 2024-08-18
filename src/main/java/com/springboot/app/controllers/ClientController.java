package com.springboot.app.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import com.springboot.app.util.paginator.PageRender;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@SessionAttributes("client")
public class ClientController {

	@Autowired
	private IClientService clientService;

	@GetMapping(value="/see/{id}")
	public String see(@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		Client client = clientService.findOneClient(id);
		if(client==null){
			flash.addFlashAttribute("error", "The client does not exist");
			return "redirect:/listClients";
		}
		model.put("client", client);
		model.put("title", "Client detail: " + client.getName());

		return "see";
	}
	

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String listClients(@RequestParam(name="page", defaultValue= "0") int page, Model model) {
		Pageable pageRequest = PageRequest.of(page, 4);
		Page<Client> clients = clientService.findAll(pageRequest);
	
		PageRender<Client> pageRender = new PageRender<>("/list", clients);
		model.addAttribute("title", "Client list");
		model.addAttribute("clients", clients);
		model.addAttribute("page", pageRender);
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
	public String save(@Valid Client client, 
						BindingResult result, 
						Model model,
					   @RequestParam("file") MultipartFile photo, 
					   RedirectAttributes flash,
					   SessionStatus status) {
		if (result.hasErrors()) {
			model.addAttribute("title", "Form client");
			return "form";
		}
		if(!photo.isEmpty()){
			String rootPath = "C://Temp//uploads";
			try {
				byte[] bytes = photo.getBytes();
				Path routeComplete = Paths.get(rootPath + "//" + photo.getOriginalFilename());
				Files.write(routeComplete, bytes);
				flash.addFlashAttribute("info", "You have successfully uploaded the photo");

				client.setPhoto(photo.getOriginalFilename());

			} catch (IOException e) {
				e.printStackTrace();
			}
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