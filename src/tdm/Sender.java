package tdm;

import java.util.Random;


public class Sender implements Runnable{

    int packets;
    float rate;
    double p;
    int id;
    Timeline timeline;
    int  n = 0;
    int rand = 0 ;
    long  d  = 0;
    long sum= 0 ;
    int notSend = 0;


    public Sender(int id,int packets, float rate, double p, Timeline timeline) {
        this.id = id;
        this.packets = packets;
        this.rate = rate;
        this.p = (1-p)*100;
        this.timeline = timeline;
    }

    @Override
    public void run(){
                if (rand > p) {

                    System.out.println("sender " + id + " sent packet " + n++ + " average dealy " + sum);
                    if(n!=0)
                    sum = (sum*(n-1) + System.currentTimeMillis()- d )/n;
                }else {
                     System.out.println("sender " + id + " didn't send");
                    notSend++;
                }

        Random rn = new Random();
         rand = rn.nextInt(100) + 1;
        if(rand > p)
            d = System.currentTimeMillis();
    }

}
