package vip.wqby.learnserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FullyQualifiedAnnotationBeanNameGenerator;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import vip.wqby.learnserver.data.constantData;
import vip.wqby.learnserver.pojo.noteData;
import vip.wqby.learnserver.utils.TenCloudUtils;

import java.util.Iterator;
import vip.wqby.learnserver.consumer.noteConsumer;

@SpringBootApplication
@MapperScan(basePackages = "vip.wqby.learnserver.mapper")
@EnableTransactionManagement //事务
public class LearnserverApplication {
    public static void main(String[] args) {
        SpringApplication.run(LearnserverApplication.class, args);

//        constantData.registerList.add(new noteData("17301491797","111",System.currentTimeMillis()/1000+60));
//        constantData.registerList.add(new noteData("17301491797","222",System.currentTimeMillis()/1000+60));
//        Iterator iterator = constantData.registerList.iterator();
//        while (iterator.hasNext()){
//            Object next = iterator.next();
//            System.out.println(next);
//        }
//        System.out.println("哈哈哈");

        new noteConsumer().run();
    }

}
