package com.springboot.app.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboot.app.models.entity.Client;
import com.springboot.app.models.service.IClientService;
import com.springboot.app.models.service.UploadFileServiceImpl;
import com.springboot.app.util.paginator.PageRender;

import jakarta.validation.Valid;

@Controller
@SessionAttributes("client")
public class ClientController {

	@Autowired
	private IClientService clientService;

	@Autowired
	private UploadFileServiceImpl uploadFileServiceImpl;

	@GetMapping(value = "/uploads/{filename:.+}")
	public ResponseEntity<Resource> seePhoto(@PathVariable String filename) {

		Resource resource = null;
		try {
			resource = uploadFileServiceImpl.load(filename);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

	@GetMapping(value = "/see/{id}")
	public String see(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		Client client = clientService.findOneClient(id);
		if (client == null) {
			flash.addFlashAttribute("error", "The client does not exist");
			return "redirect:/listClients";
		}
		
		model.put("client", client);
		model.put("title", "Client detail: " + client.getName());

		return "see";
	}

	@RequestMapping(value = "/listClients", method = RequestMethod.GET)
	public String listClients(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		Pageable pageRequest = PageRequest.of(page, 4);
		Page<Client> clients = clientService.findAll(pageRequest);

		PageRender<Client> pageRender = new PageRender<>("/listClients", clients);
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

	@RequestMapping(value = "/form/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		Client client = null;
		if (id > 0) {
			client = clientService.findOneClient(id);
			if(client == null){
				flash.addAttribute("error", "ID is not exists in db");
				return "redirect:/listClient";
			}
		} else {
			flash.addAttribute("error", "ID is not  valid");
			return "redirect:/listClients";
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

		if (!photo.isEmpty()) {
			if (client.getId() != null
					&& client.getId() > 0
					&& client.getPhoto() != null
					&& client.getPhoto().length() > 0) {
				uploadFileServiceImpl.delete(client.getPhoto());
			}
			String uniqueFileName = null;
			try {
				uniqueFileName = uploadFileServiceImpl.copy(photo);
			} catch (IOException e) {
				e.printStackTrace();
			}

			flash.addFlashAttribute("info", "You have successfully uploaded the photo");
			client.setPhoto(uniqueFileName);
		}

		String messageFlash = (client.getId() != null) ? "The client has been edited successfully" : "The client has been created successfully";

		clientService.save(client);
		status.setComplete();
		flash.addFlashAttribute("success", messageFlash);
		return "redirect:/listClients";

	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable("id") Long id, RedirectAttributes flash) {
		if (id > 0) {
			Client client = clientService.findOneClient(id);

			clientService.deleteClient(id);
			flash.addFlashAttribute("success", "Client successfully deleted");

			if (uploadFileServiceImpl.delete(client.getPhoto())) {
				flash.addFlashAttribute("info", "Photo " + client.getPhoto() + "deleted successfully");
			}
		}

		return "redirect:/listClients";
	}
}