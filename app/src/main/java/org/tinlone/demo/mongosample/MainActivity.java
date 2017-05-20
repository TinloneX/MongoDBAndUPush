package org.tinlone.demo.mongosample;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    String data = "default";
    MongoUtil util;
    TextView textView;
    MyTask task;
    /**
     * name
     */
    private EditText mEtName;
    /**
     * age
     */
    private EditText mEtAge;
    /**
     * find
     */
    private Button mFind;
    /**
     * insert
     */
    private Button mInsert;
    /**
     * update
     */
    private Button mUpdate;

    private CheckBox cbSex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        textView = (TextView) findViewById(R.id.tv);
        textView.setText(data);
        try {
            TLog.i("onCreate: 1");
            util = new MongoUtil("TestDB");
            task = new MyTask();
            task.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Convert Cursor To List.
     *
     * @param cursor Required DBCursor.
     * @return String List Result.
     */
    private List<String> convertCursorToList(DBCursor cursor) {
        List<String> results = new ArrayList<String>();
        while (cursor.hasNext()) {
            DBObject dbObject = cursor.next();
            for (String key : dbObject.keySet()) {
                results.add("{" + key + ":" + dbObject.get(key) + "}");
            }
        }
        return results;
    }

    private void initView() {
        mEtName = (EditText) findViewById(R.id.et_name);
        mEtAge = (EditText) findViewById(R.id.et_age);
        mFind = (Button) findViewById(R.id.find);
        mFind.setOnClickListener(this);
        mInsert = (Button) findViewById(R.id.insert);
        mInsert.setOnClickListener(this);
        mUpdate = (Button) findViewById(R.id.update);
        mUpdate.setOnClickListener(this);
        cbSex = (CheckBox) findViewById(R.id.sex);
    }

    private String name = "";
    private int age = 0;
    private boolean sex = true;
    private int index = 0;

    private void gotoDB() {
        name = mEtName.getText().toString();
        age = Integer.parseInt(mEtAge.getText().toString());
        sex = cbSex.isChecked();
        if (task != null) {
            task.cancel(true);
            task = null;
        }
        task = new MyTask();
        task.execute();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.find:
                index = 0;
                break;
            case R.id.insert:
                index = 1;
                break;
            case R.id.update:
                index = 2;
                break;
        }
        gotoDB();
    }

    private List<String> find() {
        TLog.i("onCreate: 2");
        DBObject where = new BasicDBObject();
        where.put("_id", false);
        where.put("name", true);
        where.put("age", true);
        where.put("sex", true);
        DBCursor cursor = util.findNoPage(null, where, "test");
        TLog.i("onCreate: 3");
        try {
            List<String> result = convertCursorToList(cursor);
            TLog.i(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private List<String> insert() {
        DBObject kid = new BasicDBObject();
        kid.put("name", name);
        kid.put("age", age);
        kid.put("sex", sex);
        util.insert(kid, "test");
        return find();
    }

    private List<String> update() {
        DBObject kid = new BasicDBObject();
        kid.put("age", 16);
        DBObject find = new BasicDBObject();
        find.put("$set", new BasicDBObject("age", age));
        util.update(kid, find, false, true, "test");
        return find();
    }

    class MyTask extends AsyncTask<Void, Void, List<String>> {

        @Override
        protected List<String> doInBackground(Void... params) {
            switch (index) {
                case 0:
                    return find();
                case 1:
                    return insert();
                case 2:
                    return update();
                default:
                    return new ArrayList<>();
            }
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            for (String s : strings) {
                TLog.i("onCreate: 4");
                textView.append("\n" + s);
            }
            textView.append("\n-----end------");
        }
    }

    @Override
    protected void onDestroy() {
        task.cancel(true);
        super.onDestroy();
    }
}
