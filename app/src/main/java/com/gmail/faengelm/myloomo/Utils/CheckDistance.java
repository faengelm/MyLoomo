package com.gmail.faengelm.myloomo.Utils;

import android.os.AsyncTask;
import android.widget.TextView;

import com.gmail.faengelm.myloomo.Services.ObstacleService;
import com.gmail.faengelm.myloomo.Services.SoundsService;

public class CheckDistance extends AsyncTask<Integer, Void, String> {

    private ObstacleService obstacleService;
    private SoundsService soundsService;
    private TextView textView;

    public CheckDistance(TextView textView) {
        this.textView = textView;
        soundsService = SoundsService.getInstance();
        obstacleService= ObstacleService.getInstance();
    }


  @Override
    protected String doInBackground(Integer... params) {
    //    int seconds = params[0];

      while(true) {
          try {
              Thread.sleep(1500);
              obstacleService.senseDistance();
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
      }
     //   return "OK";
    }

    @Override
    protected void onPostExecute(String result) {
  }
}