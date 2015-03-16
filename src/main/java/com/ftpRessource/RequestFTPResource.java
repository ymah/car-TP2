package main.java.com.ftpRessource;

import java.awt.PageAttributes.MediaType;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import main.java.FTPservice.FTPService;
import javax.ws.rs.core.MediaType;


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
		String[] ls = this.ftp.ls().split(",");
		for(String n : ls){
			String[] tmp=n.split(" ");
			if(tmp.length==2){
				if(tmp[1].equals("-d")){
					corps= corps + "<li>"+"<a href="+BASE_URL+"cd/"+tmp[0]+">"+tmp[0]+"</a>"+"</li>";
				}
				else{
					corps= corps + "<li>"+"<a href="+BASE_URL+"get/"+tmp[0]+">"+tmp[0]+"</a>"+"</li>";
				}
			}
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
	@Produces()
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

	private String formUpload(){
		String res = "<html>"; 
		res += "<body>";	
		res += "<h1>Chargement d'un fichier</h1>";

		res += "<form action=\"rest/api/serverFTP/uploadFile\" method=\"post\" enctype=\"multipart/form-data\">";

		res+="<p>";
		res+="Selectionner un fichier : <input type=\"file\" name=\"file\" size=\"45\" />";
		res+="</p>";

		res+="<input type=\"submit\" value=\"Charger\" />";
		res+="</form>";

		res+="</body>";
		res+="</html>";
		return res;
	}
	
	@GET
	@Produces("text/html")
	@Path("/upload/")
	public String upload(){
		return this.formUpload();
	}
	
	@POST
	@Path("/uploadFile")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(
		@FormDataParam("file") InputStream uploadedInputStream,
		@FormDataParam("file") FormDataContentDisposition fileDetail) {
 
		String uploadedFileLocation = "d://uploaded/" + fileDetail.getFileName();
 
		// save it
		writeToFile(uploadedInputStream, uploadedFileLocation);
 
		String output = "File uploaded to : " + uploadedFileLocation;
 
		return Response.status(200).entity(output).build();
 
	}
 
	// save uploaded file to new location
	private void writeToFile(InputStream uploadedInputStream,
		String uploadedFileLocation) {
 
		try {
			OutputStream out = new FileOutputStream(new File(
					uploadedFileLocation));
			int read = 0;
			byte[] bytes = new byte[1024];
 
			out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch (IOException e) {
 
			e.printStackTrace();
		}
 
	}
}
