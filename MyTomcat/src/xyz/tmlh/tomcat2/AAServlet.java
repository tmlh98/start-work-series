 package xyz.tmlh.tomcat2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AAServlet implements Servlet {

	@Override
	public void init() {
		System.out.println("AAServlet's init()...");

	}
	
	@Override
	public void service(InputStream inputStream, OutputStream outputStream) throws IOException {
		outputStream.write("AAServlet's service()...".getBytes());
		System.out.println("AAServlet's service()...");
	}

 

	@Override
	public void destory() {
		System.out.println("AAServlet's destory()...");

	}




}
