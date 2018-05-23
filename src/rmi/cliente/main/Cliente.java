package rmi.cliente.main;

import javax.swing.JFrame;

public class Cliente
{
	public static void main(String []args)
	{
		Visual v;
		
		v = new Visual(args);
		
		v.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
