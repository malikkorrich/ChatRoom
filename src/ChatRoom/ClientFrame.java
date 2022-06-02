package ChatRoom;

import java.awt.Color;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


/**
 * Clase ClientFrame  es una clase de tipo JFrame  o ventana tiene Dos clases Client class y la MessagesThread class que extiende de la Clases Thread
 * ClientFrame tiene  otra ventana que ChatFrame
 * @author malik
 *
 */
 

public class ClientFrame   {
	
	private JFrame ClientFrame;
	private JTextField tfIP;
	private JTextField tfPort;
	private JTextField tfName;
	private JButton btnConnect;
	private JButton btnExit;

	/**
	 * Chat Frame Var
	 */
	private JTextArea taMsg;
	private JTextField tfMsg;
	private JFrame ChatFrame;
	
	
	/**
	 * Client var
	 */

	private Socket socket;					 
	private PrintWriter pw;
	private BufferedReader br;
	private JTextArea  taMessages;
	private JTextField tfInput;
	private Client client;
    
	
	
	 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientFrame window = new ClientFrame();
					window.ClientFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
		
		
		
		
	}

	/**
	 * Create the application.
	 */
	public ClientFrame() {
		initialize();
		initialize2();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		ClientFrame = new JFrame();
		ClientFrame.setResizable(false);
		ClientFrame.setTitle("Login");
		ClientFrame.setBounds(100, 100, 405, 285);
		ClientFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ClientFrame.getContentPane().setLayout(null);
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Login", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(0, 0, 403, 261);
		ClientFrame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel labelIP = new JLabel("IP Address");
		labelIP.setBounds(43, 38, 60, 22);
		panel.add(labelIP);
		
		tfIP = new JTextField();
		tfIP.setText("127.0.0.1");
		tfIP.setBounds(122, 32, 167, 34);
		panel.add(tfIP);
		tfIP.setColumns(10);
		
		JLabel labelPort = new JLabel("Port");
		labelPort.setBounds(43, 83, 60, 22);
		panel.add(labelPort);
		
		tfPort = new JTextField();
		tfPort.setText("5555");
		tfPort.setColumns(10);
		tfPort.setBounds(122, 77, 167, 34);
		panel.add(tfPort);
		
		JLabel labelName = new JLabel("Name");
		labelName.setBounds(43, 128, 60, 22);
		panel.add(labelName);
		
		tfName = new JTextField();
		tfName.setText("malik");
		tfName.setColumns(10);
		tfName.setBounds(122, 122, 167, 34);
		panel.add(tfName);
	 
		 btnConnect = new JButton("Connect");
		 /**
		  * En el  Evento click del  Button Connect  se crea un objeto de la clase cliente que reciber ip,puerto , nombe de usario luego , se llama al metodo connectClient que permite
		  * estableze la conexion con el servidor si no   mostramos un messgae dialgo que se ha producido un error usando JoptionPane
		  */
		 btnConnect.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
				   client = new Client(tfIP.getText(),Integer.parseInt(tfPort.getText()),tfName.getText());
				 if(client.connectClient() != true) 
			 JOptionPane.showMessageDialog(ClientFrame, "server down or try check your ip and port .","Connection Error", JOptionPane.ERROR_MESSAGE);
				 
				 
				 
				 
		 	}
		 });
		btnConnect.setBounds(221, 179, 89, 41);
		panel.add(btnConnect);
		
	
		 
		 btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {

			/**
			 *  En el  Evento click del  Button se se destruye la ventana ClientFrame
			 *  
			 */
			public void actionPerformed(ActionEvent arg0) {
	int dialogResult = JOptionPane.showConfirmDialog(ClientFrame, "Are you sure you want to Exit", "Client Frame",  JOptionPane.YES_NO_OPTION);
				
				//yes button
				if(dialogResult == 0) {
					 
					ClientFrame.dispose();

				} else {
				  
				} 
				
			}
		});
		btnExit.setBounds(94, 179, 89, 41);
		panel.add(btnExit);
	}
	
	
	
	
	
	private void initialize2() {
		ChatFrame = new JFrame();
		ChatFrame.setBounds(100, 100, 435, 489);
		ChatFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ChatFrame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 426, 447);
		ChatFrame.getContentPane().add(panel);
		panel.setLayout(null);
		
		  taMsg = new JTextArea();
		taMsg.setBackground(Color.WHITE);
		taMsg.setEditable(false);
		taMsg.setBounds(0, 0, 424, 347);
		panel.add(taMsg);
		
		tfMsg = new JTextField();
		tfMsg.setBounds(10, 358, 293, 31);
		panel.add(tfMsg);
		tfMsg.setColumns(10);
		
		JButton btnSendMsg = new JButton("Send");
		btnSendMsg.addActionListener(new ActionListener() {
			/**
			 *  En el  Evento click del button send en la ventana de chat  se recupera el mensaje desde textarea  y usando flujo de escritura PrintWriter
			 *  se manda mensaje al servidor 
			 */
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String chatMessage =tfMsg.getText();
				System.out.println(chatMessage);
				 
				 pw.println(chatMessage);
				 tfMsg.setText(null);
			}
			
		});
		btnSendMsg.setBounds(313, 358, 81, 30);
		panel.add(btnSendMsg);
		
		
		/**
		 *  se crea un objeto  Jmenubar y se aade ala ventana ChatFrame 
		 *  se crea Un objeto Jmenu y se añade al objeto Jmenubar
		 *  se crea un objeto JmenuItmen (Logout) y se anade al objeto Jmenu
		 */
		
		JMenuBar menuBar = new JMenuBar();
		ChatFrame.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Menu");
		menuBar.add(mnNewMenu);
		
	
		JMenuItem mntmNewMenuItem2 = new JMenuItem("Logout");
		
		
		mnNewMenu.add(mntmNewMenuItem2);
		
		
		/**
		 * En el  Evento click sobre el menu logout  se envia un mensjae logout al servidor una vez recibido y se quita usario de la lista users
		 * y se quita tambien de la lista los clientes connecteado se indica en el serverStatus que el usario se desconectado
		 */
		mntmNewMenuItem2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				 
				int dialogResult = JOptionPane.showConfirmDialog(ChatFrame, "Are you sure you want to log out", "Title on Box",  JOptionPane.YES_NO_OPTION);
				
				//yes button
				if(dialogResult == 0) {
					pw.println("logout");
					ChatFrame.setVisible(false);
					 ClientFrame.setVisible(true);

				} else {
				  
				} 
				
								 
			}
			
		});
		
	}

	
	
	
	/**
	 * Clase Client tiene un metodo connectClient , otra clase (inner) MessagesThread que extiende de la clase Thread 
	 * @author malik
	 *
	 */
	
	
	public class Client  {

  
		private Socket socket;					 
		private String server, username;	 
		private int port;					 
 
      

  
		
		Client(String server, int port, String username) {
			this.server = server;
			this.port = port;
			this.username = username;
			 
		}
		
		
		/**
		 * Metodo ConnectClient es metodo booleano permite de crear client socket y establecer la conexion con el servidor  si se establece la conxion se oculta la venta
		 * de clientFrame y se muestra la ventana del ChatFrame se recupera flujo del client socket y se manda nombre del usuario  del cliente al servidor usando flujo de escritura PrintWriter 
		 * y se crea un objeto anonymous de la clase MessagesThread que extende de la clase Thread y lanzamos hilo en un boucle que va esperando a los mensajes cada vez que lea un mensaje se añade
		 * textArea en la ventana de chat  
		 * @return Boolean  
		 */
		
		public boolean connectClient() {
			 boolean connected = false;
		 
				
				try {
					 socket = new Socket(server,port);
					 ClientFrame.setVisible(false);
					 ChatFrame.setVisible(true);
					 ChatFrame.setTitle("Client :"+ username.toUpperCase());
					 
					 connected = true;
					System.out.println( "Connection accepted " + socket.getInetAddress() + ":" + socket.getPort());
			
					
					br = new BufferedReader( new InputStreamReader( socket.getInputStream()) ) ;
			        pw = new PrintWriter(socket.getOutputStream(),true);
			        pw.println(username);  // send name to server
				 
			        new MessagesThread().start(); 
			 
				} catch (IOException e) {
					System.out.println("Cannot connect to server please check your ip or port");
					
				}
				
		 
			return connected;
		}
		
		/**
		 * Clase MessagesThread  que extiende de la clase Thread implementa el metodo run que sirver para recuperar los mensajes enviados desde servidor 
		 * @author malik
		 *
		 */
		
		class  MessagesThread extends Thread {
	        public void run() {
	            String line;
	            try {
	                while(true) {
	                    line = br.readLine();
	                    taMsg.append(line + "\n");
	                } // end of while
	            } catch(Exception ex) {}
	        }
	    }

		
		
 
		
	}



	
	
	
}
