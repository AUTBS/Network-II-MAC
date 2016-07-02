package saloha;

import java.util.ArrayList;
import java.util.List;


public class Timeline implements Runnable{

    int line;
    List<Sender> senders;
    int time = 0;
    boolean s = false;
    int sending = 0;
    public Timeline() {
        init();
    }

    void init(){
        line = -1;

        new Thread(this,"timer").start();

        senders = new ArrayList<>();

        for(int c = 0 ;c < 8 ;c++) {
            senders.add(new Sender(c, 100, 0, 0.33, this));
            new Thread(senders.get(c), c+"").start();
        }

    }

    public static void main(String[] args) {
        new Timeline();
    }

    public void sendPacket(int id) {
        if (line >= 0) {
            senders.get(id).conflict();
            senders.get(line).conflict();
            s= false;
        } else {
            line = id;
            s = true;
        }
    }

    public void sendingFinished(){

        System.out.println("------------------------");
        line = -1;
        for(int c = 0 ;c < senders.size() ;c++) {
            new Thread(senders.get(c),c+"").start();
        }

    }

    @Override
    public void run() {
        int d = 0;
        while (true) {
            try {
                Thread.sleep(1);
                time++;
                if(time % 100 == 0) {
                    d++;
                    sendingFinished();
                    s = false;
                }
                if(time == senders.size()*1000)
                    break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int av = 0;
        int c = 0;
        int s = 0;
        for(Sender sender : senders)
        {
            if(sender.n!=0)
            System.out.println("sender "+senders.indexOf(sender) + " delay: "+sender.sum/sender.n);
            av += sender.sum/sender.n;
            sending += sender.n;
            c += sender.c;
            s += sender.s;
        }
        System.err.println("average delay : " + av/senders.size());
        System.err.println("link util" + sending+"/"+d +" = "+ (float)sending/d );
        System.err.println("success rate :" + (s-c) +"/"+s +"="+(float)(s-c)/s );

    }
}

