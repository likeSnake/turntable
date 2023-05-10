package net.nice.turntable.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import net.nice.turntable.R;

public class FeedbackAct extends AppCompatActivity implements View.OnClickListener{


    private ImageView back;
    private EditText content;
    private Button submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_feedback);

        initUI();
        initListener();
    }

    public void initUI(){
        back = findViewById(R.id.back);
        content = findViewById(R.id.content);
        submit = findViewById(R.id.submit);


    }

    public void initListener(){
        back.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;

            case R.id.submit:
                Toast.makeText(this, "Thank you for your feedback, and we take your suggestions seriously！", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }
}