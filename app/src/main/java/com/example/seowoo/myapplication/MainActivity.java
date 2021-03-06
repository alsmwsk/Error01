package com.example.seowoo.myapplication;

import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    Button courseButton;
    Button statisticsButton;
    Button scheduleButton;
    LinearLayout notice;
    private ListView noticeListView;
    private NoticeListAdapter adapter;
    private List<Notice> noticeList;
    public static String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //화면을 세로로 고정
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        userID = getIntent().getStringExtra("userID");

        noticeListView = (ListView)findViewById(R.id.noticeListView);
        noticeList = new ArrayList<Notice>();

        adapter = new NoticeListAdapter(getApplicationContext(),  noticeList);
        //noticeListView.setAdapter(adapter);

        imageView = (ImageView)findViewById(R.id.main_logo);
        final Button courseButton = (Button)findViewById(R.id.courseButton);
        final Button statisticsButton = (Button)findViewById(R.id.statisticsButton);
        final Button scheduleButton = (Button)findViewById(R.id.scheduleButton);
        final LinearLayout notice = (LinearLayout)findViewById(R.id.notice);


        Glide.with(getApplicationContext()).load(R.drawable.hoseo).centerCrop().override(125,125).into(imageView);

        courseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notice.setVisibility(View.GONE);
                courseButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                statisticsButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                scheduleButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new CourseFragment());
                fragmentTransaction.commit();
            }
        });

        statisticsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notice.setVisibility(View.GONE);
                courseButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                statisticsButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                scheduleButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new StatisticsFragment());
                fragmentTransaction.commit();
            }
        });

        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notice.setVisibility(View.GONE);
                courseButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                statisticsButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                scheduleButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new ScheduleFragment());
                fragmentTransaction.commit();
            }
        });

        new BackgroundTask().execute();
    }

    class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        String targert;

        @Override
        protected void onPreExecute() {
            targert = "http://alsmwsk3.dothome.co.kr/Android/NoticeList.php";

        }

        //좀 많이 어려움
        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url = new URL(targert);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                //넘어오는 결과값을 저장할수 있도록 하는 메소드..
                InputStream inputStream = httpURLConnection.getInputStream();
                //해당 InputStream에 있는 값을 읽을 수 있도록 하기 위한 것
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();

                //bufferedReader로 읽어온 값을 temp에다 넣은 것이 null이 아니면 temp에다가 값을 직업넣고 개행을해준다.?
                while ((temp = bufferedReader.readLine()) != null)
                {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return  stringBuilder.toString().trim();

            }catch (Exception e){

            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        //해당 결과를 처리 할 수 있는 메소드..
        @Override
        protected void onPostExecute(String result) {

            noticeListView.setAdapter(adapter);
            try {
                //JSONObject jsonObject = new JSONObject(result);
                JSONArray  jsonArray = new JSONArray(result);
                //response에 각각의 공지사항 데이터가 담긴다.
                //json_encode($arr)
                //JSONArray jsonArray = jsonObject.getJSONArray("response");
                //JSONArray jsonArray = jsonObject.getJSONArray("jsonArray");


                int count = 0;
                String noticeContent, noticeName, noticeDate;
                while (count < jsonArray.length())
                {
                    JSONObject object = jsonArray.getJSONObject(count);
                    noticeContent = object.getString("noticeContent");
                    noticeName = object.getString("noticeName");
                    noticeDate = object.getString("noticeContent");
                    Notice notice = new Notice(noticeContent, noticeName, noticeDate);
                    noticeList.add(notice);
                    //adapter.notifyDataSetChanged();


                    count++;

//
                }

            }catch (Exception e){
                e.printStackTrace();;
            }
            noticeListView.setAdapter(adapter);
        }
    }

    //메인액티비티 부분에서 두번 뒤로가기버튼을 누르면 어플리케이션 종료되도록 한다...?
    private long lastTimeBackPressed;

    //뒤로가기버튼을 누른후 1.5초 이내에 한번더 뒤로가기 버튼을 눌렀을 경우에 실행되는 메소드..
    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis() - lastTimeBackPressed < 1500)
        {
            finish();
            return;
        }
        Toast.makeText(this, "'뒤로' 버튼을 한 번 더 눌러 종료합니다.", Toast.LENGTH_SHORT).show();
        lastTimeBackPressed = System.currentTimeMillis();
    }
}
