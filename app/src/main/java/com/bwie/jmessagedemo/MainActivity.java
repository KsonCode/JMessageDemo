package com.bwie.jmessagedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.EventNotificationContent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.api.BasicCallback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button loginBtn,regBtn,sendBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        JMessageClient.registerEventReceiver(this);
    }

    private void initView() {
        loginBtn = findViewById(R.id.login);
        regBtn = findViewById(R.id.reg);
        sendBtn = findViewById(R.id.send);
        loginBtn.setOnClickListener(this);
        regBtn.setOnClickListener(this);
        sendBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.login:

                JMessageClient.login("bbbb", "111111", new BasicCallback() {
                    @Override
                    public void gotResult(int i, String s) {
                        System.out.println("i=="+i+" s==="+s);
                        if (0==i){//成功

                            Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        }else{//失败
                            Toast.makeText(MainActivity.this, "登录失败", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
                break;
            case R.id.reg:
                JMessageClient.register("bbbb", "111111", new BasicCallback() {
                    @Override
                    public void gotResult(int i, String s) {
                        if (0==i){//成功

                            Toast.makeText(MainActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        }else{//失败
                            Toast.makeText(MainActivity.this, "注册失败", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

                break;
            case R.id.send:
               Message message =  JMessageClient.createSingleTextMessage("aaaa", "", "hello");
                message.setOnSendCompleteCallback(new BasicCallback() {
                    @Override
                    public void gotResult(int i, String s) {
                        System.out.println("iiiii"+i);
                    }
                });
                JMessageClient.sendMessage(message);

                break;
        }

    }


    //用户在线期间，如果群组中发生了成员变化事件，sdk也会通过上抛MessageEvent的方式来通知上层
    public void onEvent(MessageEvent event) {
        Message msg = event.getMessage();
        System.out.println("msg==="+msg);
        //获取消息类型，如text voice image eventNotification等。
        switch (msg.getContentType()) {
            //处理事件提醒消息，此处message的contentType类型为eventNotification。
            case eventNotification:
                //获取事件发生的群的群信息
                GroupInfo groupInfo = (GroupInfo) msg.getTargetInfo();
                //获取事件具体的内容对象
                EventNotificationContent eventNotificationContent = (EventNotificationContent)msg.getContent();
                //获取事件具体类型
                switch (eventNotificationContent.getEventNotificationType()){
                    case group_member_added:
                        //群成员加群事件
                        break;
                    case group_member_removed:
                        //群成员被踢事件
                        break;
                    case group_member_exit:
                        //群成员退群事件
                        break;
                    case group_info_updated://since 2.2.1
                        //群信息变更事件
                        break;

                }
                break;
        }
    }
}
