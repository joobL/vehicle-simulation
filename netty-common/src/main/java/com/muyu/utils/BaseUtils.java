package com.muyu.utils;

/**
 * @author 牧鱼
 * @Classname BaseUtils
 * @Description 进制转换工具类
 * @Date 2021/8/1
 */
public class BaseUtils {


    /** 16进制中的字符集 */
    private static final String HEX_CHAR = "0123456789ABCDEF";

    /** 16进制中的字符集对应的字节数组 */
    private static final byte[] HEX_STRING_BYTE = HEX_CHAR.getBytes();

    /**
     * 16进制字符 转 10进制byte数组
     * @param hexstr
     * @return
     */
    public static byte[] hexStrToTenByte(String hexstr) {
        int len = (hexstr.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hexstr.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (((byte)"0123456789ABCDEF".indexOf(achar[pos])) << 4 | ((byte)"0123456789ABCDEF".indexOf(achar[pos + 1])));
        }
        return result;
    }

    /**
     * 16进制字符 转 10进制 汉字
     * @param hexStr
     * @return
     */
    public static String hexStrToTenStr(String hexStr){
        return new String(hexStrToTenByte(hexStr));
    }


    /**
     * 10进制字节数组转换为16进制字节数组
     *
     * byte用二进制表示占用8位，16进制的每个字符需要用4位二进制位来表示，则可以把每个byte
     * 转换成两个相应的16进制字符，即把byte的高4位和低4位分别转换成相应的16进制字符，再取对应16进制字符的字节
     *
     * @param b 10进制字节数组
     * @return 16进制字节数组
     */
    public static byte[] byte2hex(byte[] b) {
        int length = b.length;
        byte[] b2 = new byte[length << 1];
        int pos;
        for(int i=0; i<length; i++) {
            pos = 2*i;
            b2[pos] = HEX_STRING_BYTE[(b[i] & 0xf0) >> 4];
            b2[pos+1] = HEX_STRING_BYTE[b[i] & 0x0f];
        }
        return b2;
    }

    /**
     * 字节数组转String字符串
     * @param bytes
     * @return
     */
    public static String byteToStr(byte[] bytes){
        StringBuilder sb = new StringBuilder();
        for (byte byt : bytes) {
            sb.append(byt);
        }
        return sb.toString();
    }
}
