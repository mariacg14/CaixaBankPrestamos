package PrestamoCaixa.model;

import jakarta.persistence.*;
import java.util.UUID;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

//import jakarta.persistence.GeneratedValue;

/**
 * Entidad que representa una solicitud de préstamo personal.
 * Contiene la información básica del préstamo y su estado.
 */
@Entity
@Table(name = "solicitud_prestamo")
public class SolicitudPrestamo {

	@Id
	@GeneratedValue
   private UUID id;

	@Column(nullable = false)
	private BigDecimal importe;
	
	@Column(nullable = false)
	private String divisa;
	
	@Column(nullable = false)
	private LocalDateTime fechaCreacion;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private EstadoPrestamo estado;
	
	@ManyToOne
	@JoinColumn(name="usuario_id, nullable=false")
	@JsonBackReference
	private Usuario usuario;
	
	// Constructores
    public SolicitudPrestamo() { }

    /**
     * Constructor para crear una solicitud de préstamo.
     * El estado y la fecha se inicializan automáticamente.
     */
    public SolicitudPrestamo(BigDecimal importe, String divisa, Usuario usuario) {
        this.importe = importe;
        this.divisa = divisa;
        this.usuario = usuario;
    }

    // Inicializa fecha de creación y estado antes de guardar
    @PrePersist
    public void inicializar() {
        this.fechaCreacion = LocalDateTime.now();
        this.estado = EstadoPrestamo.PENDIENTE;
    }
	
	// Getters y setters
	
	public UUID getId()
	{
		return id;
	}
	
	public void setId(UUID id) {
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

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public EstadoPrestamo getEstado() {
		return estado;
	}

	public void setEstado(EstadoPrestamo estado) {
		this.estado = estado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	
	
	
	
}
