package PrestamoCaixa.repository;


import PrestamoCaixa.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID>
{
	// Validar si existe un DNI
    boolean existsByDNI(String DNI);

    // Opcional: buscar usuario por DNI 
    Optional<Usuario> findByDNI(String DNI);

}