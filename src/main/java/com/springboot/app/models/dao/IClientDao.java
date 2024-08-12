package com.springboot.app.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.app.models.entity.Client; 

public interface IClientDao extends JpaRepository<Client, Long> { 

}
