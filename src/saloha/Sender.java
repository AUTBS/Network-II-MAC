package saloha;

import java.util.Random;


public class Sender implements Runnable{

    int packets;
    float rate;
    double p;
    int id;
    Timeline timeline;
    boolean sending;
    int  n = 0;
    int rand = 100;
    long d = 0;
    long sum = 0;
    int co = -1;
    int c = 0;
    int s =0 ;

    public Sender(int id,int packets, float rate, double p, Timeline timeline) {
        this.id = id;
        this.packets = packets;
        this.rate = rate;
        this.p = (1-p)*100;
        sending = false;
        this.timeline = timeline;
    }

    @Override
    public void run() {
        Random rn = new Random();
        rand = rn.nextInt(100) + 1;
        if (rand > p) {
            if(co != n && n != 0)
                sum +=  System.currentTimeMillis() - d ;
                    System.out.println("sender " + id + "sent packet " + n++ + " average delay :" + sum);
                    sending = true;
                    s++;
                    timeline.sendPacket(id);


            if(co != n-1)
            d = System.currentTimeMillis();
        }else                 try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void conflict() {
        if (sending) {
            n--;
            sending =false;
            System.out.println("sender " + id + "confilict packet " + n);
            co = n;
            c++;
        }
    }
}
