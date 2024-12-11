package com.programacion_avanzada.mega_store.Repository;

import com.programacion_avanzada.mega_store.Modelos.ItemOrden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemOrdenRepository extends JpaRepository<ItemOrden, Long> {
}
