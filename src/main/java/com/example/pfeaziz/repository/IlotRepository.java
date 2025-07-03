/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.pfeaziz.repository;

/**
 *
 * @author xfour
 */

import com.example.pfeaziz.model.Ilot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IlotRepository extends JpaRepository<Ilot, Long> {
}
