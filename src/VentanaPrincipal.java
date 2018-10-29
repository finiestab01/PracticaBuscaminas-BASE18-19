import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * Clase gestora de la ventana, en esta se generan e inicializan los botones y el tablero.
 * Tambi�n es donde se inician los listener de cada bot�n. Muestra las minas alrededor y comprueba
 * si es el fin del juego mostrando un cuadro con la puntuaci�n y si el usuario quiere volver a jugar
 * {@link #inicializar()}
 * <p>
 * {@code ventana.setVisible(true);
		inicializarComponentes();	
		inicializarListeners();	}
 * </p>
 * @author fernandoiniestabermejo
 * @version 1.0
 * @since 26/10/2018
 * @see ControlJuego
 *
 */

public class VentanaPrincipal {

	//La ventana principal, en este caso, guarda todos los componentes:
	JFrame ventana;
	JPanel panelImagen;
	JPanel panelEmpezar;
	JPanel panelPuntuacion;
	JPanel panelJuego;
	
	//Todos los botones se meten en un panel independiente.
	//Hacemos esto para que podamos cambiar después los componentes por otros
	JPanel [][] panelesJuego;
	JButton [][] botonesJuego;
	
	//Correspondencia de colores para las minas:
	Color correspondenciaColores [] = {Color.BLACK, Color.CYAN, Color.GREEN, Color.ORANGE, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED};
	
	JButton botonEmpezar;
	JTextField pantallaPuntuacion;
	
	
	//LA VENTANA GUARDA UN CONTROL DE JUEGO:
	ControlJuego juego;
	
	
	//Constructor, marca el tamaño y el cierre del frame
	public VentanaPrincipal() {
		ventana = new JFrame();
		ventana.setBounds(100, 100, 700, 500);
		ventana.setLocationRelativeTo(null);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		juego = new ControlJuego();
	}
	
	//Inicializa todos los componentes del frame
	public void inicializarComponentes(){
		
		//Definimos el layout:
		ventana.setLayout(new GridBagLayout());
		
		//Inicializamos componentes
		panelImagen = new JPanel();
		panelEmpezar = new JPanel();
		panelEmpezar.setLayout(new GridLayout(1,1));
		panelPuntuacion = new JPanel();
		panelPuntuacion.setLayout(new GridLayout(1,1));
		panelJuego = new JPanel();
		panelJuego.setLayout(new GridLayout(10,10));
		
		
		botonEmpezar = new JButton("Go!");
		pantallaPuntuacion = new JTextField("0");
		pantallaPuntuacion.setEditable(false);
		pantallaPuntuacion.setHorizontalAlignment(SwingConstants.CENTER);
		
		//Bordes y colores:
		panelImagen.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		panelEmpezar.setBorder(BorderFactory.createTitledBorder("Empezar"));
		panelPuntuacion.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		panelJuego.setBorder(BorderFactory.createTitledBorder("Juego"));
		
			
		//Colocamos los componentes:
		//AZUL
		GridBagConstraints settings = new GridBagConstraints();
		settings.gridx = 0;
		settings.gridy = 0;
		settings.weightx = 1;
		settings.weighty = 1;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelImagen, settings);
		//VERDE
		settings = new GridBagConstraints();
		settings.gridx = 1;
		settings.gridy = 0;
		settings.weightx = 1;
		settings.weighty = 1;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelEmpezar, settings);
		//AMARILLO
		settings = new GridBagConstraints();
		settings.gridx = 2;
		settings.gridy = 0;
		settings.weightx = 1;
		settings.weighty = 1;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelPuntuacion, settings);
		//ROJO
		settings = new GridBagConstraints();
		settings.gridx = 0;
		settings.gridy = 1;
		settings.weightx = 1;
		settings.weighty = 10;
		settings.gridwidth = 3;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelJuego, settings);
		
		tableroNuevo();
		
	}
	
	
	public void tableroNuevo() {
		//Paneles
				panelesJuego = new JPanel[10][10];
				for (int i = 0; i < panelesJuego.length; i++) {
					for (int j = 0; j < panelesJuego[i].length; j++) {
						panelesJuego[i][j] = new JPanel();
						panelesJuego[i][j].setLayout(new GridLayout(1,1));
						panelJuego.add(panelesJuego[i][j]);
					}
				}
				
				//Botones
				botonesJuego = new JButton[10][10];
				for (int i = 0; i < botonesJuego.length; i++) {
					for (int j = 0; j < botonesJuego[i].length; j++) {
						botonesJuego[i][j] = new JButton("-");
						panelesJuego[i][j].add(botonesJuego[i][j]);
					}
				}
				
				//BotónEmpezar:
				panelEmpezar.add(botonEmpezar);
				panelPuntuacion.add(pantallaPuntuacion);
		
	}

	/**
	 * Método que inicializa todos los lísteners que necesita inicialmente el programa
	 */
	public void inicializarListeners(){
		botonEmpezar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				panelJuego.removeAll();
				tableroNuevo();
				refrescarPantalla();
				juego.inicializarPartida();
				inicializarListeners();
				
			}
		});

		for (int i = 0; i < botonesJuego.length; i++) {
			int y=i;
			for (int j = 0; j < botonesJuego[i].length; j++) {
				int x=j;
				botonesJuego[i][j].addActionListener(new ActionBoton(this,y,x));
				//Marcar posible mina
				botonesJuego[i][j].addMouseListener(new MouseAdapter() {	
					@Override
					public void mousePressed(MouseEvent e) {
						if(e.getButton()==MouseEvent.BUTTON3) {
							botonesJuego[y][x].setBackground(Color.RED);
						}
					}	
				});
			}
		}
		
	}
	
	
	/**
	 * Pinta en la pantalla el número de minas que hay alrededor de la celda
	 * Saca el botón que haya en la celda determinada y añade un JLabel centrado y no editable con el número de minas alrededor.
	 * Se pinta el color del texto según la siguiente correspondecia (consultar la variable correspondeciaColor):
	 * - 0 : negro
	 * - 1 : cyan
	 * - 2 : verde
	 * - 3 : naranja
	 * - 4 ó más : rojo 
	 * @param i: posición vertical de la celda.
	 * @param j: posición horizontal de la celda.
	 */
	public void mostrarNumMinasAlrededor(int i , int j) {
		if(juego.abrirCasilla(i, j)) {
		panelesJuego[i][j].remove(0);
		JLabel agua=new JLabel(juego.getMinasAlrededor(i, j)+"");
		agua.setHorizontalAlignment(SwingConstants.CENTER);
		agua.setForeground(correspondenciaColores[juego.getMinasAlrededor(i, j)]);
		panelesJuego[i][j].add(agua);
		refrescarPantalla();
		}else {
			panelesJuego[i][j].remove(0);
			JLabel agua=new JLabel("X");
			agua.setHorizontalAlignment(SwingConstants.CENTER);
			panelesJuego[i][j].add(agua);
			refrescarPantalla();
			mostrarFinJuego(true);
		}
	}
	
	
	/**
	 * Muestra una ventana que indica el fin del juego
	 * @param porExplosion : Un booleano que indica si es final del juego porque ha explotado una mina (true) o bien porque hemos desactivado todas (false)
	 */
	public void mostrarFinJuego(boolean porExplosion) {
		String opane;
		if(porExplosion) {
			opane="Perdiste!!\nPuntuaci�n: "+juego.getPuntuacion()+"\nJugar otra vez?";
		}else {
			opane="Ganaste!!\nPuntuaci�n: "+juego.getPuntuacion()+"\nJugar otra vez?";
		}
		
		int boton=JOptionPane.showConfirmDialog(ventana, opane,"Fin de programa",JOptionPane.YES_NO_OPTION);
		if(boton==0) {
			panelJuego.removeAll();
			tableroNuevo();
			refrescarPantalla();
			juego.inicializarPartida();
			inicializarListeners();
		}else {
			System.exit(0);
		}
	}

	/**
	 * Método que muestra la puntuación por pantalla.
	 */
	public void actualizarPuntuacion() {
		pantallaPuntuacion.setText(juego.getPuntuacion()+"");
		if(juego.getPuntuacion()==80) {
			mostrarFinJuego(false);
		}
	}
	
	/**
	 * Método para refrescar la pantalla
	 */
	public void refrescarPantalla(){
		ventana.revalidate(); 
		ventana.repaint();
	}

	/**
	 * Método que devuelve el control del juego de una ventana
	 * @return un ControlJuego con el control del juego de la ventana
	 */
	public ControlJuego getJuego() {
		return juego;
	}

	/**
	 * Método para inicializar el programa
	 */
	public void inicializar(){
		//IMPORTANTE, PRIMERO HACEMOS LA VENTANA VISIBLE Y LUEGO INICIALIZAMOS LOS COMPONENTES.
		ventana.setVisible(true);
		inicializarComponentes();	
		inicializarListeners();		
	}



}
