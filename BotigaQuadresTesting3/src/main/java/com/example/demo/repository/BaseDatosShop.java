package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.demo.entity.Shop;

//DAO

public interface BaseDatosShop extends JpaRepository<Shop,Integer> {
	

}
