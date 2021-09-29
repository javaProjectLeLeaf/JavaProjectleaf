package com.rzspider.project.car.carManage.utils;



import com.rzspider.project.car.carManage.domain.Car;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class CarUtil {


    public Car newCar() {
        Car car = new Car();
        car.setType(this.randomtype());
        car.setCode(this.randomCode() + "");
        car.setIndate(new Date(new Date().getTime() - new Random().nextInt(100000000)));
        car.setType(this.randomtype());
        return car;
    }

    private String randomtype() {
        Random random = new Random();
        String a[] = {"0","1","2"};
        String type = a[random.nextInt(2)];
        return type;
    }

    public StringBuffer randomCode() {
        Random random = new Random();
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXTZQWERTYUIOPASDFGHJKLZXCVBNM012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789";
        String stri = "0123456789";
        StringBuffer chePai = new StringBuffer(4);
        chePai.append("浙L");
        for (int i = 0; i < 4; i++) {
            char a = str.charAt(random.nextInt(str.length()));
            chePai.append(a);

        }
        char b = stri.charAt(random.nextInt(stri.length()));
        chePai.append(b);
        return chePai;
    }



}
