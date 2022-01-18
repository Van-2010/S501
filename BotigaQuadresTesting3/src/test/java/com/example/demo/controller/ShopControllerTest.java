package com.example.demo.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.example.demo.entity.Quadres;
import com.example.demo.entity.Shop;
import com.example.demo.repository.BaseDatosQuadres;
import com.example.demo.repository.BaseDatosShop;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(classes= {ShopControllerTest.class})
public class ShopControllerTest {

	@Mock//crea un simulacre.Serà injectat a @InjectMocks ShopController shopController;
	BaseDatosShop baseDatos;
	
	@InjectMocks
	ShopController shopController;

	List<Shop> llistaBotiga;
	Shop shop;
	//Optional<Shop> optionalBotiga;
	
	List<Quadres>llistaQuadres;
	Quadres quadre;
	
	@Mock
	BaseDatosQuadres baseDatosQuadres;
	
	@Test
	@Order(1)
	public void getAllShopTest() {
		
		List<Shop>llistaBotiga=new ArrayList<Shop>();
		llistaBotiga.add(new Shop(1,"Rafles",20));
		llistaBotiga.add(new Shop(2,"Jewellery",20));
		
		when(baseDatos.findAll()).thenReturn(llistaBotiga);//Mocking de la dependencia externa
		
		ResponseEntity<List<Shop>> res=shopController.getShop();//es busca la  resposta
		
		assertEquals(HttpStatus.OK,res.getStatusCode());//verifiquem la resposta
		assertEquals(2,res.getBody().size());
	}
	
	@Test
	@Order(2)
	public void crearShopTest() {
		
		shop=new Shop(1,"Lolis",13);

		when(baseDatos.save(shop)).thenReturn(shop);//Mocking de la dependencia externa
		ResponseEntity<Shop> res=shopController.crearShop("Lolis",13);
		
		assertEquals(HttpStatus.OK,res.getStatusCode());//verifiquem la resposta
		assertEquals("Lolis",res.getBody().getNom());
	}
	@Test
	@Order(3)
	public void crearQuadreTest() {
		
		shop=new Shop(1,"Lolis",13);
		
		quadre=new Quadres(1,23.0,"Naturaleza muerta con flores","Cezanne",new Date(1888-06-24),shop);
		
		int idShop=1;
		
		when(baseDatos.findById(idShop)).thenReturn(Optional.of(shop));
		//shop.addQuadre(quadre);//és necessari posar això?
		
		ResponseEntity<String> res=shopController.crearQuadre(idShop, quadre);
		assertEquals(HttpStatus.OK,res.getStatusCode());//verifiquem la resposta
		
		assertEquals("El quadre amb id 1 ha sigut insertat amb èxit",res.getBody());
		//em retorna un String
	}
	@Test
	@Order(4)
	public void llistarQuadresTest() {
		
		shop=new Shop(1,"Lolis",13);
		int idShop=1;
		Quadres quadre1=new Quadres(1,3000.0,"Naturalesa Morta","Cezanne",new Date(1888-06-24),shop);
		Quadres quadre2=new Quadres(2,23.0,"Els elefants","Picasso",new Date(1984-06-24),shop);
		Quadres quadre3=new Quadres(3,23.0,"Les senyoretes d'Avinyó","Picasso",new Date(1888-06-24),shop);
		shop.addQuadre(quadre1);
		shop.addQuadre(quadre2);
		shop.addQuadre(quadre3);
		
		List<Quadres>llistaQuadres=new ArrayList<Quadres>();
		
		llistaQuadres.add(quadre1);
		llistaQuadres.add(quadre2);
		llistaQuadres.add(quadre3);
		
		int id_quadres=1;
		
		when(baseDatos.findById(idShop)).thenReturn(Optional.of(shop));
	    //when(baseDatosQuadres.getById(idShop)).thenReturn(quadre);
		
	    ResponseEntity<List<Quadres>> res = shopController.getQuadre(id_quadres);
		assertEquals(HttpStatus.OK,res.getStatusCode());
		assertEquals(1,res.getBody().get(0).getId_quadres());
	}
	@Test
	@Order(5)
	public void deleteQuadresTest() {
		shop=new Shop(1,"Lolis",13);
		int idShop=1;
		quadre=new Quadres(1,3000.0,"Naturalesa Morta","Cezanne",new Date(1888-06-24),shop);
		int id_quadres=1;
		when(baseDatos.findById(idShop)).thenReturn(Optional.of(shop));
		when(baseDatosQuadres.deleteAllByShop(shop)).thenReturn(id_quadres);
		
		ResponseEntity<String> res = shopController.deleteQuadres(idShop);
		assertEquals(HttpStatus.OK,res.getStatusCode());
		assertEquals("El quadre s'ha borrat amb èxit",res.getBody());
		
	}
}
