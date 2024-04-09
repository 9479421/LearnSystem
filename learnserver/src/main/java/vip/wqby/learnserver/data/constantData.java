package vip.wqby.learnserver.data;

import vip.wqby.learnserver.pojo.noteData;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class constantData {
    public static LinkedBlockingQueue<noteData> registerQueue =new LinkedBlockingQueue<noteData>();

    //线程安全
    public static Vector<noteData> registerList = new Vector<>();
//    public static List<noteData>resetList = new ArrayList();

    public static void sendRegisterNote(String phone){
        String code = String.valueOf((int)((Math.random()*9+1)*Math.pow(10,5)));
        registerQueue.add(new noteData(phone,code,System.currentTimeMillis()/1000+60));
    }
}
