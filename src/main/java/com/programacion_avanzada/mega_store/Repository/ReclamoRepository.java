package com.programacion_avanzada.mega_store.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.programacion_avanzada.mega_store.Modelos.Reclamo;

public interface ReclamoRepository extends JpaRepository<Reclamo, Long>{
    boolean existsById(long id);

    List<Reclamo> findByUsuarioId(Long idUsuario);
    Reclamo findById(long id);

    //Estadisticas de reclamo

    @Query("SELECT r.tipoReclamo.nombre, COUNT(r) FROM Reclamo r " +
           "WHERE r.fechaCreacion BETWEEN :fechaInicio AND :fechaFin " +
           "AND r.estaActivo = true " +
           "GROUP BY r.tipoReclamo.nombre")
    List<Object[]> obtenerEstadisticasPorFechas(@Param("fechaInicio") LocalDateTime fechaInicio, 
                                                @Param("fechaFin") LocalDateTime fechaFin);
}
