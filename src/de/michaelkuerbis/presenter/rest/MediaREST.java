package de.michaelkuerbis.presenter.rest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import org.json.simple.JSONArray;

@Path("/media")
public class MediaREST {

	@Context
	private HttpServletRequest webRequest;

	@GET
	@Path("/get/{file}")
	public Response getMedia(@PathParam("file") String fname) {
		if(fname == null || fname.isEmpty()) {
			return Response.status(Response.Status.BAD_REQUEST).entity("file must be provided").build();
		}
		final String file = fname.replaceAll("\\.", "").replaceAll("/", "");
		String path = webRequest.getServletContext().getRealPath("/WEB-INF/media");
		File f = new File(path+File.separator+file);
		if(f.exists()){
			StreamingOutput fileStream =  new StreamingOutput() 
	        {
	            @Override
	            public void write(java.io.OutputStream output) throws IOException, WebApplicationException 
	            {
	                try
	                {
	                    java.nio.file.Path path = Paths.get(webRequest.getServletContext().getRealPath("/WEB-INF/media")+File.separator+file);
	                    byte[] data = Files.readAllBytes(path);
	                    output.write(data);
	                    output.flush();
	                } 
	                catch (Exception e) 
	                {
	                    throw new WebApplicationException("File Not Found !!");
	                }
	            }
	        };
	        return Response
	                .ok(fileStream, MediaType.APPLICATION_OCTET_STREAM)
	                .header("content-disposition","attachment; filename = "+file)
	                .build();
		}else{
			return Response.status(Response.Status.BAD_REQUEST).entity("file does not exist").build();
		}
	}
	
	@DELETE
	@Path("/{file}")
	public Response delete(@PathParam("file") String fname) {
		if(fname == null || fname.isEmpty()) {
			return Response.status(Response.Status.BAD_REQUEST).entity("file must be provided").build();
		}
		final String file = fname.replaceAll("\\.", "").replaceAll("/", "");
		String path = webRequest.getServletContext().getRealPath("/WEB-INF/media");
		File f = new File(path+File.separator+file);
		if(f.exists()){
			if(f.delete()){
				return Response.ok().build();
			}
			else{
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("could not delete file").build();
			}
		}
		return Response.status(Response.Status.BAD_REQUEST).entity("file does not exist").build();
	}

	@GET
	@Path("/list")
	public Response getList() {
		JSONArray list = new JSONArray();
		String path = webRequest.getServletContext().getRealPath("/WEB-INF/media");
		File mediaDir = new File(path);
		for (File f : mediaDir.listFiles()) {
			String fileName = f.getName().toLowerCase();
			if (fileName.endsWith("aac") || fileName.endsWith("mp4") || fileName.endsWith("mp3")
					|| fileName.endsWith("wav") || fileName.endsWith("webm")) {
				list.add(fileName);
			}
		}
		return Response.ok(list.toJSONString()).build();
	}
}
