package org.example;


import com.rometools.rome.feed.synd.SyndEntry;

import java.time.LocalTime;
import java.util.List;


public class MailingThread implements Runnable{


    private final Bot bot;

    public MailingThread(Bot bot){
        this.bot=bot;
    }

    @Override
    public void run() {

        while (true){




            if (isNow(8,0)){
                mailNews();
            } else if (isNow(14,0)){
                mailNews();
            } else if (isNow(20,0)){
                mailNews();
            } else if (isNow(23,0) || isNow(11,0)){
                DataBase.insertDailyNews();
            }


            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                System.out.println("Exc in mailing tread " + e.getMessage() + " " + e.getClass().getName());
            }
        }

    }

    private Boolean isNow(int hour, int minute){
        return (LocalTime.now(Constants.timeZone).getHour() == hour &&
                LocalTime.now(Constants.timeZone).getMinute() == minute);
    }



    private void mailNews(){
        try {
            bot.mailingForAllSubs(Parser.parse(Journals.VC.getRssUrl(), Constants.defaultAmountOfHoursForParse));
        } catch (Exception exception) {
        }
    }


}
