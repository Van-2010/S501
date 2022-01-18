package com.example.demo.entity;


import java.io.Serializable;
import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="quadres")
public class Quadres implements Serializable{
	
	@Id
	@Column(name="id_quadres")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_quadres;
	
	@Column(name="preu")
	private Double preu;
	
	@Column(name="nom_quadre")
	private String nom_quadre;
	
	@Column(name="nom_autor")
	private String nom_autor;
	
	@Column(name="data_entrada")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date data_entrada;
	
	@JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name= "shop_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
	private Shop shop;
	
	public Quadres() {
		
	}

	public Quadres(Double preu, String nom_quadre, String nom_autor, Date data_entrada) {
		this.preu = preu;
		this.nom_quadre = nom_quadre;
		this.nom_autor = nom_autor;
		this.data_entrada = data_entrada;
	}
	
	public Quadres(int id_quadres, Double preu, String nom_quadre, String nom_autor, Date data_entrada, Shop shop) {
		this.id_quadres = id_quadres;
		this.preu = preu;
		this.nom_quadre = nom_quadre;
		this.nom_autor = nom_autor;
		this.data_entrada = data_entrada;
		this.shop = shop;
	}

	public int getId_quadres() {
		return id_quadres;
	}

	public void setId_quadres(int id_quadres) {
		this.id_quadres = id_quadres;
	}

	public Double getPreu() {
		return preu;
	}

	public void setPreu(Double preu) {
		this.preu = preu;
	}

	public String getNom_quadre() {
		return nom_quadre;
	}

	public void setNom_quadre(String nom_quadre) {
		this.nom_quadre = nom_quadre;
	}

	public String getNom_autor() {
		return nom_autor;
	}

	public void setNom_autor(String nom_autor) {
		this.nom_autor = nom_autor;
	}

	public Date getData_entrada() {
		return data_entrada;
	}

	public void setData_entrada(Date data_entrada) {
		this.data_entrada = data_entrada;
	}

	public Shop getShop_id() {
		return shop;
	}

	public void setShop_id(Shop shop_id) {
		shop= shop_id;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	@Override
	public String toString() {
		return "Quadres [id_quadres=" + id_quadres + ", preu=" + preu + ", nom_quadre=" + nom_quadre + ", nom_autor="
				+ nom_autor + ", data_entrada=" + data_entrada + "]";
	}
	
	
	
}

