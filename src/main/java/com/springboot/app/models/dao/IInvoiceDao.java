package com.springboot.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.springboot.app.models.entity.Invoice;

public interface  IInvoiceDao extends CrudRepository<Invoice, Long>{
    
}
