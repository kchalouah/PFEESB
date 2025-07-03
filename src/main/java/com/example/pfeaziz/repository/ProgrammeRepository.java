package com.example.pfeaziz.repository;


import com.example.pfeaziz.model.Programme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgrammeRepository extends JpaRepository<Programme, Long> {
}
