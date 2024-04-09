package vip.wqby.learnserver.utils;

public class Utils {
    public static int toPercent(int a, int b) {
       try {
           if (a % b == 0) {
               return a / b * 100;
           } else {
               return (int) Math.round(a / (double) b * 1000) / 10;
           }
       }catch (Exception e){
           return 0;
       }
    }

    public static String formatTime(int allseconds) {
        int hours = allseconds / 3600;
        int minutes = (allseconds - 3600 * hours) / 60;
        int seconds = (allseconds - 3600 * hours - 60 * minutes);
        return hours+":"+minutes+":"+seconds;
    }
}
