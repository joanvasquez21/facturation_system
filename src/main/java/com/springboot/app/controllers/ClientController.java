package com.springboot.app.controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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

	private final static String UPLOADS_FOLDER = "uploads";

	private final Logger log = LoggerFactory.getLogger(ClientController.class);

	@GetMapping(value="/uploads/{filename:.+}")
	public ResponseEntity<Resource> seePhoto(@PathVariable String filename){
		Path pathPhoto = Paths.get(UPLOADS_FOLDER).resolve(filename).toAbsolutePath();
		log.info("pathPhoto: " + pathPhoto);
		Resource resource = null;
		try {
			 resource = new UrlResource(pathPhoto.toUri());
			if(!resource.exists() && !resource.isReadable()){
				throw new RuntimeException("Error: cannot load image " + pathPhoto.toString());
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
					.body(resource);
	}
	

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

			if(client.getId() != null && client.getId() > 0 && client.getPhoto() !=null && client.getPhoto().length() > 0){
				Path rootPath = Paths.get(UPLOADS_FOLDER).resolve(client.getPhoto()).toAbsolutePath();
				File filePath = rootPath.toFile();

				if(filePath.exists() && filePath.canRead()) {
					filePath.delete();
				}
			}

			String uniqueFilename = UUID.randomUUID().toString() + "_" + photo.getOriginalFilename();
			Path rootPath = Paths.get(UPLOADS_FOLDER).resolve(uniqueFilename);
			Path rootAbsolutePath = rootPath.toAbsolutePath();

			log.info("Rootpath: " + rootPath);
			log.info("rootabsolutepath: " + rootAbsolutePath);
			try {
				Files.copy(photo.getInputStream(), rootAbsolutePath);
				flash.addFlashAttribute("info", "You have successfully uploaded the photo");

				client.setPhoto(uniqueFilename);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		clientService.save(client);
		status.setComplete();
		return "redirect:/list";
	}

	 @RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	 public String delete(@PathVariable("id") Long id, RedirectAttributes flash) {
		if(id > 0){

			Client client = clientService.findOneClient(id);
			flash.addFlashAttribute("success", "Client successfully deleted");
			clientService.deleteClient(id);

			Path rootPath = Paths.get(UPLOADS_FOLDER).resolve(client.getPhoto()).toAbsolutePath();
			File filePath = rootPath.toFile();

			if(filePath.exists() && filePath.canRead()) {
				if(filePath.delete()){
					flash.addFlashAttribute("info", client.getPhoto() + "deleted successfully");
				}
			}
		}

		return "redirect:/list";

	}
}