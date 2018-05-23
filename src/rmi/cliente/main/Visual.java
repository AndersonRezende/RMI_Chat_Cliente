package rmi.cliente.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.List;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.RemoteException;
import rmi.chat.msg.Mensagem;

import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

	
	
public class Visual extends JFrame
{
	private String nome = "User";
	private Mensagem msg;
	private int port = 1090;
	private String ip = "localhost";
	private Scanner write = new Scanner(System.in);
	private int itTamanhoFontTextArea = 20;
	private int itTipoFontTextArea = Font.PLAIN;
	private String stNomeFonte = "Serif";
	private int[] itTiposDaFonte = {Font.PLAIN,Font.BOLD,Font.ITALIC,Font.ITALIC+Font.BOLD};
	private String[] stTiposDaFonte = {"Normal","Negrito","Itálico","Negrito e Itálico"};
	private String[] stTamanhosDaFonte = {"8","9","10","12","14","16","18","20","22","24","26","28","36","48","72"};
	private String[]stNomesDaFonte;
	private GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
	private Font[] fonts = e.getAllFonts();
	private Font ftTextArea = new Font(stNomeFonte, itTipoFontTextArea,itTamanhoFontTextArea);
	private String[] stUsuarios;
	private String stDestinatario = "todos";
			
	private Container container = getContentPane();
	private JButton btEnviar = new JButton("Enviar");
	private JTextField tfMensagem = new JTextField();					//Campo para enviar mensagem
	private JTextArea taMensagens = new JTextArea(5,5);					//Area onde ficará armazenada as mensagens
	private JPanel pnAreaMensagens = new JPanel();
	private JPanel pnAreaEnviarMensagem = new JPanel();
	private JPanel pnListaUsuarios = new JPanel();
	private JList ltTamanhoFonte = new JList(stTamanhosDaFonte);
	private JList ltTipoFonte = new JList(stTiposDaFonte);
	private JList ltNomeFonte;
	private JList ltListaUsuarios;	// = new JList();
	private JComboBox cbListaUsuarios;
	private JLabel lbDestinatario = new JLabel("Todos");
	private JButton btAtualizarLista = new JButton("Atuailzar lista");
	
	private JMenuBar mbBar = new JMenuBar();
	private JMenu mnArquivo = new JMenu("Arquivo");
	private JMenu mnFormatar = new JMenu("Formatar");
	private JMenu mnFormatarTamanho = new JMenu("Tamanho da fonte");
	private JMenu mnFormatarTipo = new JMenu("Tipo da fonte");
	private JMenu mnFormatarNome = new JMenu("Fonte");
	private JMenu mnAjuda = new JMenu("Ajuda");
	private JMenuItem miSobre = new JMenuItem("Sobre");
	private JMenuItem miSair = new JMenuItem("Sair");
	
		
	
