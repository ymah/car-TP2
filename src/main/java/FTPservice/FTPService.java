package main.java.FTPservice;

import java.io.IOException;
import java.net.Socket;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;


public class FTPService {

	private FTPClient client;
	private Socket ds;
	
	
	public FTPService(){
		this.client = new FTPClient();
		try{
			this.client.connect("localhost",2121);
			this.client.login("mah", "toto");
			System.out.println("Start ok");
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	public String ls() {
		// TODO Auto-generated method stub
		try{
			
			String[] liste = this.client.listNames();
			System.out.println(liste.length);
			String res = "";
			for(int i = 0;i<liste.length;i++)
				res = res + " "+liste[i];
			System.out.println("RES----------------------------- "+res);
			return res;

			
		}catch(IOException e){
			
		}
		
		return "NULL";
	}
	
	public String pwd() {
		// TODO Auto-generated method stub
		try{
			this.client.pwd();
			return this.client.getReplyStrings()[0];
			
		}catch(IOException e){
			
		}
		
		return "NULL";
	}
	
	public void cd(String dir){
		try {
			this.client.cwd(dir);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("erreur");
		}

	}

}
