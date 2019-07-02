package example.app15chatting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import example.app15chatting.thread.ChatClientSocketThread;
import example.app15chatting.util.EndAlertDialog;

public class ChatActivity extends AppCompatActivity {

    ///Field
    private LinearLayout messageInLayout;
    private String clientName;
    private Button buttonSend;
    private EditText editTextMessage;
    private ScrollView scrollView;


    private ChatClientSocketThread chatClientSocketThread;

    private Handler handler = new Handler(){

        // Call Back Method Definition
        public void handleMessage(Message message){

            if(message.what == 100){

                String fromHostData = (String)message.obj;

                append(fromHostData);

                if(fromHostData.indexOf("회원만 입장가능합니다.") != -1){

                    buttonSend.setEnabled(false);
                    editTextMessage.setClickable(false);
                    editTextMessage.setEnabled(false);
                    editTextMessage.setFocusable(false);
                    editTextMessage.setFocusableInTouchMode(false);

                     new EndAlertDialog(ChatActivity.this)
                            .showEndDialogToActivity("[ 비회원 입니다. ]","회원가입후 사용하세요",LoginActivity.class);
                }

                scrollView.post(new Runnable(){
                    public void run(){
                        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });

                editTextMessage.setText("");

            }

            if(message.what == 500){

                String endMessage = (String)message.obj;

                append(endMessage);

                scrollView.post(new Runnable(){
                    public void run(){
                        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });

                buttonSend.setEnabled(false);
                editTextMessage.setEnabled(false);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);

        Intent intent = this.getIntent();

        this.clientName = intent.getStringExtra("clientName");
        System.out.println(getClass().getSimpleName()+"::대화명:: "+clientName);

        this.scrollView = (ScrollView)findViewById(R.id.scrollview);
        this.messageInLayout = (LinearLayout)findViewById(R.id.message_in_layout);
        this.buttonSend = (Button)findViewById(R.id.button_send);
        this.editTextMessage = (EditText)findViewById(R.id.edittext_message);

        this.chatClientSocketThread =  new ChatClientSocketThread(handler, clientName);
        chatClientSocketThread.start();

        buttonSend.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread() {
                    @Override
                    public void run() {
                        chatClientSocketThread.sendMessgeToServer("200:"+editTextMessage.getText());
                    }
                }.start();
            }
        });

    }


    public void append(String message){

        LinearLayout messageLayout
                = (LinearLayout)View.inflate	(this, R.layout.message, null);

        messageInLayout.addView(messageLayout);

        if(message.indexOf(clientName) == -1){
            ( (TextView)  (messageLayout.findViewById(R.id.left_message))   ).setText(message);
        }else{
            ( (TextView) ( messageLayout.findViewById(R.id.right_message))   ).setText(message);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        System.out.println("ChatActivity.onDestory()");

        new Thread() {
            @Override
            public void run() {
                chatClientSocketThread.sendMessgeToServer("400:");
            }
        }.start();

        if( chatClientSocketThread != null){
            chatClientSocketThread.onDestroy();
        }
    }

    @Override
    public void onBackPressed() {

        new EndAlertDialog(this).showEndDialog();

    }
}