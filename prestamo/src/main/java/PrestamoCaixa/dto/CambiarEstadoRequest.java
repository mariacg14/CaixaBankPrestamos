package PrestamoCaixa.dto;

import PrestamoCaixa.model.EstadoPrestamo;
import jakarta.validation.constraints.NotNull;

/**
 * DTO utilizado para recibir la petición de cambio de estado
 * de una solicitud de préstamo.
 */
public class CambiarEstadoRequest {

	/**
     * Nuevo estado al que se quiere cambiar la solicitud.
     * No puede ser nulo.
     */
    @NotNull
    private EstadoPrestamo estado;

    /**
     * Devuelve el estado solicitado.
     */
    public EstadoPrestamo getEstado() 
    {
    	return estado;
    	
    }
    
    /**
     * Establece el nuevo estado solicitado.
     */
    public void setEstado(EstadoPrestamo estado)
    { 
    	this.estado = estado;
    	
    }
}
