package com.yunxin.utils.http;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class Http {
    /**
     * post 请求
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static InputStream postAsBinary(String url, String json ) throws Throwable {
        URL realurl = null;
        InputStream in = null;
        HttpURLConnection conn = null;
        try {

            realurl = new URL(url);
            conn = (HttpURLConnection) realurl.openConnection();
            conn.setConnectTimeout(8000);// 8秒
            conn.setReadTimeout(8000);// 8秒
            conn.setDoOutput(true);
            conn.setUseCaches(false);//不能使用缓存
            conn.setRequestProperty("Content-Type","application/json; charset=UTF-8");
            conn.setRequestProperty("accept","application/json");
            conn.setRequestMethod("POST");

            PrintWriter pw = new PrintWriter(conn.getOutputStream());
            pw.print(json);
            pw.flush();
            pw.close();
            in = conn.getInputStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            if(e instanceof SocketTimeoutException){
                SocketTimeoutException e1 = (SocketTimeoutException)e;
                throw e1;
            }
            e.printStackTrace();
        }
        return in;
    }


    public static InputStream postAsBinary(String url, String json, Map<String,String> header ) throws Throwable {
        URL realurl = null;
        InputStream in = null;
        HttpURLConnection conn = null;
        try {

            realurl = new URL(url);
            conn = (HttpURLConnection) realurl.openConnection();
            conn.setConnectTimeout(8000);// 8秒
            conn.setReadTimeout(8000);// 8秒
            conn.setDoOutput(true);
            conn.setUseCaches(false);//不能使用缓存
            conn.setRequestProperty("Content-Type","application/json; charset=UTF-8");
            conn.setRequestProperty("accept","application/json");
            for(String key: header.keySet()){
                conn.setRequestProperty(key,header.get(key));
            }
            conn.setRequestMethod("POST");

            PrintWriter pw = new PrintWriter(conn.getOutputStream());
            pw.print(json);
            pw.flush();
            pw.close();
            in = conn.getInputStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            if(e instanceof SocketTimeoutException){
                SocketTimeoutException e1 = (SocketTimeoutException)e;
                throw e1;
            }
            e.printStackTrace();
        }
        return in;
    }


    public static byte[] postAsBinary(String url, String json, String... headers ) throws Throwable {
        URL realurl = null;
        InputStream in = null;
        HttpURLConnection conn = null;
        try {
            Map<String,String> header = new HashMap<>();
            for(int i=0;i<headers.length;i=i+2){
                header.put(headers[i],headers[i+1]);
            }

            realurl = new URL(url);
            conn = (HttpURLConnection) realurl.openConnection();
            conn.setConnectTimeout(8000);// 8秒
            conn.setReadTimeout(8000);// 8秒
            conn.setDoOutput(true);
            conn.setUseCaches(false);//不能使用缓存
            conn.setRequestProperty("Content-Type","application/json; charset=UTF-8");
            conn.setRequestProperty("accept","application/json");
            for(String key: header.keySet()){
                conn.setRequestProperty(key,header.get(key));
            }
            conn.setRequestMethod("POST");

            PrintWriter pw = new PrintWriter(conn.getOutputStream());
            pw.print(json);
            pw.flush();
            pw.close();
            in = conn.getInputStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            if(e instanceof SocketTimeoutException){
                SocketTimeoutException e1 = (SocketTimeoutException)e;
                throw e1;
            }
            e.printStackTrace();
        }
        return streamToBytes(in);
    }

    public static String postAsString(String url, String json, String... headers ) throws Throwable {
        URL realurl = null;
        InputStream in = null;
        HttpURLConnection conn = null;
        try {
            Map<String,String> header = new HashMap<>();
            for(int i=0;i<headers.length;i=i+2){
                header.put(headers[i],headers[i+1]);
            }

            realurl = new URL(url);
            conn = (HttpURLConnection) realurl.openConnection();
            conn.setConnectTimeout(8000);// 8秒
            conn.setReadTimeout(8000);// 8秒
            conn.setDoOutput(true);
            conn.setUseCaches(false);//不能使用缓存
            conn.setRequestProperty("Content-Type","application/json; charset=UTF-8");
            conn.setRequestProperty("accept","application/json");
            for(String key: header.keySet()){
                conn.setRequestProperty(key,header.get(key));
            }
            conn.setRequestMethod("POST");

            PrintWriter pw = new PrintWriter(conn.getOutputStream());
            pw.print(json);
            pw.flush();
            pw.close();
            in = conn.getInputStream();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            if(e instanceof SocketTimeoutException){
                SocketTimeoutException e1 = (SocketTimeoutException)e;
                throw e1;
            }
            e.printStackTrace();
        }
        return stream2String(in);
    }



    public static InputStream postAsBinary(String url, byte[] body, Map<String,String> header) throws Throwable {
        URL realurl = null;
        InputStream in = null;
        HttpURLConnection conn = null;
        try {

            realurl = new URL(url);
            conn = (HttpURLConnection) realurl.openConnection();
            conn.setConnectTimeout(8000);// 8秒
            conn.setReadTimeout(8000);// 8秒
            conn.setDoOutput(true);
            conn.setUseCaches(false);//不能使用缓存
            conn.setRequestProperty("Content-Type","application/json; charset=UTF-8");
            conn.setRequestProperty("accept","application/json");
            for(String key: header.keySet()){
                conn.setRequestProperty(key,header.get(key));
            }
            conn.setRequestMethod("POST");
            OutputStream pw = conn.getOutputStream();
            pw.write(body);
            pw.flush();
            pw.close();
            in = conn.getInputStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            if(e instanceof SocketTimeoutException){
                SocketTimeoutException e1 = (SocketTimeoutException)e;
                throw e1;
            }
            e.printStackTrace();
        }
        return in;
    }


    /**
     * @param url http://xx.sss.com?a=b&aa=bb
     * @return
     * @throws IOException
     */
    public static InputStream sendGet(String url, String sign) {
        URL realurl = null;
        InputStream in = null;
        HttpURLConnection conn = null;
        try {
            realurl = new URL(url);
            conn = (HttpURLConnection) realurl.openConnection();
            conn.setConnectTimeout(8000);// 8秒
            conn.setReadTimeout(8000);// 8秒
            conn.setUseCaches(false);//不能使用缓存
            conn.setRequestMethod("GET");
            conn.setRequestProperty("extend", sign);
            conn.setRequestProperty("Content-Type","application/json;charset=UTF-8");

            in = conn.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return in;
    }

    public static InputStream sendGet(String url, String sign, String... headers) {
        URL realurl = null;
        InputStream in = null;
        HttpURLConnection conn = null;
        try {
            Map<String,String> header = new HashMap<>();
            for(int i=0;i<headers.length;i=i+2){
                header.put(headers[i],headers[i+1]);
            }

            realurl = new URL(url);
            conn = (HttpURLConnection) realurl.openConnection();
            conn.setConnectTimeout(8000);// 8秒
            conn.setReadTimeout(8000);// 8秒
            conn.setUseCaches(false);//不能使用缓存
            conn.setRequestMethod("GET");
            conn.setRequestProperty("extend", sign);
            conn.setRequestProperty("Content-Type","application/json;charset=UTF-8");
            for(String key: header.keySet()){
                conn.setRequestProperty(key,header.get(key));
            }

            in = conn.getInputStream();
        } catch (IOException e) {
//            e.printStackTrace();
        }
        return in;
    }

    public static InputStream sendGet(String url, int timeout, String sign, String... headers) {
        URL realurl = null;
        InputStream in = null;
        HttpURLConnection conn = null;
        try {
            Map<String,String> header = new HashMap<>();
            for(int i=0;i<headers.length;i=i+2){
                header.put(headers[i],headers[i+1]);
            }

            realurl = new URL(url);
            conn = (HttpURLConnection) realurl.openConnection();
            conn.setConnectTimeout(timeout);// 8秒
            conn.setReadTimeout(timeout);// 8秒
            conn.setUseCaches(false);//不能使用缓存
            conn.setRequestMethod("GET");
            conn.setRequestProperty("extend", sign);
            conn.setRequestProperty("Content-Type","application/json;charset=UTF-8");
            for(String key: header.keySet()){
                conn.setRequestProperty(key,header.get(key));
            }

            in = conn.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return in;
    }

    public static byte[] getAsBinary(String url, String sign, String... headers) {
        return streamToBytes(sendGet(url,sign,headers));
    }

    public static String getAsString(String url, String sign, String... headers){
        return stream2String(sendGet(url,sign,headers));
    }

    public static byte[] streamToBytes(InputStream in) {
        if(in==null){
            return null;
        }
        byte[] bytes = null;
        BufferedInputStream bin = new BufferedInputStream(in);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(baos);
        byte[] buffer = new byte[1028 * 8];
        int length = 0;
        try {
            while ((length = bin.read(buffer)) > 0) {
                bos.write(buffer, 0, length);
            }
            bos.flush();
            bytes = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                bin.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bytes;
        }
    }

    public static String stream2String(InputStream in) {
        if (in == null) {
            return null;
        }
        InputStreamReader bin = new InputStreamReader(in);
        BufferedReader br = new BufferedReader(bin);
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sb.toString();
        }
    }
}
