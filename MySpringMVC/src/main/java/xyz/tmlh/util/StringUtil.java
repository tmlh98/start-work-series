package xyz.tmlh.util;

/**  
 * Created by TianXin on 2019年1月22日. 
 */
public class StringUtil {
    
    
    /**
     * 把字符串的首字母小写
     * 
     * @param name
     * @return
     */
    public static String toLowerFirstWord(String name) {
        char[] charArray = name.toCharArray();
        charArray[0] += 32;
        return String.valueOf(charArray);
    }

}
