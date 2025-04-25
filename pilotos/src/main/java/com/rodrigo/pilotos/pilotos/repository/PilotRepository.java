/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rodrigo.pilotos.pilotos.repository;
import com.rodrigo.pilotos.pilotos.model.Pilot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PilotRepository extends JpaRepository<Pilot, Long> {
    List<Pilot> findByTeamId(Long teamId);
}
