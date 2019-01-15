/*
 * $Id: MyScoket.java, 2018年11月23日 上午9:31:34 TianXin Exp $
 * 
 * Copyright (c) 2018 Vnierlai Technologies Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by Vnierlai or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
 package xyz.tmlh.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * <p>
 * Title: MyScoket
 * </p>
 * <p>
 * Description: 模仿浏览器向服务端（www.baidu.com）发送请求并接受响应数据
 * </p>
 * 
 * @author TianXin
 * @created 2018年11月23日 上午9:31:34
 * @modified [who date description]
 * @check [who date description]
 */
public class MySocket {
    @SuppressWarnings("unused")
    public static void main(String[] args) {
        Socket socket = null;
        InputStream is = null;
        OutputStream os = null;
        try {
            socket = new Socket("www.baidu.com", 80);
            
            is = socket.getInputStream();
            os = socket.getOutputStream();
            
            os.write("GET / HTTP/1.1\n".getBytes("UTF-8"));
            os.write("Host: baidu.com\n".getBytes("UTF-8"));
            os.write("\n".getBytes("UTF-8"));
            
            byte[] bs = new byte[1024];
            int read = 0;
            while((read = is.read(bs)) != -1) {
                System.out.print(new String(bs));
            }
        } catch (IOException e) {
             e.printStackTrace();
        }finally {
            try {
                os.close();
            } catch (IOException e) {
                 e.printStackTrace();
            }
            try {
                is.close();
            } catch (IOException e) {
                 e.printStackTrace();
            }
            try {
                socket.close();
            } catch (IOException e) {
                 e.printStackTrace();
            }
        }
    }

}
