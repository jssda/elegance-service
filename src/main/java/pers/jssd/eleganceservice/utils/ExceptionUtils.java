package pers.jssd.eleganceservice.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author jssd jssdjing@gmail.com
 * @date 2020/10/23 16:14
 */
public class ExceptionUtils {

    /**
     * 获取e.printStackTrace() 的具体信息，赋值给String 变量，并返回
     *
     * @param e Exception
     * @return e.printStackTrace() 中 的信息
     */
    public static String getStackTraceInfo(Exception e) {
        try (StringWriter sw = new StringWriter(); PrintWriter pw = new PrintWriter(sw)) {
            //将出错的栈信息输出到printWriter中
            e.printStackTrace(pw);
            pw.flush();
            sw.flush();
            return sw.toString();
        } catch (Exception ex) {
            return "发生错误";
        }
    }

}
