package PrestamoCaixa.service;


import PrestamoCaixa.model.*;
import PrestamoCaixa.repository.*;
import org.springframework.stereotype.Service;
import PrestamoCaixa.dto.SolicitudPrestamoResponse;
import PrestamoCaixa.dto.CrearSolicitudPrestamoRequest;

import PrestamoCaixa.model.Rol;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Servicio que contiene la lógica de negocio
 * relacionada con las solicitudes de préstamo.
 */
@Service
public class PrestamoService {
	
    /**
     * Repositorio de usuarios y solicitudes de préstamo.
     */

    private final UsuarioRepository usuarioRepository;
    private final SolicitudPrestamoRepository prestamoRepository;

    public PrestamoService(UsuarioRepository usuarioRepository,
                           SolicitudPrestamoRepository prestamoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.prestamoRepository = prestamoRepository;
    }

      
    	// Validar formato DNI
        /*if (!dto.getDNI().matches("^[0-9]{8}[A-Z]$|^[XYZ][0-9]{7}[A-Z]$")) {
            throw new IllegalArgumentException("DNI/NIE no válido");
        }*/

        // Validar duplicado
      /*  if (usuarioRepository.existsByDNI(dto.getDNI())) {
            throw new IllegalArgumentException("Ya existe un usuario con ese DNI");
        }*/
        
    	// Crear usuario si no existe (o siempre crear nuevo para simplificar)
    //	Usuario usuario = new Usuario(dto.getNombreSolicitante(), dto.getDNI(), Rol.USUARIO);

    
    // ---------------- Crear solicitud ----------------
    	 /**
         * Crea una nueva solicitud de préstamo.
         * Si el usuario no existe, se crea.
         * Si existe, se valida que el nombre coincida con el DNI.
         */
   
    public SolicitudPrestamoResponse crearSolicitud(CrearSolicitudPrestamoRequest dto) {
     
    	// Buscar usuario por dni
        Usuario usuario = usuarioRepository.findByDNI(dto.getDNI())
            .map(u -> {
                if (!u.getNombre().equals(dto.getNombreSolicitante())) { // Si existe, validar que el nombre coincida
                    throw new IllegalArgumentException("El nombre no coincide con el DNI existente");
                }
                return u;
            })
            .orElseGet(() -> {
                // Si no existe, crear nuevo usuario
                Usuario u = new Usuario(dto.getNombreSolicitante(), dto.getDNI(), Rol.USUARIO);
                usuarioRepository.save(u);
                return u;
            });
    	
        // Crear solicitud de préstamo
        SolicitudPrestamo prestamo = new SolicitudPrestamo(dto.getImporte(), dto.getDivisa(), usuario);
        
        prestamo.setFechaCreacion(LocalDateTime.now());
        prestamo.setEstado(EstadoPrestamo.PENDIENTE);

        // Relación bidireccional
        usuario.getPrestamos().add(prestamo);

       // Guardar préstamo explícitamente para que genere UUID
        prestamoRepository.save(prestamo); 
        
        // Guardar en repositorio
        usuarioRepository.save(usuario);

        // Mapear a DTO
        return mapToDTO(prestamo);
    }

    // ---------------- Listar todas las solicitudes ----------------
   /* public List<SolicitudPrestamoResponse> listarSolicitudes() {
        return prestamoRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }*/
    
 // ---------------- Listar solicitudes ----------------
    // Si es GESTOR o SISTEMA devuelve todas, si es USUARIO solo las suyas
    public List<SolicitudPrestamoResponse> listarSolicitudes(Rol rol, Long usuarioId) {
        List<SolicitudPrestamo> prestamos;

        if (rol == Rol.GESTOR || rol == Rol.SISTEMA) {
            prestamos = prestamoRepository.findAll();
        } else {
            prestamos = prestamoRepository.findByUsuarioId(usuarioId);
        }

        return prestamos.stream()
        		.map(this::mapToDTO)
        		.collect(Collectors.toList());
    }


    
    // ---------------- Obtener solicitud por ID ----------------
    //Obtiene una solicitud concreta por su identificador.
    public SolicitudPrestamoResponse obtenerSolicitud(UUID id) {
        SolicitudPrestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));
        return mapToDTO(prestamo);
    }

    // ---------------- Cambiar estado de solicitud ----------------
    /**
     * Cambia el estado de una solicitud validando
     * que la transición sea correcta.
     */
    public SolicitudPrestamoResponse cambiarEstado(UUID id, EstadoPrestamo nuevoEstado) {
        SolicitudPrestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        // Validar transición
        if (!prestamo.getEstado().puedeTransicionarA(nuevoEstado)) {
            throw new IllegalArgumentException("Transición de estado no permitida");
        }
        prestamo.setEstado(nuevoEstado);
        prestamoRepository.save(prestamo);

        return mapToDTO(prestamo);
    }

    // ---------------- Validación de transiciones ----------------
    /*private boolean esTransicionValida(EstadoPrestamo actual, EstadoPrestamo nuevo) {
        return (actual == EstadoPrestamo.PENDIENTE && (nuevo == EstadoPrestamo.APROBADA || nuevo == EstadoPrestamo.RECHAZADA))
                || (actual == EstadoPrestamo.APROBADA && nuevo == EstadoPrestamo.CANCELADA)
                || (actual == EstadoPrestamo.RECHAZADA && nuevo == EstadoPrestamo.CANCELADA);
    }*/

    // ---------------- Mapper a DTO ----------------
    /**
     * Convierte una entidad SolicitudPrestamo
     * en un DTO de respuesta.
     */
    private SolicitudPrestamoResponse mapToDTO(SolicitudPrestamo prestamo) {
        SolicitudPrestamoResponse dto = new SolicitudPrestamoResponse();
        dto.setId(prestamo.getId()); //UUID
        dto.setImporte(prestamo.getImporte());
        dto.setDivisa(prestamo.getDivisa());
        dto.setFechaCreacion(prestamo.getFechaCreacion());
        dto.setEstado(prestamo.getEstado().toString());

     // Mapear usuario
        SolicitudPrestamoResponse.UsuarioResponse usuarioDTO = new SolicitudPrestamoResponse.UsuarioResponse();
        usuarioDTO.setId(prestamo.getUsuario().getId()); // Long
        usuarioDTO.setNombre(prestamo.getUsuario().getNombre());
        usuarioDTO.setDNI(prestamo.getUsuario().getDNI());
        usuarioDTO.setRol(prestamo.getUsuario().getRol().name()); 

        dto.setUsuario(usuarioDTO);
        return dto;
    }
}
