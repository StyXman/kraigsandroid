package com.angrydoughnuts.android.alarmclock;

import java.util.TimerTask;

import android.content.Context;
import android.os.Handler;
import android.os.RemoteException;

public class AlarmClockTimerTask extends TimerTask {

  private Handler handler;
  int id;
  private AlarmClockServiceBinder service;

  final private Runnable work = new Runnable() {
    @Override
    public void run() {
      try {
        service.clock().fire(id);
      } catch (RemoteException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  };

  public AlarmClockTimerTask(Context context, Handler handler, int id) {
    this.handler = handler;
    this.id = id;
    this.service = AlarmClockServiceBinder.newBinder(context);
  }

  @Override
  public synchronized void run() {
    // TODO(cgallek): This currently re-binds to the service on every
    // run.  Figure out how to reference count threads and only
    // bind as necessary in the Timer thread.
    service.bind();

    handler.post(work);

    service.unbind();
  }

  @Override
  public boolean cancel() {
    handler.removeCallbacks(work);
    return super.cancel();
  }
}
