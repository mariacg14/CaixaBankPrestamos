package PrestamoCaixa.controller;

import PrestamoCaixa.dto.*;
import PrestamoCaixa.model.Rol;
import PrestamoCaixa.service.PrestamoService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controlador REST que expone los endpoints
 * relacionados con las solicitudes de préstamo.
 */
@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {

	/**
     * Servicio que contiene la lógica de negocio.
     */
    private final PrestamoService service;

    public PrestamoController(PrestamoService service) {
        this.service = service;
    }

    // ---------------- Crear solicitud ----------------
    /**
     * Endpoint para crear una nueva solicitud de préstamo.
     */
    @PostMapping
    public SolicitudPrestamoResponse crear(@Valid @RequestBody CrearSolicitudPrestamoRequest request) {
        return service.crearSolicitud(request);
    }

    
    // ---------------- Listar todas las solicitudes ----------------
  /*  @GetMapping
    public List<SolicitudPrestamoResponse> listar() {
        return service.listarSolicitudes();
    }*/

    
    // ---------------- Listar todas las solicitudes ----------------
    // Para simplificar, recibimos rol y usuarioId por parámetros (simulando autenticación)
    /**
     * Lista las solicitudes según el rol del usuario.
     * - GESTOR/SISTEMA: ve todas
     * - USUARIO: solo ve sus propias solicitudes
     */
    @GetMapping
    public List<SolicitudPrestamoResponse> listar(
            @RequestParam Rol rol,
            @RequestParam(required = false) Long usuarioId) {

        return service.listarSolicitudes(rol, usuarioId);
    }
    
    
    // ---------------- Obtener solicitud por ID ----------------
    /**
     * Obtiene una solicitud concreta a partir de su ID. Solo gestores
     */
    
    @GetMapping("/{id}")
    public SolicitudPrestamoResponse obtener(
            @PathVariable UUID id,
            @RequestParam Rol rol) {

        if (rol != Rol.GESTOR) {
            throw new IllegalArgumentException("Solo el gestor puede ver solicitudes por ID");
        }

        return service.obtenerSolicitud(id);
    }

    // ---------------- Cambiar estado de solicitud ----------------
    /**
     * Cambia el estado de una solicitud existente.Solo gestores
     */
    @PatchMapping("/{id}/estado")
    public SolicitudPrestamoResponse cambiarEstado(
            @PathVariable UUID id,
            @RequestParam Rol rol,
            @Valid @RequestBody CambiarEstadoRequest request) {

        if (rol != Rol.GESTOR) {
            throw new IllegalArgumentException("Solo el gestor puede cambiar el estado");
        }

        return service.cambiarEstado(id, request.getEstado());
    }
}
