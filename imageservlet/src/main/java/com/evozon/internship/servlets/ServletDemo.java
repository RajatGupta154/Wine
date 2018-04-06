package com.evozon.internship.servlets;

import java.io.*;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletDemo extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String filename = req.getParameter("fileName");
		File file = new File(filename);
		resp.setContentLength((int)file.length());
		FileInputStream in = new FileInputStream(file);
		OutputStream out = resp.getOutputStream();
		byte[] buffer = new byte[1024];
		int count;
		while ((count = in.read(buffer)) >= 0) {
			out.write(buffer, 0, count);
		}
		out.close();
		in.close();
	}
}