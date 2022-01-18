package com.example.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Quadres;
import com.example.demo.entity.Shop;
import com.example.demo.repository.BaseDatosQuadres;
import com.example.demo.repository.BaseDatosShop;


@RestController
@RequestMapping("/shops")
public class ShopController {
	
	@Autowired
	private BaseDatosShop baseDatos;
	
	@Autowired
	private BaseDatosQuadres baseDatosQuadres;
	
	//1.CREAR BOTIGA
	@PostMapping("/{nom}/{capacitatQuadres}")
	//@PostMapping("")
	public ResponseEntity<Shop> crearShop(@PathVariable(name="nom")String nom,@PathVariable(name="capacitatQuadres")int capacitatQuadres){
		
		//public ResponseEntity<Shop> crearShop(@RequestBody Shop shop)
		
		Shop novaBotiga=new Shop(nom,capacitatQuadres);
		baseDatos.save(novaBotiga);
		return ResponseEntity.ok(novaBotiga);		
	}
	
	//2.LLISTAR BOTIGA
	@GetMapping("/listar")
	public ResponseEntity <List<Shop>> getShop(){
		try {
			List<Shop>llistaBotiga=baseDatos.findAll();//conexió a  la base de dades
		return ResponseEntity.ok(llistaBotiga);
		
		}catch (Exception e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
							
	}
	//3.AFEGIR QUADRE
	@PostMapping("/{id_shop}/quadres")
	public ResponseEntity<String> crearQuadre(@PathVariable(name="id_shop")int id_shop,@RequestBody Quadres quadre){
		
		Optional<Shop> optionalBotigaDao = baseDatos.findById(id_shop);//busca la tenda i la posa en una botiga que pot estar buida o plena per lo que encara que no hi hagui res no peta
		
		
		if(optionalBotigaDao.isPresent()) { //si la botiga existeix 
			
			Shop botiga=optionalBotigaDao.get(); //guarda'm la botiga 
			
			quadre.setShop(botiga);//li donem al quadre l'id de la botiga. Com quee ho fem amb jpa, li passem la botiga sencera i el toMany fa que agafi automaticament l'id 
			
			baseDatosQuadres.save(quadre);//guardem el quadre
			
			botiga.addQuadre(quadre);//Afegim el quadre mitjançant un mètode que hem creat a Shop
			
			baseDatos.save(botiga);
			
			return ResponseEntity.ok("El quadre amb id "+quadre.getId_quadres()+" ha sigut insertat amb èxit");
		
		}else {
			
			return ResponseEntity.ok("la botiga "+id_shop +" no existeix");
			
		}			
	}
	
	//2.LLISTAR QUADRES
		@GetMapping("/{id_shop}/quadres")
		public ResponseEntity <List<Quadres>> getQuadre(@PathVariable(name="id_shop")int idShop){
			
			Optional<Shop> optionalBotigaDao = baseDatos.findById(idShop);
			
			//if(optionalBotigaDao.isPresent()) {
			if (!optionalBotigaDao.isEmpty()) {
				Shop botiga=optionalBotigaDao.get();
				List<Quadres>llistaQuadres=botiga.getQuadres();
						
			return ResponseEntity.ok(llistaQuadres);
			
			}else {
		
				List<Quadres>buida=new ArrayList<Quadres>();
		
				return ResponseEntity.ok(buida);
			}
		}
		
	//4.ELIMINAR QUADRE
		
	@Transactional
	@DeleteMapping("/{id_shop}/pictures")
	public ResponseEntity<String> deleteQuadres(@PathVariable("id_shop")int id_shop){
		Optional<Shop> shop=baseDatos.findById(id_shop);
		if(!shop.isEmpty()) {
			baseDatosQuadres.deleteAllByShop(shop.get());
			return ResponseEntity.ok("El quadre s'ha borrat amb èxit");
		}else {
			return ResponseEntity.ok("no trobat");
		}
	}
		
	//5.ACTUALITZAR BOTIGA
	@PutMapping ("/update") // /shop(update)
	public ResponseEntity<Shop>UpdateShop(@RequestBody Shop botiga){
		
		Optional<Shop>optionalShop=baseDatos.findById(botiga.getIdShop());
		
		if(optionalShop.isPresent()) {
			Shop updateShop=optionalShop.get();
		 updateShop.setNom(botiga.getNom());
		 updateShop.setCapacitatQuadres(botiga.getCapacitatQuadres());
		
		 baseDatos.save(updateShop);
		 return ResponseEntity.ok(updateShop);
		}else {
			
			return  ResponseEntity.notFound().build();
		}
	}
}
