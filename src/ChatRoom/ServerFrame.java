package ChatRoom;
import java.awt.EventQueue;


import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

 /**
  * Clase ServerFrame  es una clase de tipo JFrame  o ventana tiene Dos clases Server Class y HandleClient class que extienden de la Clases Thread
  * ServerFrame tiene  otra ventana que ServerStatusFrame
  * @author malik
  *
  */

public class ServerFrame {

	private JFrame FrameServer;
	private JFrame FrameServerStatus;
	private JTextField tfPort;
	private JTextArea txServerStatus;
	private  ArrayList<String> users = new ArrayList<String>();
	
	
	 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerFrame window = new ServerFrame();
					window.FrameServer.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ServerFrame() {
		initialize();
		initialize2();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		FrameServer = new JFrame();
		FrameServer.setTitle("Start Server");
		FrameServer.setBounds(100, 100, 450, 300);
		FrameServer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		FrameServer.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Server", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(0, 0, 434, 261);
		FrameServer.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel labelPort = new JLabel("Port");
		labelPort.setBounds(83, 68, 60, 22);
		panel.add(labelPort);

		tfPort = new JTextField();
		tfPort.setText("5555");
		tfPort.setColumns(10);
		tfPort.setBounds(140, 62, 167, 34);
		panel.add(tfPort);

		/**
		 *En el  Evento click del  Button Connect se crea un objeto de la clase server que recibe puerto luego se llama el metodo startServer si esta establicida
		 *se lanza hilo servidor que va estar en boucle esperando las peticiones 
		 * 
		 */
		JButton btnStartServer = new JButton("Start");
		btnStartServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ServerFrame window = new ServerFrame();
				Server server = new Server(Integer.parseInt(tfPort.getText()));
				try {
					if (server.startServer() == true) 
					server.start();
					else JOptionPane.showMessageDialog(FrameServer, "Server Error could not bind to port.","Server Error", JOptionPane.ERROR_MESSAGE);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 
				
			}
		});
		btnStartServer.setBounds(238, 140, 89, 41);
		panel.add(btnStartServer);

		/**
		 * Server Frame Exit Button
		 */
		JButton btnExit = new JButton("Exit");
		
		/**
		 * el  Evento click del  Button se se destruye la ventana FrameServer
		 */
		
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	int dialogResult = JOptionPane.showConfirmDialog(FrameServer, "Are you sure you want to Exit", "Server Frame",  JOptionPane.YES_NO_OPTION);
				
				//yes button
				if(dialogResult == 0) {
					 
					FrameServer.dispose();

				} else {
				  
				} 
				 
			}
		});
		btnExit.setBounds(103, 140, 89, 41);
		panel.add(btnExit);
	}

	/**
	 * Ventana Server Status
	 */

	private void initialize2() {
		FrameServerStatus = new JFrame();
		FrameServerStatus.setTitle("Server Status");
		FrameServerStatus.setResizable(false);
		FrameServerStatus.setBounds(100, 100, 397, 429);
		FrameServerStatus.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		FrameServerStatus.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 394, 402);
		FrameServerStatus.getContentPane().add(panel);
		panel.setLayout(null);

		txServerStatus = new JTextArea();
		txServerStatus.setBounds(0, 48, 406, 354);
		txServerStatus.setEditable(false);
		panel.add(txServerStatus);

		JLabel labelServerStatus = new JLabel("Server Status");
		labelServerStatus.setFont(new Font("Tahoma", Font.BOLD, 11));
		labelServerStatus.setBounds(137, 11, 91, 26);
		panel.add(labelServerStatus);
		
		
		JMenuBar menuBar = new JMenuBar();
		FrameServerStatus.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Menu");
		menuBar.add(mnNewMenu);
		
		/** 
		 * En el  Evento click sobre el menu users list se comprueba si la lista no esta vacia es decir si hay cliente connectado  
		 * se crea un objeto de la clase JDialog 
		 * se crea un objeto de la clase JPanel
		 * se crea Un objeto de clase Jlist que reciba como parametro un array de los usarios
		 * se anade jlist al panel y el panel al JDialog
		 * 
		 */
		JMenuItem mntmNewMenuItem = new JMenuItem("Users List");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			 
			 
				
				//here we check if there is users connected 
				if (users.size() <=0){
					JOptionPane.showMessageDialog(FrameServerStatus, "There is no user connected","users list", JOptionPane.INFORMATION_MESSAGE);
				}else {
				
			    // create a dialog Box 
	            JDialog jd = new JDialog(FrameServerStatus, "Users List"); 
	          
	    		JPanel panel = new JPanel();
	    		panel.setBounds(10, 11, 250, 400);
	    		FrameServerStatus.getContentPane().add(panel);
	    		panel.setLayout(null);
	    		
	    		
	    		JList list = new JList(users.toArray());
	    		list.setBounds(0, 0, 250, 400);
	    		
	    		 
	    		
	    		
	    		//add jlist to apnel 
	    		panel.add(list);
	    		
	    		// add panel to jdialog
	            jd.add(panel);
	           
	  
	            // setsize of Jdialog 
	            jd.setSize(250, 400); 
	  
	            // set visibility 
	            jd.setVisible(true); 
			}
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		

	}
	/**
	 * Clase Server extiende  de la clase Thread tiene metodo startServer y implementa el metodo run  
	 * @author malik
	 *
	 */
	 
	public   class Server  extends Thread  {
	private	ServerSocket serverSocket;
	private Socket clientSocket;

	private   ArrayList<HandleClient> clients = new ArrayList<HandleClient>();
	private int port;
	 

		Server(int port) {
			this.port = port;
		}

		/**
		 * Metodo startServer es metodo booleano permite de crear Server Socket en el  peurto recibido una vez establecida
		 * se oculta la ventana ServerFrame y se muestra La Ventana ServerStatus 
		 * @return Boolean  return = true si la conexion  esta establecida
		 * @throws Exception  expcion En el server Socket
		 */
		public boolean startServer() throws Exception {
			boolean started =  false;
			try {
				serverSocket = new ServerSocket(port);
				started = true;
				FrameServer.setVisible(false);
				FrameServerStatus.setVisible(true);
				txServerStatus.setText("Server waiting for Clients on port " + port + ".");
				
				 
			} catch (IOException e) {

				System.out.println(" server Error could not bind to port" + e.getLocalizedMessage());
			}
			return started;
		}
		
		/**
		 * Ena vez se lanza hilo va estar en un boucle escuchando a las peticiones de conexion de los clientes usando el metodo accept  
		 * en cada conexion establicida se crea un objeto de la clase HandleClient  que recibe socket del cliente connectado 
		 * y se añade a una lista de tipo HandleClient
		 * 
		 */
		 
		@Override
		public void run() {
			while(true) {
				
				
				try {
					Socket clientSocket = serverSocket.accept();
					HandleClient  handleClient = new HandleClient(clientSocket);
					clients.add(handleClient);
 
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				}
		}

		

 
	
		
		/**
		 * La clase HandleClient extiende de la clase Thread 
		 * @author malik
		 *
		 */
		
		class HandleClient extends Thread {
			String userName = "";
			BufferedReader br;
			PrintWriter pw;
			Socket socketClient;
			
			 
			/**
			 * Constructor HandleClient recibe socket del cliente connectado  se recupera los flujos de lectura y escritura  se lee nombre del usario enviado desde cliente
			 * y se añade a una lista y se lanza  un hilo 
			 * @param socketClient
			 * @throws Exception
			 */
			
			public HandleClient(Socket socketClient) throws Exception {
				// get input and output  
				br = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
				pw = new PrintWriter(socketClient.getOutputStream(), true);
				// read username
				userName = br.readLine();
				users.add(userName);  			
				txServerStatus.append("\n"+ userName + "... Connected");
				start();
			}
			
			/**
			 * Metodo BroadCast que recibe dos paremtros que son nombre del usario y el mensaje  recorremos la lista de handleClient  se envia el mensaje y nombre 
			 * del usario a todos los clientes connectados llamando el metodo sendMessage  menos usario mismo llmando el metodo getUserName
			 * @param user  nombre de usario
			 * @param message mensaje
			 */
			
			public void broadcast(String user, String message) {
				 
				for (HandleClient c : clients)
					if (!c.getUserName().equals(user))
						c.sendMessage(user, message);
			}
			
			
			
			/**
			 * Metodo sendMessage tiene dos parametros nombre usario y el mensaje  permite enviar mensaje usando flujo de escritura 
			 * recuperado en el contructor 
			 * @param userName
			 * @param msg
			 */

			public void sendMessage(String userName, String msg) {
				pw.println(userName + ": > " + msg);
			}
			
			/**
			 * Metodo getUsername devuelve un nombre del usario 
			 * @return
			 */
			public String getUserName() {
				return userName;
			}

			/**
			 * en el metodo run cuando se lanza el hilo va estar en un boucle esperando mensajes  una vez recibe un mensaje se muestra en el textarea del serverStatus
			 * y si el mensaje es igual logout  borramos el usario de la lista  y indicamos en textarea del serverstatus que usario de que se ha desconectado
			 * luego llamando metodo broadcast le pasamos nombre de usario y  el mensaje recebido 
			 */
			public void run() {
				
				String msg;
				try {
					while (true) {
						msg = br.readLine();
						if (!msg.equals("logout") || !msg.equals(" "))
						txServerStatus.append("\n" + userName +" > " + msg);
						 
						if (msg.equals("logout")) {
							txServerStatus.append("\n" + userName + ".. has disconnected");
							clients.remove(this);
							users.remove(userName);
							break;
						} 
						broadcast(userName, msg); 
					}  
				}  
				catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
			}  
		}  
		
			}
	
	
	

}
		
 
