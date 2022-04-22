package de.michaelkuerbis.presenter.servlets;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.michaelkuerbis.presenter.rest.SettingsREST;

@MultipartConfig
public class MediaServlet extends HttpServlet {

	private final static Logger log = LogManager.getLogger(MediaServlet.class);
	
	private static final long serialVersionUID = -8435883813255134209L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Part filePart = request.getPart("file");
		String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString().toLowerCase().replace(' ',
				'_');
		InputStream fileContent = filePart.getInputStream();
		String path = request.getServletContext().getRealPath("/WEB-INF/media");
		if (fileName.endsWith("aac") || fileName.endsWith("mp4") || fileName.endsWith("mp3") || fileName.endsWith("wav")
				|| fileName.endsWith("webm")) {
			Files.copy(fileContent, Paths.get(path + File.separator + fileName), StandardCopyOption.REPLACE_EXISTING);
		} else {
			response.setStatus(400);
		}
		log.debug(path);
		fileContent.close();
		// ... (do your job here)
	}

}
