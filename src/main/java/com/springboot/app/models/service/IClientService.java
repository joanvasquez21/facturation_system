package com.springboot.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.springboot.app.models.entity.Client;
import com.springboot.app.models.entity.Invoice;
import com.springboot.app.models.entity.Product;

public interface IClientService {

    public List<Client> findAll();

    public Page<Client> findAll(Pageable pageable);

    public void save(Client client);

    public Client findOneClient(Long id);

    public void deleteClient(Long id);

    public List<Product> findByName(String term);

    public void saveInvoice(Invoice invoice);

    public Product findProductById(Long id);
    
}