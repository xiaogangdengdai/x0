package com.ssx.spa.common;

import com.loopj.android.http.AsyncHttpResponseHandler;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URLEncoder;
import java.util.logging.Logger;

public class SocketClient {
    private static final int MAX_TIMEOUT = 10;
    private static Logger logger = Logger.getLogger(SocketClient.class.getName());

    private SocketClient() {
    }

    public static void send(String host, int port, int timeout, String PagerID, boolean tmp) {
        Throwable th;
        Socket s = null;
        PrintWriter out = null;
        try {
            Socket s2 = new Socket(host, port);
            if (timeout > 10) {
                timeout = 10;
            }
            try {
                s2.setSoTimeout(timeout * 1000);
                OutputStream outputStream = s2.getOutputStream();
                StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\" ?> ");
                sb.append("<TinyBee>");
                sb.append("<Header>");
                sb.append("<Version>1</Version>");
                sb.append("<Name>CallSystem</Name>");
                sb.append("<Type>PagerPress</Type>");
                sb.append("</Header>");
                sb.append("<Content>");
                sb.append("<PagerID>" + PagerID + "</PagerID>");
                if (tmp) {
                    sb.append("<KeyValue>0001</KeyValue>");
                } else {
                    sb.append("<KeyValue>80001</KeyValue>");
                }
                sb.append("</Content>");
                sb.append("</TinyBee>");
                sb.append("\n\t");
                System.out.println(sb.toString());
                byte[] xmlbyte = sb.toString().getBytes(AsyncHttpResponseHandler.DEFAULT_CHARSET);
                outputStream.write(xmlbyte, 0, xmlbyte.length);
                outputStream.flush();
                if (s2 != null) {
                    try {
                        s2.close();
                    } catch (IOException e) {
                    }
                }
                if (out != null) {
                    out.close();
                }
            } catch (Exception e2) {
                s = s2;
                if (s != null) {
                    try {
                        s.close();
                    } catch (IOException e3) {
                    }
                }
                if (out != null) {
                    out.close();
                }
            } catch (Throwable th2) {
                th = th2;
                s = s2;
                if (s != null) {
                    try {
                        s.close();
                    } catch (IOException e4) {
                    }
                }
                if (out != null) {
                    out.close();
                }
                throw th;
            }
        } catch (Exception e5) {
            if (s != null) {
                try{
                    s.close();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
            if (out != null) {
                out.close();
            }
        } catch (Throwable th3) {
            th = th3;
            if (s != null) {
                try{
                    s.close();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
            if (out != null) {
                out.close();
            }
            th.printStackTrace();
        }
    }

    public static String sendAndGetReply(String host, int port, int timeout, String content) {
        Throwable th;
        String encode = "utf-8";
        Socket s = null;
        BufferedReader in = null;
        PrintWriter out = null;
        String line = null;
        try {
            content = URLEncoder.encode(content, encode);
            Socket s2 = new Socket(host, port);
            if (timeout > 10) {
                timeout = 10;
            }
            try {
                s2.setSoTimeout(timeout * 1000);
                BufferedReader in2 = new BufferedReader(new InputStreamReader(s2.getInputStream()));
                try {
                    PrintWriter out2 = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s2.getOutputStream())), true);
                    try {
                        out2.println(content);
                        line = in2.readLine();
                        if (s2 != null) {
                            try {
                                s2.close();
                            } catch (IOException e) {
                            }
                        }
                        if (out2 != null) {
                            out2.close();
                        }
                        if (in2 != null) {
                            try {
                                in2.close();
                            } catch (IOException e2) {
                            }
                        }
                    } catch (Exception e3) {
                        out = out2;
                        in = in2;
                        s = s2;
                        if (s != null) {
                            try {
                                s.close();
                            } catch (IOException e4) {
                            }
                        }
                        if (out != null) {
                            out.close();
                        }
                        if (in != null) {
                            try {
                                in.close();
                            } catch (IOException e5) {
                            }
                        }
                        line = URLEncoder.encode(line, encode);
                        return line;
                    } catch (Throwable th2) {
                        th = th2;
                        out = out2;
                        in = in2;
                        s = s2;
                        if (s != null) {
                            try {
                                s.close();
                            } catch (IOException e6) {
                            }
                        }
                        if (out != null) {
                            out.close();
                        }
                        if (in != null) {
                            try {
                                in.close();
                            } catch (IOException e7) {
                            }
                        }
                        throw th;
                    }
                } catch (Exception e8) {
                    in = in2;
                    s = s2;
                    if (s != null) {
                        s.close();
                    }
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                    }
                    line = URLEncoder.encode(line, encode);
                    return line;
                } catch (Throwable th3) {
                    th = th3;
                    in = in2;
                    s = s2;
                    if (s != null) {
                        s.close();
                    }
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                    }
                    throw th;
                }
            } catch (Exception e9) {
                s = s2;
                if (s != null) {
                    s.close();
                }
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
                line = URLEncoder.encode(line, encode);
                return line;
            } catch (Throwable th4) {
                th = th4;
                s = s2;
                if (s != null) {
                    s.close();
                }
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
                throw th;
            }
        } catch (Exception e10) {
            if (s != null) {
                try{
                    s.close();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
            if (out != null) {
                out.close();
            }
            if (in != null) {
                try{
                    in.close();
                }
                catch(Exception e){
                    e.printStackTrace();
                }

            }
            try{
                line = URLEncoder.encode(line, encode);
                return line;
            }
            catch(Exception e){
                e.printStackTrace();
            }
        } catch (Throwable th5) {
            th = th5;
            if (s != null) {
                try{
                    s.close();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
            if (out != null) {
                out.close();
            }
            if (in != null) {
                try{
                    in.close();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
            th.printStackTrace();
        }
        try {
            line = URLEncoder.encode(line, encode);
        } catch (UnsupportedEncodingException e11) {
        }
        return line;
    }

    public static void sendAndNoReply(String host, int port, int timeout, String content) {
        PrintWriter out;
        Throwable th;
        Socket s = null;
        PrintWriter out2 = null;
        try {
            content = URLEncoder.encode(content, "utf-8");
            Socket s2 = new Socket(host, port);
            if (timeout > 10) {
                timeout = 10;
            }
            try {
                s2.setSoTimeout(timeout * 1000);
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s2.getOutputStream())), true);
                out.println(content);
                if (s2 != null) {
                    try {
                        s2.close();
                    } catch (IOException e4) {
                    }
                }
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
                s = s2;
                if (s != null) {
                    try {
                        s.close();
                    } catch (IOException e2) {
                    }
                }
                if (out2 != null) {
                    out2.close();
                }
            } catch (Throwable th2) {
                th = th2;
                s = s2;
                if (s != null) {
                    try {
                        s.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                }
                if (out2 != null) {
                    out2.close();
                }
                throw th;
            }/*catch (Exception e5) {
                s = s2;
                if (s != null) {
                    s.close();
                }
                e5.printStackTrace();
            } catch (Throwable th3) {
                th = th3;

                s = s2;
                if (s != null) {
                    s.close();
                }
                //if (out) {
                //    out.close();
                //}
                th.printStackTrace();
            }*/
        } catch (Exception e6) {
            if (s != null) {
                try {
                    s.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
            if (out2 != null) {
                out2.close();
            }
        } catch (Throwable th4) {
            th = th4;
            if (s != null) {
                try {
                    s.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
            if (out2 != null) {
                out2.close();
            }
            th.printStackTrace();
        }
    }
}
