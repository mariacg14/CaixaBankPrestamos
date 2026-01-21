package PrestamoCaixa.model;

public enum EstadoPrestamo {

	PENDIENTE,
	APROBADA,
	RECHAZADA,
	CANCELADA;
	
	/**
     * Indica si el estado actual puede cambiar al estado indicado.
     * Implementa el flujo de estados definido en el enunciado.
     */
	public boolean puedeTransicionarA(EstadoPrestamo nuevo) {
        return switch(this) {
            case PENDIENTE -> nuevo == APROBADA || nuevo == RECHAZADA;
            case APROBADA, RECHAZADA -> nuevo == CANCELADA;
            default -> false;
        };
    }
}
