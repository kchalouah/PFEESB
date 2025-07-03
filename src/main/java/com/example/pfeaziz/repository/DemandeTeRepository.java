/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.pfeaziz.repository;


import com.example.pfeaziz.model.DemandeTe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandeTeRepository extends JpaRepository<DemandeTe, Long> {
}

