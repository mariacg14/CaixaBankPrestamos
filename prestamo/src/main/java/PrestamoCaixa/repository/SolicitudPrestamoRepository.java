package PrestamoCaixa.repository;

import PrestamoCaixa.model.SolicitudPrestamo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SolicitudPrestamoRepository extends JpaRepository<SolicitudPrestamo, UUID> {

    // Lista de préstamos de un usuario específico
    List<SolicitudPrestamo> findByUsuarioId(Long usuarioId);
}