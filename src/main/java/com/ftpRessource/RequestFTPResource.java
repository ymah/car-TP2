package main.java.com.ftpRessource;

import java.awt.PageAttributes.MediaType;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import main.java.FTPservice.FTPService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;



@Path("/serverFTP")
public class RequestFTPResource {

	private FTPService ftp;
	private static final String BASE_URL = "/rest/api/serverFTP/";
	private static final String FTP_SERVER = "<h1>FTP server</h1>";		
	public RequestFTPResource(){
		this.ftp = new FTPService();
	}

	/**
	 * print in the browser a web page presentation
	 * @return String
	 */ 
	@GET
	@Produces("text/html")
	@Path("/")
	public String presentation(){
		return this.corps();
	}

	private String corps() {
		String css="<style type=\"text/css\">"+
				"#home { width: 300px; margin: 0 auto; border-radius: 5px;background-color: #f6f6f6;}"+
				"#related_links { color: #333; padding: 1em; }"+
				"#cdup { text-decoration: none;}"+
				"#related_links ul { list-style: none; margin: 0; border: none;}"+
				"#related_links li {border-bottom: 1px solid #90bade; margin: 0;}"+
				"#related_links li a {display: block; padding: 5px 5px 5px 0.5em; background-color: #2175bc;color: #fff;text-decoration: none; width: 100%;}"+
				"html>body #related_links li a { width: auto;}"+
				"#related_links li a:hover { background-color: #2586d7; color: #fff;}"+
				"</style>";
		String head="<div id='home'><div id='related_links'>" + FTP_SERVER +"<a href="+BASE_URL+"cdup>cdup</a>"+"<br/><a href=\"upload\">Charger un fichier</a><p>"+"path:"+this.ftp.pwd().substring(3)+"</p>"+"<ul>";
		String corps="";
		String[] ls = this.ftp.ls().split(",,");
		for(String n : ls){
			String[] tmp=n.split(" ");
			if(tmp.length==2 && tmp[1].equals("-dir-"))
				corps= corps + "<li>"+"<a href="+BASE_URL+"cd/"+tmp[0]+">"+tmp[0]+"/</a>"+"</li>";
			else
				corps= corps + "<li>"+"<a href="+BASE_URL+"get/"+tmp[0]+">"+tmp[0]+"</a>"+"</li>";
		}
		if(corps.equals("")){
			corps+="<li>Dossier vide</li>";
		}
		corps= corps + "</ul></div></div>";
		return css+head+corps;
	}

	/**
	 * get list of files
	 * @return String
	 */
	@GET
	@Produces("text/html")
	@Path("ls")
	public String ls(){
		return this.ftp.ls();
	}


	/**
	 * get current directory
	 * @return String
	 */
	@GET
	@Produces()
	@Path("pwd")
	public String pwd(){
		return this.ftp.pwd();
	}
	
	
	/**
	 * go to specific directory
	 * @param dossier
	 * @return String
	 */
	@GET
	@Produces("text/html")
	@Path("/cd/{dossier}")
	public String cd( @PathParam("dossier") String dossier ) {
		this.ftp.cd(dossier);
		return this.corps();
	}
	/**
	 * go to upper directory
	 * @return String
	 */
	@GET
	@Produces("text/html")
	@Path("cdup")
	public String cdup() {
		this.ftp.cdup();
		return this.corps();
	}


	private String formUpload(){
		String res = "<html>"; 
		res += "<body>";	
		res += "<h1>Chargement d'un fichier</h1>";


		res += "<form action=\"uploadFile\" method=\"post\" enctype=\"multipart/form-data\">";

		res+="<p>";
		res+="Selectionner un fichier : <input type=\"file\" name=\"file\" size=\"45\" />";
		res+="</p>";
		res+="<input type=\"text\" name=\"name\" />";
		res+="<input type=\"submit\" value=\"Charger\" />";
		res+="</form>";

		res+="</body>";
		res+="</html>";
		return res;
	}

	/**
	 * provide a form to upload file
	 * @return String
	 */
	@GET
	@Produces("text/html")
	@Path("upload")
	public String upload(){
		return this.formUpload();
	}

	/**
	 * Upload a specifi file
	 * @return String
	 */
	@POST
	//@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	@Path("uploadFile")
	public String up( @FormParam("file") String fichier,
			@FormParam("name") String name) {
		this.ftp.stor(fichier, name);
		//this.ftp.stor(file);
		return this.corps();
	}

	// save uploaded file to new location


	/**
	 * get back a file
	 * @param fichier
	 * @return {@link Response}
	 */
	@GET
	@Produces(javax.ws.rs.core.MediaType.APPLICATION_OCTET_STREAM)
	@Path("/get/{fichier}")
	public Response get(@PathParam("fichier") String fichier){
		return javax.ws.rs.core.Response.ok(this.ftp.get(fichier), javax.ws.rs.core.MediaType.APPLICATION_OCTET_STREAM).build();
	}

}
