package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Quadres;
import com.example.demo.entity.Shop;

@Repository
public interface BaseDatosQuadres extends JpaRepository <Quadres,Integer>{
	
	int deleteAllByShop(Shop shop);
}
