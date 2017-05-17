package org.tinlone.demo.mongosample;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    String data = "default";
    MongoUtil util;
    TextView textView;
    MyTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    class MyTask extends AsyncTask<Void, Void, List<String>> {

        @Override
        protected List<String> doInBackground(Void... params) {
            TLog.i("onCreate: 2");
            DBObject where = new BasicDBObject();
            where.put("_id", false);
            where.put("name", true);
            where.put("age", true);
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

        @Override
        protected void onPostExecute(List<String> strings) {
            for (String s : strings) {
                TLog.i("onCreate: 4");
                textView.append(s);
            }

        }
    }

    @Override
    protected void onDestroy() {
        task.cancel(true);
        super.onDestroy();
    }
}
