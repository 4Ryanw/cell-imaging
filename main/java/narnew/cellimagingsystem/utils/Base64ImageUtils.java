package narnew.cellimagingsystem.utils;

import java.io.*;
import java.util.Base64;


/**
 * @Author: WenRan
 * @Date: 2022/3/7
 * @description:
 */
public class Base64ImageUtils {

    /**
     * 将图片转换成Base64编码
     * @param imgFile 待处理文件
     * @return
     */
    public static String imageFileToBase64String(String imgFile) {
        InputStream in = null;
        byte[] data = null;
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Base64.getEncoder().encodeToString(data);
    }

    /**
     * 将Base64字符串生成一个图片文件
     * @param base64String 待处理的Base64字符串
     * @return
     */
    public static boolean base64StringToImageFile(String base64String, String imgFilePath) {
        byte[] data = null;
        // 对字节数组字符串进行Base64解码并生成图片
        if (base64String == null)
            return false;
        try {
            // Base64解码
            data = Base64.getDecoder().decode(base64String.getBytes());
            // 生成图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(data);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
