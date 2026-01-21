package PrestamoCaixa.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

/**
 * DTO utilizado para recibir los datos necesarios
 * para crear una nueva solicitud de préstamo.
 */
public class CrearSolicitudPrestamoRequest {

    @NotBlank
    private String nombreSolicitante;

    /**
     * Documento identificativo del solicitante (DNI o NIE).
     * Se valida mediante una expresión regular.
     */
    @NotBlank
    @Pattern(
        regexp = "^[0-9]{8}[A-Z]$|^[XYZ][0-9]{7}[A-Z]$",
        message = "DNI/NIE no válido"
    )
    private String DNI;

    @NotNull
    @Positive
    private BigDecimal importe;

    @NotBlank
    private String divisa;

    // Getters y setters
    public String getNombreSolicitante() 
    { 
    	return nombreSolicitante;
    }
    public void setNombreSolicitante(String nombreSolicitante) 
    { 
    	this.nombreSolicitante = nombreSolicitante; 
    }

    public String getDNI() 
    { 
    	return DNI; 
    }
    public void setDNI(String DNI)
    { 
    	this.DNI = DNI;
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
}
