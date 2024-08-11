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
		if(client.getId() != null && client.getId() >0){
			em.merge(client);
		}else{
			em.persist(client);
		}
	}

	@Override
	public Client findOneClient(Long id) {
		return em.find(Client.class, id);
	}

	@Override
	@Transactional
	public void deleteClient(Long id) {
		Client client = findOneClient(id);
		em.remove(client);
	}

}
