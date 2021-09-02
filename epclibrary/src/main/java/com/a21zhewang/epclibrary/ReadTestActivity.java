package com.a21zhewang.epclibrary;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.handheld.UHF.UhfManager;

import java.util.List;

import cn.pda.serialport.Tools;

public class ReadTestActivity extends AppCompatActivity {


    private Handler handler;
    private TextView textView;
    private Button btn;
    private ReadThread readThread;
    private UhfManager uhfManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_test);
        try {
            uhfManager = UhfManager.getInstance();
        } catch (Exception e) {

        }finally {
            if (uhfManager==null){
                Toast.makeText(this,"开启扫描设备失败,请重新打开页面！",Toast.LENGTH_LONG).show();
            }
        }
        initview();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                //线程里获取到id 显示出来
                if (msg.obj != null) {
                    textView.setText(msg.obj.toString());
                }
            }
        };
        //创建一个线程
        readThread = new ReadThread();
        //开始一个线程
        readThread.start();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (readThread==null)
                    return;
                //开始扫描
                if (readThread.start){
                    readThread.start=false;
                }else{
                    readThread.start=true;
                }
            }
        });

    }

    private void initview() {
        textView = (TextView) findViewById(R.id.textView);
        btn = (Button) findViewById(R.id.button);
    }

    /**
     * Inventory EPC Thread
     */
    class ReadThread extends Thread {
        private List<byte[]> epcList;
        private boolean start;
        private boolean finish=true;
        @Override
        public void run() {
            super.run();
            while (finish) {
                if (start) {
                   if (uhfManager==null) {
                       Log.e("readuhf", "Util.manager==null");
                       break;
                   }
                    epcList = uhfManager.inventoryRealTime(); // inventory real time

                    if (epcList != null && !epcList.isEmpty()) {
                        // play sound
                        Util.play(1, 0);
                        byte[] bytes = epcList.get(0);
                        String epcStr = Tools.Bytes2HexString(bytes,
                                bytes.length);

                        Message msg = new Message();
                        msg.obj = epcStr;

                        epcList = null;
                        start = false;

                        handler.sendMessage(msg);

                    }
                }

            }

        }
    }
}
