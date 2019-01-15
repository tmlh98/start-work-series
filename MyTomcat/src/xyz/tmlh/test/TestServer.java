package xyz.tmlh.test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * <p>
 * Title: TestService
 * </p>
 * <p>
 * Description:客户端发送请求！服务端接受并响应出去
 * </p>
 * 
 * @author TianXin
 * @created 2018年11月24日 下午4:03:54
 * @modified [who date description]
 * @check [who date description]
 */
public class TestServer {
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = null;
		OutputStream os = null;
		Socket socket = null;
		try {
			serverSocket = new ServerSocket(8080);
			while (true) {
				socket = serverSocket.accept();//接受浏览器请求
				os = socket.getOutputStream();
				
				os.write("HTTP/1.1 200 ok\n".getBytes());
				os.write("Content-Language: zh-CN\n".getBytes());
				os.write("Content-Type: text/html;charset=UTF-8\n".getBytes());
				os.write("Server: Apache-Coyote/1.1\n".getBytes());
				StringBuffer sBuffer = new StringBuffer();
				sBuffer.append("\n\n");
				sBuffer.append("<!DOCTYPE html>");
				sBuffer.append("<html lang=\"en\">");
				sBuffer.append("<head><meta charset=\"UTF-8\">");
				sBuffer.append("</head><body>");
				sBuffer.append("<p>目标：查询数据库中的字段，然后转换成 JSON 格式的数据，返回前台。</p>");
				sBuffer.append("</body></html>");
				os.write(sBuffer.toString().getBytes());
				os.flush();
			}
		} catch (IOException e) {
			 e.printStackTrace();
		}finally {
			if(os != null) {
				os.close();
				os = null;
			}
			if(socket != null) {
				socket.close();
				socket = null;
			}
		}
				
	}
}
