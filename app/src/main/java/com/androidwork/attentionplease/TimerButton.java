package com.androidwork.attentionplease;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class TimerButton extends androidx.appcompat.widget.AppCompatButton implements View.OnClickListener {
    private long length = 3 * 1000;//默认倒计时时间；
    private long time;//倒计时时长
    private Timer timer;//开始执行倒计时
    private TimerTask timerTask;//每次倒计时执行的任务
    private String showText = "秒 跳过？";
    private OnClickListener onClickListener;//按钮点击事件
    /**
     * 更新显示的文本
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            TimerButton.this.setText(time / 1000 + showText);
            time -= 1000;
            if (time < 0) {
                TimerButton.this.setEnabled(true);
                //倒计时结束
                clearTimer();
            }
        }
    };


    public TimerButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
    }

    public TimerButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnClickListener(this);
    }

    /**
     * 清除倒计时
     */
    private void clearTimer() {
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    /**
     * 设置倒计时时长
     *
     * @param length 默认毫秒
     */
    public void setLenght(long length) {
        this.length = length;
    }



    /**
     * 设置未点击后显示的文字
     *
     * @param showText
     */
    public void setAfterText(String showText) {
        this.showText = showText;
    }

    /**
     * 点击按钮后的操作
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (onClickListener != null) {
            onClickListener.onClick(v);
        }
        initTimer();
        this.setText(time / 1000 + showText);
        this.setEnabled(false);
        timer.schedule(timerTask, 0, 1000);
    }

    /**
     * 初始化时间
     */
    private void initTimer() {
        time = length;
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(1);
            }
        };
    }

    /**
     * 设置监听按钮点击事件
     *
     * @param onclickListener
     */
    @Override
    public void setOnClickListener(OnClickListener onclickListener) {
        if (onclickListener instanceof TimerButton) {
            super.setOnClickListener(onclickListener);

        } else {
            this.onClickListener = onclickListener;
        }

    }
}
