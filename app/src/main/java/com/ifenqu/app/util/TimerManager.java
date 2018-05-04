package com.ifenqu.app.util;

import android.os.CountDownTimer;

/**
 * 处理验证码倒计时请求逻辑类
 *
 */
public class TimerManager {
    private TimerListener listener;
    private CountDownTimer countDownTimer;

    public void startTimer(long millis,TimerListener listener){
        this.listener = listener;
        start(millis);
    }

    private void start(long millis){
        countDownTimer = new CountDownTimer(millis, 1000) {

            public void onTick(long millisUntilFinished) {
                if (listener != null){
                    listener.timer(false,millisUntilFinished/1000);
                }
            }

            public void onFinish() {
                if (listener != null){
                    listener.timer(true,0);
                }
            }
        }.start();

    }

    public void cancelTimer(){
        if (countDownTimer != null){
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }


    public interface TimerListener {
        void timer(boolean isFinished,long time);
    }
}
