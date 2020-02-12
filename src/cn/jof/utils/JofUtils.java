package cn.jof.utils;

/**
 * 工具类
 * 
 * @author JunOneWolf
 * @date 2020-1-17
 * @version 1.0
 */
public class JofUtils {
    public static String bytes2hex(byte[] bytes) {
        if (bytes == null) {
            return "";
        }
        char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        // 一个字节对应两个16进制数，所以长度为字节数组乘2
        char[] resultCharArray = new char[bytes.length * 2];
        int index = 0;
        for (byte b : bytes) {
            resultCharArray[index++] = hexDigits[b >>> 4 & 0xF];// 左边四位,&转化16进制
            resultCharArray[index++] = hexDigits[b & 0xF];// 右边四位,&转化16进制
        }
        return new String(resultCharArray);
    }

    // 转化四位十六进制数值为对应的char
    private static char chars2char(char a, char b, char c, char d) {
        return (char) ((Character.digit(a, 16) << 12) + (Character.digit(b, 16) << 8) + (Character.digit(c, 16) << 4)
                + (Character.digit(d, 16)));
    }

    /**
     * 十六进制字符串(E68891E7)转化byte数组,
     * 
     * @param hex
     * @return
     */
    public static byte[] hex2bytes(String hex) {
        if (hex == null || hex.length() == 0 || (hex.length() & 2) == 1) {
            return null;
        }
        byte[] bs = new byte[hex.length() / 2];
        for (int i = 0, size = hex.length(); i < size; i += 2) {
            bs[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4) + (Character.digit(hex.charAt(i + 1), 16)));
        }
        return bs;
    }

    /**
     * 字符是否符合十六进制
     * 
     * @param c
     * @return 字符是否是16进制
     * @date 2020-1-17
     */
    public static boolean isHex(char c) {
        return (c >= '0' && c <= '9') || (c >= 'A' && c <= 'F') || (c >= 'a' && c <= 'f');
    }

    /**
     * 字符串是否全部为十六进制
     * 
     * @param str
     * @return boolean
     * @date 2020-1-17
     */
    public static boolean isHex(String str) {
        for (int i = 0, size = str.length(); i < size; i++) {
            if (!isHex(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * char数组都是十六进制字符
     * 
     * @param cs
     * @return
     * @date 2020-1-21
     */
    public static boolean isHexs(char... cs) {
        for (char d : cs) {
            if (isHex(d)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 字符串尽可能替换unicode数据(&#x7231;或\u7231)为对应字符(不区分大小写)
     * 
     * @param data
     * @return
     */
    public static String parseUnicode(String data) {
        if (data == null || data.length() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(data.length());
        for (int i = 0, size = data.length(); i < size; i++) {
            if (i < size - 5 && '\\' == data.charAt(i) && ('u' == data.charAt(i + 1) || 'U' == data.charAt(i + 1))
                    && isHex(data.charAt(i + 2)) && isHex(data.charAt(i + 3)) && isHex(data.charAt(i + 4))
                    && isHex(data.charAt(i + 5))) {
                sb.append(chars2char(data.charAt(i + 2), data.charAt(i + 3), data.charAt(i + 4), data.charAt(i + 5)));
                i += 5;
                continue;
            }
            if (i < size - 7 && '&' == data.charAt(i) && '#' == data.charAt(i + 1) && ';' == data.charAt(i + 7)
                    && ('x' == data.charAt(i + 2) || 'X' == data.charAt(i + 2))
                    && isHexs(data.charAt(i + 3), data.charAt(i + 4), data.charAt(i + 5), data.charAt(i + 6))) {// 不检测是否16进制数，会被恶意数据玩死比如\\uabcg
                sb.append(chars2char(data.charAt(i + 3), data.charAt(i + 4), data.charAt(i + 5), data.charAt(i + 6)));
                i += 7;
                continue;
            }
            sb.append(data.charAt(i));
        }
        return sb.toString();
    }

    /**
     * 字符转化unicode字符串,转化"\u0000"的格式
     * 
     * @param c
     * @return String unicode字符串
     * @date 2020-1-17
     */
    public static String toUnicode(char c) {
        return new StringBuilder(6).append("\\u").append(c >>> 12 & 0xf).append(c >>> 8 & 0xf).append(c >>> 4 & 0xf)
                .append(c & 0xf).toString();
    }

    /**
     * 字符串全部转化unicode字符串,每一个都转化"\u0000"的格式
     * 
     * @param str
     * @return unicode字符串
     * @date 2020-1-17
     */
    public static String toUnicode(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0, size = str.length(); i < size; i++) {
            sb.append(toUnicode(str.charAt(i)));
        }
        return sb.toString();
    }

    private JofUtils() {
    }
}
