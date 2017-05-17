package org.tinlone.demo.mongosample;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.umeng.message.PushAgent;

/**
 * Created by Administrator on 2017/5/17 0017.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        PushAgent.getInstance(this).onAppStart();
    }
}
