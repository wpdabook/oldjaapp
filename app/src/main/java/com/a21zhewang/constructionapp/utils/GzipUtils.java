package com.a21zhewang.constructionapp.utils;

import android.text.TextUtils;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by zhewang_ljw on 2017/2/23.
 */

public class GzipUtils {
    /**
     * Gzip 压缩数据
     *
     * @param unGzipStr
     * @return
     */
    public static byte[] compressForGzip(String unGzipStr) {

        if (TextUtils.isEmpty(unGzipStr)) {
            return null;
        }
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream(baos);
            gzip.write(unGzipStr.getBytes());
            gzip.close();
            byte[] encode = baos.toByteArray();
            Log.e("encode", encode.length + "");
            baos.flush();
            baos.close();
            return encode;
        } catch (UnsupportedEncodingException e) {
            Log.e("错误", e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("错误", e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Gzip 压缩数据
     *
     * @param unGzipStr
     * @return
     */
    public static byte[] compressForGzip(File unGzipStr) {

        if (unGzipStr == null) {
            return null;
        }
        try {
            FileInputStream fis = new FileInputStream(unGzipStr);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream(baos);
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                gzip.write(b, 0, n);
            }
            gzip.close();
            byte[] encode = baos.toByteArray();
            Log.e("encode", encode.length + "");
            baos.flush();
            baos.close();
            return encode;
        } catch (UnsupportedEncodingException e) {
            Log.e("错误", e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("错误", e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public static byte[] File2byte(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * Gzip解压数据
     *
     * @param gzipStr
     * @return
     */
    public static String decompressForGzip(byte[] gzipStr) {
//        if (TextUtils.isEmpty(gzipStr)) {
//            return null;
//        }
//        byte[] t = Base64Decoder.decodeToBytes(gzipStr);
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ByteArrayInputStream in = new ByteArrayInputStream(gzipStr);
            GZIPInputStream gzip = new GZIPInputStream(in);
            byte[] buffer = new byte[1024];
            int n = 0;
            while ((n = gzip.read(buffer, 0, buffer.length)) > 0) {
                out.write(buffer, 0, n);
            }
            gzip.close();
            in.close();
            out.close();
            return out.toString("UTF-8");
        } catch (IOException e) {
            Log.e("错误", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
