package xyz.tmlh.tomcat2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Servlet {
	
	/**
	 * 初始化
	 */
	public void init();
	
	/**
	 * 服务 
	 */
	public void service(InputStream inputStream , OutputStream outputStream) throws IOException;
	
	/**
	 * 销毁
	 */
	public void destory();
}
