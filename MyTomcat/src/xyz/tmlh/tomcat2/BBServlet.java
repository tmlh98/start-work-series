 package xyz.tmlh.tomcat2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BBServlet implements Servlet {

	@Override
	public void init() {
		System.out.println("BBServlet's init()...");

	}
	
	@Override
	public void service(InputStream inputStream, OutputStream outputStream) throws IOException {
		outputStream.write("BBServlet's service()...".getBytes());
		System.out.println("BBServlet's service()...");
	}

 

	@Override
	public void destory() {
		System.out.println("BBServlet's destory()...");

	}




}
