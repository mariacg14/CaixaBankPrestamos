package PrestamoCaixa.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


/**
 * DTO utilizado para devolver la información
 * de una solicitud de préstamo.
 */
public class SolicitudPrestamoResponse {

    private UUID id;
    private BigDecimal importe;
    private String divisa;
    private LocalDateTime fechaCreacion;
    private String estado;
    private UsuarioResponse usuario;

    // Getters y setters
    public UUID getId() 
    { 
    	return id; 
    }
    
    public void setId(UUID id)
    {
    	this.id = id;
    }
    
    public BigDecimal getImporte()
    { 
    	return importe;
    }
    
    public void setImporte(BigDecimal importe)
    { 
    	this.importe = importe;
    }
    
    public String getDivisa() 
    {
    	return divisa; 
    }
    
    public void setDivisa(String divisa)
    { 
    	this.divisa = divisa; 
    }
    
    public LocalDateTime getFechaCreacion() 
    { 
    	return fechaCreacion; 
    }
    
    public void setFechaCreacion(LocalDateTime fechaCreacion)
    { 
    	this.fechaCreacion = fechaCreacion;
    }
    
    public String getEstado() 
    { 
    	return estado;
    }
    
    public void setEstado(String estado)
    { 
    	this.estado = estado;
    }
    
    public UsuarioResponse getUsuario() 
    { 
    	return usuario; 
    }
    
    public void setUsuario(UsuarioResponse usuario)
    { 
    	this.usuario = usuario;
    }

    // Clase interna para usuario
    /**
     * Clase interna que representa la información básica
     * del usuario asociada a la solicitud.
     */
    public static class UsuarioResponse {
       
    	private Long id;
        private String nombre;
        private String DNI;
        private String rol;
        
        public Long getId()
        { 
        	return id; 
        }
        
        public void setId(Long id) 
        { 
        	this.id = id; 
        }
        
        public String getNombre() 
        { 
        	return nombre; 
        }
        
        public void setNombre(String nombre)
        { 
        	this.nombre = nombre;
        }
        
        public String getDNI()
        { 
        	return DNI;
        }
        
        public void setDNI(String DNI) 
        { 
        	this.DNI = DNI; 
        }
        
        public String getRol() 
        { 
        	return rol;
        }
        
        public void setRol(String rol)
        { 
        	this.rol = rol; 
        }
    }
}
