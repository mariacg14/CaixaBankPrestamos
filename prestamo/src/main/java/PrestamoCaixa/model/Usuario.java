package PrestamoCaixa.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * Entidad que representa a un usuario del sistema.
 * Un usuario puede ser un cliente, un gestor o el sistema,
 * y puede tener asociadas una o varias solicitudes de préstamo.
 */
@Entity
@Table(name = "usuarios")
public class Usuario {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	
	
	@Column(nullable = false)
	private String nombre;
	
	@Column(nullable = false, unique = true)
	private String DNI;
	
	  @Enumerated(EnumType.STRING)
	  @Column(nullable = false)
	  private Rol rol;
	
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<SolicitudPrestamo> prestamos = new ArrayList<>();
	
	// Constructores
    public Usuario() { }

    /**
     * Constructor con parámetros para crear un usuario.
     */
    public Usuario(String nombre, String documentoIdentificativo, Rol rol) {
        this.nombre = nombre;
        this.DNI = documentoIdentificativo;
        this.rol=rol;
        this.prestamos = new ArrayList<>();
    }
	
	// Getters y setters
	
	public Long getId()
	{
		return id;
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
	
	public void setDNI( String DNI)
	{
		this.DNI = DNI;
	}

    public Rol getRol()
    {
        return rol;
    }

    public void setRol(Rol rol) 
    {
        this.rol = rol;
    }
	public List<SolicitudPrestamo> getPrestamos()
	{
		return prestamos;
	}
	public void SetPrestamos(List<SolicitudPrestamo> prestamos)
	{
		this.prestamos = prestamos;
	}
}
