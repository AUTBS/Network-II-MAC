package aloha;

import java.util.ArrayList;
import java.util.Random;


public class Sender implements Runnable{

    int packets;
    int rate;
    double p;
    int id;
    Timeline timeline;
    boolean sending;
    int  n = 0;
    int avrageDealy = 0;
    private int conflict = -1;
    long dealys = 0 ;
    int c =0;
    int s = 0;
    public Sender(int id,int packets, int rate, double p, Timeline timeline) {

        this.id = id;
        this.packets = packets;
        this.rate = rate;
        this.p = (1-p)*100;
        sending = false;
        this.timeline = timeline;
    }

    @Override
    public void run() {
        while (true) {
            Random rn = new Random();
            int rand = rn.nextInt(100) + 1;
            int t = 0;
            if(conflict != n)
                dealys = System.currentTimeMillis();
            if (rand > p) {
                System.out.println("sender " + id + " sent packet " + n++ +" average:" + avrageDealy / n);
                sending = true;
                s++;
                timeline.sendPacket(id);

                while ( t < packets/rate ) {
                    try {
                        Thread.sleep(1);
                        t++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                timeline.finish(id);
                if(n != conflict) {
                    avrageDealy +=  System.currentTimeMillis() - dealys;
                }
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        }

    public void conflict() {
            n--;
            conflict = n;
            sending= false;
        c++;
            System.out.println("sender " + id + "confilict packet " + n);
    }
}
