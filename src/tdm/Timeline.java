package tdm;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;


public class Timeline implements Runnable{


    List<Sender> senders;
    int time = 0;
    int n = 0;
    public Timeline() {
        init();
    }

    void init(){

        senders = new ArrayList<>();

        for(int c = 0 ; c < 16 ; c++)
        senders.add(new Sender(c,100,0,0.5,this));

        new Thread(this,"timer").start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {

        int bahre = 0;
        for(int c = 0 ;c <1;c++) {
            Timeline t = new Timeline();
        }


    }

    public void sendingFinished(){

        System.out.println("------------------------");

    }

    @Override
    public void run() {
        int send = 0;
        while (true) {
            try {
                Thread.sleep(1);
                time++;
                if(time % 100 == 0) {
                    System.out.println("TDM#"+time/100%senders.size());
                    send++;
                    new Thread(senders.get(time / 100%senders.size()), time / 100 + "").start();
                    sendingFinished();
                }
                if(time == senders.size()*1000)
                    break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        int av = 0;
         n = 0;

        for(tdm.Sender sender : senders)
        {
            System.out.println("sender "+senders.indexOf(sender) + " delay: "+sender.sum + " NOT SEND "+sender.notSend);
            av += sender.sum;
            n += sender.notSend;
        }
        System.err.println("average delay : " + av/senders.size() );
        System.err.println("link util : " + (send-n) +"/"+send +"="+ (float)(send-n)/send);
        System.err.println("success rate : 100%");

    }
}

