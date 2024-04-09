package vip.wqby.learnserver.utils.logUtils;

import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.Date;

public class qLog {
    public static void info(String... msg) {
        String allMsg = "";
        for (String m : msg) {
            allMsg += m + "|";
        }
        allMsg=allMsg.substring(0,allMsg.length()-1);
        System.out.println(getMsg_red_strong_null("qLog{" + getPID() + "}: [INFO] ") + getMsg_blue_null_null(getTime()) + " " + allMsg);
    }


    //@param fontColor, 字体颜色：30黑 31红 32绿 33黄 34蓝 35紫 36深绿 37白
    //@param fontType, 字体格式：0重置 1加粗 2减弱 3斜体 4下划线 5慢速闪烁 6快速闪烁
    //@param backgroundColor, 字背景颜色：40黑 41红 42绿 43黄 44蓝 45紫 46深绿 47白
    public static String getColoredOutputString(String content, int fontColor, int fontType, int backgroundColor) {
        return String.format("\033[%d;%d;%dm%s\033[0m", fontColor, fontType, backgroundColor, content);
    }

    public static String getColoredOutputString(String content, int fontColor, int fontType) {
        return String.format("\033[%d;%dm%s\033[0m", fontColor, fontType, content);
    }

    //colorCode决定了取字体颜色\格式\背景颜色
    public static String getColoredOutputString(String content, int colorCode) {
        return String.format("\033[%dm%s\033[0m", colorCode, content);
    }


    public static String getMsg_red_strong_null(String msg) {
        return getColoredOutputString(msg, 31, 1);
    }

    public static String getMsg_red_null_null(String msg) {
        return getColoredOutputString(msg, 31);
    }

    public static String getMsg_blue_null_null(String msg) {
        return getColoredOutputString(msg, 34);
    }

    public static String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    public static String getPID() {
        String RuntimeMXBean = ManagementFactory.getRuntimeMXBean().getName();
        String[] infos = RuntimeMXBean.split("@");
        return infos[0];
    }
}
