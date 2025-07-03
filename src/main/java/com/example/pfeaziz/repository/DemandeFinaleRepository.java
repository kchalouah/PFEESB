/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.pfeaziz.repository;


import java.util.List;

import com.example.pfeaziz.model.DemandeFinale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandeFinaleRepository extends JpaRepository<DemandeFinale, Long>{

    @Override
    public List<DemandeFinale> findAll();
}
