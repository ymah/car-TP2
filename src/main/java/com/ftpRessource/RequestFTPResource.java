package main.java.com.ftpRessource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import main.java.FTPservice.FTPService;



@Path("/serverFTP")
public class RequestFTPResource {

	private FTPService ftp;
	private static final String BASE_URL = "/rest/api/serverFTP/";
	private static final String FTP_SERVER = "<h1>FTP server</h1>";		

	public RequestFTPResource(){
		this.ftp = new FTPService();
	}

	@GET
	@Produces("text/html")
	@Path("/")
	public String presentation(){
		return this.corps();
	}

	private String corps() {
		String head=FTP_SERVER +"<a href="+BASE_URL+"cdup>cdup</a>"+"<p>"+"path:"+this.ftp.pwd().substring(3)+"</p>"+"<ul>";
		String corps="";
		String[] ls = this.ftp.ls().split(",,");
		for(String n : ls){
			System.out.println(n);
			String[] tmp=n.split(" ");
			if(tmp.length==2 && tmp[1].equals("-dir-"))
				corps= corps + "<li>"+"<a href="+BASE_URL+"cd/"+tmp[0]+">"+tmp[0]+"</a>"+"</li>";
			else
				corps= corps + "<li>"+"<a href="+BASE_URL+"get/"+tmp[0]+">"+tmp[0]+"</a>"+"</li>";
		}
		if(corps.equals("")){
			corps+="<li>Dossier vide</li>";
		}
		corps= corps + "</ul>";
		return head+corps;
	}
	/**
	 *  : retourne la liste des fichiers pour un compte
	 * 
	 */
	@GET
	@Produces("text/html")
	@Path("ls")
	public String ls(){
		return this.ftp.ls();
	}


	@GET
	@Produces("text/html")
	@Path("pwd")
	public String pwd(){
		return this.ftp.pwd();
	}
	@GET
	@Produces("text/html")
	@Path("/cd/{dossier}")
	public String cd( @PathParam("dossier") String dossier ) {
		this.ftp.cd(dossier);
		return this.corps();
	}
	@GET
	@Produces("text/html")
	@Path("cdup")
	public String cdup() {
		this.ftp.cdup();
		return this.corps();
	}


}
