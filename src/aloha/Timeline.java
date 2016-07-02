package aloha;

import java.util.ArrayList;
import java.util.List;


public class Timeline implements Runnable{

    int line;
    List<Thread> threads;
    List<Sender> senders;
    int time = 0;
    public Timeline() {
        init();
    }

    void init(){
        line = -1;
        senders = new ArrayList<>();
        threads = new ArrayList<>();

        new Thread(this,"timer").start();
        for(int c = 0 ; c < 8 ;c++) {
            senders.add(new Sender(c, 10000, 100, 0.05, this));
            threads.add(new Thread(senders.get(c), c + "0"));
            threads.get(c).start();
        }

//
//        senders.add(new Sender(2,50,1,0.02,this));
//        threads.add(new Thread(senders.get(2),"2"));
//        threads.get(2).start();
//
//        senders.add(new Sender(3,50,1,0.02,this));
//        threads.add(new Thread(senders.get(3),"3"));
//        threads.get(3).start();



    }

    @Override
    public void run() {
        int d = 0;
        while (true) {
            try {
                Thread.sleep(1);
                time++;
                if(time == senders.size()*100)
                    break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for(Thread t : threads)
            t.stop();

        int av = 0;
        int sending = 0;
        int c = 0;
        int s = 0;
        for(Sender sender : senders)
        {
            if(sender.n!=0)
                System.out.println("sender "+senders.indexOf(sender) + " delay: "+sender.avrageDealy/sender.n);
            av += sender.avrageDealy/(sender.n+1);
            sending += sender.n*(sender.packets/sender.rate);
            c += sender.c;
            s += sender.s;
        }
        c = s-c;
        System.err.println("average delay : " + av/senders.size());
        System.err.println("link util : "+sending+"/"+time +"="+ (float)sending/time );
        System.err.println("success rate : "+ c + "/" + s + "=" + (float)c/s);
    }


    public static void main(String[] args) {
        new Timeline();
    }

    public void sendPacket(int id) {
        if(line >= 0) {
            senders.get(id).conflict();
            if(line != -1)
            senders.get(line).conflict();
            line = -1;
        }
        else  line = id;
    }

    public void finish(int id) {
        if(line  ==  id)
            line = -1;
    }
}

