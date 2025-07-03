/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.pfeaziz.repository;


import java.util.List;

import com.example.pfeaziz.model.DemandeDelegue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandeDelegueRepository extends JpaRepository<DemandeDelegue, Long>{

    @Override
    public List<DemandeDelegue> findAll();
}
