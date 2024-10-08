	package com.springboot.app.models.entity;

	import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

	@Entity()
	@Table(name = "clients")
	public class Client implements Serializable {

		/**
		 * Es recomendado para convertir el objeto en una secuencia de bytes
		 */
		private static final long serialVersionUID = 1L;

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;

		@NotEmpty(message = "{NotEmpty.client.name}")
		private String name;

		@NotEmpty
		@Column(name="lastname")
		private String lastName;

		@NotEmpty
		@Email
		private String email;

		@NotNull
		@Column(name = "create_at")
		@Temporal(TemporalType.DATE)
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		private Date createAt;

		private String photo;

		@OneToMany(mappedBy = "client" , fetch = FetchType.LAZY, cascade=CascadeType.ALL)
		private List<Invoice> invoices;

		public Client() {
			invoices = new ArrayList<Invoice>();
		}



		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public Date getCreateAt() {
			return createAt;
		}

		public void setCreateAt(Date createAt) {
			this.createAt = createAt;
		}

		

		public String getPhoto() {
			return photo;
		}



		public void setPhoto(String photo) {
			this.photo = photo;
		}



		public List<Invoice> getInvoices() {
			return this.invoices;
		}

		public void setInvoices(List<Invoice> invoices) {
			this.invoices = invoices;
		}



		@Override
		public String toString() {
			return "Client: " + name + " " + lastName + " ";
		}
		

	}
