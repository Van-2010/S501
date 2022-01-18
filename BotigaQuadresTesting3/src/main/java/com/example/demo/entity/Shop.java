package com.example.demo.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(name="shop")
public class Shop implements Serializable {
	
	
	@Id
	@Column(name="id_shop")
	@GeneratedValue(strategy=GenerationType.IDENTITY)//aquí pot ser auto o identity. Ambb auto em generava error
	private int idShop;
	
	@Column(name="nom")
	private String nom;
	
	@Column(name="capacitat_quadres")
	private int capacitatQuadres;
	
	//@JoinColumn
	@JsonIgnore
	@OneToMany(mappedBy="shop",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private List<Quadres> quadres;
	
	public Shop() {
	
	}
	public Shop(int idShop,String nom, int capacitatQuadres) {
		this.idShop = idShop;
		this.nom = nom;
		this.capacitatQuadres = capacitatQuadres;
	}
	public Shop(String nom, int capacitatQuadres) {
		this.nom = nom;
		this.capacitatQuadres = capacitatQuadres;
	}

	public int getIdShop() {
		return idShop;
	}

	public void setIdShop(int idShop) {
		this.idShop = idShop;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getCapacitatQuadres() {
		return capacitatQuadres;
	}

	public void setCapacitatQuadres(int capacitatQuadres) {
		this.capacitatQuadres = capacitatQuadres;
	}

	public List<Quadres> getQuadres() {
		return quadres;
	}

	public void setQuadres(List<Quadres> quadres) {
		this.quadres = quadres;
	}
	
	
	public void addQuadre(Quadres quadre) {
		if(quadres==null) {//si la llista de quadres està buida
             quadres=new ArrayList<>();
		}
		quadre.setShop_id(this);//guarda'l a la botiga amb l'id de la botiga
		quadres.add(quadre); //afegeix quadre
		
	}
	public void cremaQuadres() {
		this.quadres.removeAll(quadres);
	}

	@Override
	public String toString() {
		return "Shop [idShop=" + idShop + ", nom=" + nom + ", capacitatQuadres=" + capacitatQuadres + ", quadres="
				+ quadres + "]";
	}
	
}