package com.yunxin.utils.compression;

import java.io.*;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

public class Zip {

    /**
     * Compresses a file with zlib compression.
     */
    public static void zip(java.io.File raw, java.io.File compressed)
            throws IOException {
        InputStream in = null;
        OutputStream out = null;
        try {
             in = new FileInputStream(raw);
             out =
                    new DeflaterOutputStream(new FileOutputStream(compressed));
            shovelInToOut(in, out);
            in.close();
            out.close();
        }catch (Exception t){
            throw t;
        }finally {
            if(in!=null){
                in.close();
            }
            if(out!=null){
                out.close();
            }
        }
    }

    /**
     * Compresses a file with zlib compression.
     */
    public static void zip(InputStream raw, OutputStream compressed) throws Exception {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = raw;
            out =
                    new DeflaterOutputStream(compressed);
            shovelInToOut(in, out);
        } catch (Exception e) {
            throw e;
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }

    }

    public static byte[] zip(byte[] data) {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            zip(bis, bos);
            return bos.toByteArray();
        } catch (Throwable e) {
            return null;
        }
    }

    /**
     * Decompresses a zlib compressed file.
     */
    public static void unzip(InputStream compressed, OutputStream raw)
            throws Exception {
        InputStream in = null;
        OutputStream out = null;
        try {
             in =
                    new InflaterInputStream(compressed);
             out = raw;
            shovelInToOut(in, out);
            in.close();
            out.close();
        }catch (Exception t){
            throw  t;
        }finally {
            if(in!=null){
                in.close();
            }
            if(out!=null){
                out.close();
            }
        }
    }

    /**
     * Decompresses a zlib compressed file.
     */
    public static byte[] unzip(byte[] compressed) throws IOException {
        InputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            in = new InflaterInputStream(new ByteArrayInputStream(compressed));
            out = new ByteArrayOutputStream();
            shovelInToOut(in, out);
            in.close();
            out.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw  e;
        }finally {
            if(in!=null){
                in.close();
            }
            if(out!=null){
                out.close();
            }
        }
    }

    /**
     * Decompresses a zlib compressed file.
     */
    public static void unzip(File compressed, File raw)
            throws IOException {
        InputStream in = null;
        OutputStream out = null;
        try {
             in =
                    new InflaterInputStream(new FileInputStream(compressed));
             out = new FileOutputStream(raw);
            shovelInToOut(in, out);
            in.close();
            out.close();
        }catch (Exception e){
            throw e;
        }finally {
            if(in!=null){
                in.close();
            }
            if(out!=null){
                out.close();
            }
        }
    }

    /**
     * Shovels all data from an input stream to an output stream.
     */
    private static void shovelInToOut(InputStream in, OutputStream out)
            throws IOException {
        byte[] buffer = new byte[1000];
        int len;
        while ((len = in.read(buffer)) > 0) {
            out.write(buffer, 0, len);
        }
    }


}
