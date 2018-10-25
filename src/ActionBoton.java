import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Clase que implementa el listener de los botones del Buscaminas.
 * De alguna manera tendrá que poder acceder a la ventana principal.
 * Se puede lograr pasando en el constructor la referencia a la ventana.
 * Recuerda que desde la ventana, se puede acceder a la variable de tipo ControlJuego
 * @author fernandoiniestabermejo
 **
 */
public class ActionBoton implements ActionListener{

	VentanaPrincipal ventana;

	public ActionBoton() {
		ventana=new VentanaPrincipal();
	}
	
	/**
	 *Acción que ocurrirá cuando pulsamos uno de los botones.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				ventana.mostrarNumMinasAlrededor(i, j);
			}
		}
	}

}
