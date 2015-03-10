package main.java.com.ftpRessource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import main.java.FTPservice.FTPService;
import main.java.serverFTP.RequestFTP;



@Path("/serverFTP")
public class RequestFTPResource {

	private FTPService ftp;
	
	
	public RequestFTPResource(){
		this.ftp = new FTPService();
	}
	
	/**
	 *  : retourne la liste des fichiers pour un compte
	 * 
	 */
	@GET
	@Path("ls")
	public String ls(){
		return this.ftp.ls();
	}
	
	
	
}
