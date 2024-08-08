package com.springboot.app.models.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.app.models.entity.Client;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository("clientDaoJpa")
public class ClientDaoImpl implements IClientDao {
	
	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	@Override
	public List<Client> findAll() {
		return em.createQuery("from Client").getResultList();
	}

	@Override
	@Transactional
	public void save(Client client) {
		em.persist(client);
	}
	
	

}
