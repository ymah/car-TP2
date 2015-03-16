package main.java.FTPservice;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.commons.net.ftp.FTPClient;

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
	/**
	 * Get list of files in the current directory
	 * @return String : List of files
	 */
	public String ls() {
		// TODO Auto-generated method stub
		try{

			String[] liste = this.client.listNames();
			String res = "";
			for(int i = 0;i<liste.length;i++)
				res = res + ",,"+liste[i];
			return res;


		}catch(IOException e){

		}

		return "NULL";
	}

	/**
	 * Return the current directory
	 * @return String 
	 */
	public String pwd() {
		try{
			this.client.pwd();
			return this.client.getReplyStrings()[0];

		}catch(IOException e){

		}

		return "NULL";
	}

	/**
	 * go to a specific directory
	 * @param dir
	 */
	public void cd(String dir){
		try {
			this.client.cwd(dir);
		} catch (IOException e) {
			System.out.println("erreur");
		}

	}
	/**
	 * go to upper directory
	 */
	public void cdup() {
		try{
			this.client.cdup();			
		}catch(IOException e){

		}
	}
	/**
	 * Stor in the FTP server the specific file
	 * @param uploadedInputStream
	 * @param name
	 */
	public void stor(String uploadedInputStream,String name) {
		try {
			this.client.storeFile(name,new ByteArrayInputStream(uploadedInputStream.getBytes()));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/**
	 * get back a specific file
	 * @param filename
	 * @return InputStream
	 * @throws IOException
	 */
	public InputStream get(String filename) {
		try {
			return this.client.retrieveFileStream(filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	} 


}