	public Visual(String []args)						//Construtor
	{	
		super("MSN RMI");
		
		if(args.length == 0)
			this.nome = (String) JOptionPane.showInputDialog(container, "Informe um nome de usuário", "Identificação", JOptionPane.INFORMATION_MESSAGE);
		else
			this.nome = args[0];
		
		if(args.length <= 1)
			this.ip = JOptionPane.showInputDialog(container,"Informe o ip","localhost");
		else
			this.ip = args[1];
		
		
		criarMenu();
		criarComponentes();
		configuraConexao();
			
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("2.png")));
		setJMenuBar(mbBar);
		setVisible(true);
		setSize(600,550);
	}
	
	
	private void criarMenu()	//----------------------Criação da barra de menu----------------------//
	{
		stNomesDaFonte = new String[fonts.length];
		for (int indice = 0; indice < fonts.length; indice++) 
		{
			stNomesDaFonte[indice] = fonts[indice].getFontName();
		}
		ltNomeFonte = new JList(stNomesDaFonte);
		JScrollPane jScrollPane = new JScrollPane();  
		jScrollPane.setViewportView(ltNomeFonte); 
		
		mbBar.add(mnArquivo);
		mbBar.add(mnFormatar);
		mbBar.add(mnAjuda);

		mnArquivo.setMnemonic('A');
		mnArquivo.add(miSair);
		mnFormatar.setMnemonic('F');
		mnFormatar.add(mnFormatarNome);
		mnFormatar.add(mnFormatarTamanho);
		mnFormatar.add(mnFormatarTipo);
		
		mnFormatarTamanho.setMnemonic('t');
		mnFormatarTamanho.add(ltTamanhoFonte);
		
		mnFormatarTipo.setMnemonic('i');
		mnFormatarTipo.add(ltTipoFonte);
		
		mnFormatarNome.setMnemonic('N');
		mnFormatarNome.add(jScrollPane);
		
		mnAjuda.setMnemonic('u');
		mnAjuda.add(miSobre);
		
		miSobre.setMnemonic('o');
		miSobre.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event) 
			{
				JOptionPane.showInternalMessageDialog(container, "MSN RMI é uma sala de bate papo virtual onde você pode \nse conectar e conversar com seus amigos!"
						+ "\n\nEquipe de desenvolvimento:\n"
						+ "Anderson Rezende\n"
						+ "Cícero Adson\n"
						+ "Giulian Victor\n"
						+ "Hiuri Medley\n"
						+ "Vitor Hugo\n",
						"Sobre MSN RMI", JOptionPane.INFORMATION_MESSAGE, null);
			}
			
		});
		
		miSair.setMnemonic('S');
		miSair.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent event) 
			{	dispose();	}
			
		});	
		
		ltTamanhoFonte.setSelectedIndex(7);
		ltTamanhoFonte.addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent event) 
			{
				itTamanhoFontTextArea = Integer.parseInt(stTamanhosDaFonte[ltTamanhoFonte.getSelectedIndex()]);
				ftTextArea = new Font(stNomeFonte,itTipoFontTextArea,itTamanhoFontTextArea);
				taMensagens.setFont(ftTextArea);
			}	
		});
		
		
		ltTipoFonte.setSelectedIndex(0);
		ltTipoFonte.addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent event) 
			{
				itTipoFontTextArea = itTiposDaFonte[ltTipoFonte.getSelectedIndex()];
				ftTextArea = new Font(stNomeFonte,itTipoFontTextArea,itTamanhoFontTextArea);
				taMensagens.setFont(ftTextArea);
			}
			
		});
		
		ltNomeFonte.addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent event) 
			{
				int indice = ltNomeFonte.getSelectedIndex();
				stNomeFonte = stNomesDaFonte[indice];
				ftTextArea = new Font(stNomeFonte,itTipoFontTextArea,itTamanhoFontTextArea);
				taMensagens.setFont(ftTextArea);
			}});
		
		

		
	}	//----------------------Fim da criação dos menus----------------------//
		
	
	private void criarComponentes()	//----------------------Criação dos frames e dos componentes----------------------//
	{
		pnAreaEnviarMensagem.setLayout(new BorderLayout());
		pnAreaMensagens.setLayout(new BorderLayout());
		
		taMensagens.setEditable(false);
		taMensagens.setLineWrap(true);
		taMensagens.setFont(ftTextArea);
		tfMensagem.setToolTipText("Digite sua mensagem");
		tfMensagem.addKeyListener(new KeyAdapter()										//Adiciona ação para enviar a mensagem com a tecla enter
		{
			public void keyReleased(KeyEvent event)
			{
				if(event.getKeyCode() == KeyEvent.VK_ENTER)
				{
					try 
					{
						if(!tfMensagem.getText().equals(""))
							msg.enviarMensagem(nome, tfMensagem.getText(), stDestinatario);
						tfMensagem.setText("");
					} 
					catch (RemoteException e) 
					{	e.printStackTrace();	}
				}
			}
		});
		btEnviar.addActionListener(new ActionListener()									//Adiciona o evento pra enviar a mensagem pelo botão
		{
			@Override
			public void actionPerformed(ActionEvent event) 
			{
				try 
				{
					if(!tfMensagem.getText().equals(""))
						msg.enviarMensagem(nome, tfMensagem.getText(), stDestinatario);
					tfMensagem.setText("");
				} 
				catch (RemoteException e) 
				{	e.printStackTrace();	}
			}
		});
		
		
		JScrollPane spAreaMensagens = new JScrollPane(taMensagens,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); //Adiciona barra de rolagem ao textarea
		
		pnAreaMensagens.add(spAreaMensagens, BorderLayout.CENTER);
		pnAreaMensagens.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"Chat",TitledBorder.LEFT,TitledBorder.ABOVE_TOP,new Font("Serif", Font.PLAIN,20),Color.black));
		
		pnAreaEnviarMensagem.add(tfMensagem, BorderLayout.CENTER);
		pnAreaEnviarMensagem.add(btEnviar, BorderLayout.EAST);
		pnAreaEnviarMensagem.setBorder(BorderFactory.createTitledBorder("Mensagem"));
		
		
		pnListaUsuarios.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"Usuários",TitledBorder.LEFT,TitledBorder.ABOVE_TOP,new Font("Serif", Font.PLAIN,20),Color.black));
		pnListaUsuarios.setLayout(new BorderLayout());
		//pnListaUsuarios.add(ltListaUsuarios);
		//------------------------------------------------------
		
		container.setLayout(new BorderLayout());
		container.add(pnAreaEnviarMensagem,BorderLayout.SOUTH);
		container.add(pnListaUsuarios, BorderLayout.WEST);
		container.add(pnAreaMensagens, BorderLayout.CENTER);
	}	//----------------------Fim da criação dos frames e componentes----------------------//
	
	
		
	private void configuraConexao() 											//Configura a conexão e funciona via linha de comando
	{
		try
		{
			msg = (Mensagem) Naming.lookup("rmi://"+ip+":"+port+"/MensagemImpl");
			
			while(true)
			{
				boolean aux = msg.adicionarUsuario(nome);
				if(aux = true)
					break;
				else
					nome = (String) JOptionPane.showInputDialog(container, "Informe um nome de usuário", "Identificação", JOptionPane.INFORMATION_MESSAGE);
			}
			
			Thread threadLerMensagem = new Thread(new Runnable() 				//Cria uma thread para ficar lendo mensagens do objeto remoto
			{
				private int ultimaMensagemLida = 0;//msg.receberQuantidadeMensagens();
				
				@Override
				public void run() 
				{
					try 
					{
						while(true)
						{
							int quantidade = msg.receberQuantidadeMensagens(nome);
							if (ultimaMensagemLida < quantidade)
							{
								int aux = ultimaMensagemLida;
								for(int i = aux; i < quantidade; i++)
								{
									System.out.println(msg.receberMensagem(nome, i));
									taMensagens.setText(taMensagens.getText() + msg.receberMensagem(nome, i));
									taMensagens.setCaretPosition(taMensagens.getText().length());
								}
								ultimaMensagemLida = quantidade;
							}
						}
					} 
					catch (RemoteException e) 
					{
						e.printStackTrace();
						JOptionPane.showMessageDialog(container, "Erro: "+e.toString(), "Erro", JOptionPane.ERROR_MESSAGE, null);
					}
				}
			});
			threadLerMensagem.start();

				
			Thread threadEnviarMensagem = new Thread(new Runnable() 		//Cria uma thread para enviar mensagens para o objeto remoto
			{
					
				@Override
				public void run() 
				{
					try 
					{
						while(true)
							msg.enviarMensagem(nome,""+write.nextLine(), stDestinatario);
					} 
					catch (RemoteException e) 
					{	e.printStackTrace();	}
				}
			});
			threadEnviarMensagem.start();
			
			try 
			{
					stUsuarios = msg.getListaUsuarios();
					ltListaUsuarios = new JList(stUsuarios);
					ltListaUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					
					//ltListaUsuarios.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
					
					pnListaUsuarios.add(ltListaUsuarios, BorderLayout.CENTER);
					pnListaUsuarios.add(lbDestinatario, BorderLayout.SOUTH);
					pnListaUsuarios.add(btAtualizarLista, BorderLayout.NORTH);
					ltListaUsuarios.addListSelectionListener(new ListSelectionListener()
					{
						@Override
						public void valueChanged(ListSelectionEvent event) 
						{
							if(ltListaUsuarios.getSelectedIndex() >= 0)
							{

								stDestinatario = stUsuarios[ltListaUsuarios.getSelectedIndex()];
								lbDestinatario.setText("Para: "+stDestinatario);
							}
						}
					});		

					btAtualizarLista.addActionListener(new ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent event) 
						{
							try 
							{
								stUsuarios = new String[msg.getListaUsuarios().length];
								stUsuarios = msg.getListaUsuarios();
								for(int indice = 0; indice < stUsuarios.length; indice++)
									if(stUsuarios[indice].equals(nome))
										stUsuarios[indice] = "todos";
								ltListaUsuarios.setListData(stUsuarios);
							} 
							catch (RemoteException e) 
							{	e.printStackTrace();	}
						}
						
					});
					
					Thread threadChecarClientes = new Thread(new Runnable() 		
					{
						@Override
						public void run() 
						{
							while(true)
							{

								try 
								{
									stUsuarios = new String[msg.getListaUsuarios().length];
									stUsuarios = msg.getListaUsuarios();
									for(int indice = 0; indice < stUsuarios.length; indice++)
										if(stUsuarios[indice].equals(nome))
											stUsuarios[indice] = "todos";
									ltListaUsuarios.setListData(stUsuarios);
									
									Thread.sleep(1000);
								} 
								catch (Exception e) 
								{
									e.printStackTrace();
								}
							}
						}
					});
					threadChecarClientes.start();
					
			} 
			catch (RemoteException e) 
			{	e.printStackTrace();	}
			
		}
		catch(Exception e)
		{	
			e.printStackTrace();
			JOptionPane.showMessageDialog(container, "Foram encontrados os seguintes erros durante a execução do programa: "
					+ "\n"+e.getMessage().replace(";", "\n")+""
							+ "\n\nO programa precisa ser reiniciado!", "Erro", JOptionPane.ERROR_MESSAGE, null);
			dispose();
		}
	}
}