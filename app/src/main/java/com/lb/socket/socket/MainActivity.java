package com.lb.socket.socket;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private EditText text; //文本框，要发送给服务器的数据
    private Button btnSocket;  //发送消息的按钮
    private ListView listView; //消息列表的ListView
    private MsgAdapter adapter; //消息适配器
    private List<String> list = new ArrayList<>();//ListView的数据源
    private Socket socket = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (EditText) findViewById(R.id.text);
        btnSocket = (Button) findViewById(R.id.button);
        listView = (ListView) findViewById(R.id.msgList);
        adapter = new MsgAdapter(MainActivity.this, list);
        listView.setAdapter(adapter);
        //开始的时候，我直接放在主线程，直接出现不能在主线程的工作的错误，于是开启了一个线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                socket();

            }
        }).start();
        //发送消息的按钮，发送消息到服务器是异步的，所以也只能另开一个线程
        btnSocket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sendMsg();
                    }
                }).start();
            }
        });

    }

    /**
     * 初始化Socket
     */
    private void socket() {
        try {
            if (socket == null) {
                socket = new Socket("192.168.11.150", 8285);
            }
            receiveMsg();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送消息的方法
     */
    public void sendMsg() {
        PrintStream writer = null;
        try {
            String msg = "客户端说:" + text.getText().toString();
            //此处一定要设置编码的格式，一定要和服务器统一，否则会出现乱码的情况
            writer = new PrintStream(socket.getOutputStream(), true, "UTF-8");
            writer.println(msg);
            writer.flush();
            list.add(msg);
            handler.sendEmptyMessage(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 接收到来自服务器的消息
     */
    public void receiveMsg() {
        BufferedReader br = null;
        try {
            //此处一定要设置编码的格式，一定要和服务器统一，否则会出现乱码的情况
            br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            String msg;
            while ((msg = br.readLine()) != null) {
                list.add(msg);
                handler.sendEmptyMessage(0);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            adapter.notifyDataSetChanged();
        }
    };

}
