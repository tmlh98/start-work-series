package xyz.tmlh.tomcat1;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

import xyz.tmlh.tomcat2.Servlet;

/**
 * <p>
 * Title: TestServer
 * </p>
 * <p>
 * Description: 服务器请求部分
 * </p>
 * 
 * @author TianXin
 * @created 2018年11月24日 下午4:33:38
 * @modified [who date description]
 * @check [who date description]
 */
public class TestServer {
	
	private static String ROOT_URL = System.getProperty("user.dir") + "\\" + "WEBContent"; 
	
	private static String uri = ""; 
	
	private static Properties prop; 
	
	static {
		prop = new Properties();
		try {
			InputStream inStream = new FileInputStream(ROOT_URL + "\\conf.properties");
			prop.load(inStream);
		} catch (IOException e) {
			 e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = null;
		Socket socket = null;
		InputStream iStream = null;
		OutputStream oStream = null;
		try {
			serverSocket = new ServerSocket(8080);
			System.out.println("server start....");
			while(true) {
				socket = serverSocket.accept();
				iStream = socket.getInputStream();
				oStream = socket.getOutputStream();
				
				parse(iStream);
				
				if(uri.indexOf(".")!= -1) {
					sendStaticResource(oStream);
				}else {
					sendDynamicResource(iStream,oStream);
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			
			if(oStream != null) {
				oStream.close();
				oStream = null;
			}
			if(iStream != null) {
				iStream.close();
				iStream = null;
			}
			if(socket != null) {
				socket.close();
				socket = null;
			}
		}
		
		
	}

	/**
	 * 发送服务器动态资源
	 */
	private static void sendDynamicResource(InputStream iStream, OutputStream oStream) {
		try {
			
			if(prop.containsKey(uri)) {
				oStream.write("HTTP/1.1 200 ok\n".getBytes());
				oStream.write("Content-Language: zh-CN\n".getBytes());
				oStream.write("Content-Type: text/html;charset=UTF-8\n".getBytes());
				oStream.write("Server: Apache-Coyote/1.1\n".getBytes());
				oStream.write("\n\n".getBytes());
				String classPath = prop.getProperty(uri);
				Class<?> clazz = Class.forName(classPath);
				try {
					Servlet servlet = (Servlet) clazz.newInstance();
					servlet.init();
					servlet.service(iStream, oStream);
					servlet.destory();
				} catch (InstantiationException | IllegalAccessException e) {
					 e.printStackTrace();
				}
			}else {
				oStream.write("HTTP/1.1 404 not found\n".getBytes());
				oStream.write("Content-Language: zh-CN\n".getBytes());
				oStream.write("Content-Type: text/html;charset=UTF-8\n".getBytes());
				oStream.write("Server: Apache-Coyote/1.1\n".getBytes());
				oStream.write("\n\n".getBytes());
				oStream.write("404".getBytes());
			}
		} catch (Exception e) {
			 e.printStackTrace();
		}
		 
	}

	/**
	 * 发送服务器静态资源
	 */
	private static void sendStaticResource(OutputStream oStream) {
		FileInputStream fileInputStream = null;
		try {
			File file = new File(ROOT_URL, uri);
			if(file.exists()) {
				fileInputStream = new FileInputStream(file);
				oStream.write("HTTP/1.1 200 ok\n".getBytes());
				oStream.write("Content-Language: zh-CN\n".getBytes());
				oStream.write("Content-Type: text/html;charset=UTF-8\n".getBytes());
				oStream.write("Server: Apache-Coyote/1.1\n".getBytes());
				oStream.write("\n\n".getBytes());
				byte[] bt = new byte[1024];
				int read = -1;
				while((read = fileInputStream.read(bt)) != -1 ) {
					oStream.write(new String(bt , 0 ,read).getBytes());
				}
				
			}else {
				oStream.write("HTTP/1.1 404 not found\n".getBytes());
				oStream.write("Content-Language: zh-CN\n".getBytes());
				oStream.write("Content-Type: text/html;charset=UTF-8\n".getBytes());
				oStream.write("Server: Apache-Coyote/1.1\n".getBytes());
				oStream.write("\n\n".getBytes());
				oStream.write("<h1>404</h1>".getBytes());
			}
			oStream.flush();
		} catch (Exception e) {
			 e.printStackTrace();
		}finally {
			if(fileInputStream != null) {
				try {
					fileInputStream.close();
					fileInputStream = null;
				} catch (IOException e) {
					 e.printStackTrace();
				}
			}
		}
		 
	}

	/**
	 * 解析浏览器请求
	 */
	private static void parse(InputStream iStream) {
		StringBuffer stringBuffer = new StringBuffer(2048);
		byte[] bs = new byte[2048];
		
		try {
			int len = iStream.read(bs);
			for (int i = 0; i < len; i++) {
				stringBuffer.append((char)bs[i]);
			}
			uri = parseUri(stringBuffer.toString());
			System.out.println("获取浏览器请求: " + ROOT_URL + "/" + uri);
		} catch (IOException e) {
			 e.printStackTrace();
		}
		 
	}

	/**
	 * 解析请求头
	 */
	private static String parseUri(String str) {
		
		int index1 = str.indexOf(" ");
		if(index1 != -1) {
			int index2 = str.indexOf(" ", index1 + 1);
			if(index2 != -1) {
				return str.substring(index1 + 2, index2);
			}
		}
		return "";
	}
	
}
