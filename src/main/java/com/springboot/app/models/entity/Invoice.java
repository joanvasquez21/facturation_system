package com.springboot.app.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "invoices")
public class Invoice implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private String observation; 
    
    @Temporal(TemporalType.DATE)
    @Column(name="create_at")
    private Date createAt;

    @ManyToOne(fetch=FetchType.LAZY)
    private Client client;
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "invoice_id")
    private List<ItemInvoice> items;
    
    public Invoice(){
        this.items = new ArrayList<ItemInvoice>();
    }
    
    @PrePersist
    public void prePersist(){
        createAt  = new Date();
    }
    
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getObservation() {
        return this.observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Date getCreateAt() {
        return this.createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<ItemInvoice> getItems() {
        return this.items;
    }

    public void setItems(List<ItemInvoice> items) {
        this.items = items;
    }

    public void addItemInvoice(ItemInvoice item) {
        this.items.add(item);
    }

    public Double getTotal() {
        Double total = 0.0;

        System.out.println("========== INICIANDO CÁLCULO DEL TOTAL ==========");
        System.out.println("Factura ID: " + this.id);
    
        if (this.items == null) {
            System.out.println("Error: La lista de items es null");
            return total;
        }

        System.out.println("Número de items en la factura: " + items.size());
    
        for (ItemInvoice item : items) {
            System.out.println("=========== PROCESANDO ITEM ===========");
            System.out.println("Item ID: " + item.getId());
            System.out.println("Cantidad: " + item.getQuantity());
        
            if (item.getProduct() == null) {
                System.out.println("Error: Producto no encontrado para el item con ID: " + item.getId());
            } else {
                System.out.println("Producto encontrado: " + item.getProduct().getName() + ", Precio: " + item.getProduct().getPrice());
            }

            if (item.getQuantity() == null) {
                System.out.println("Error: La cantidad es null para el item con ID: " + item.getId());
            } else {
                System.out.println("Calculando importe del item con cantidad: " + item.getQuantity());
                total += item.calculateImport();
            }
        }

        System.out.println("Total calculado para la factura: " + total);
        return total;
    }


}
