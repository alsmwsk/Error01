package com.example.seowoo.myapplication;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by seowoo on 2018-04-30.
 */

public class CourseListAdapter extends BaseAdapter {

    private Context context;
    private List<Course>  courseList;
    private android.support.v4.app.Fragment parent;
    private String userID = MainActivity.userID; // 매인액티비티에 있는 userID를 가져와서 해당 사용자의 ID를 변환할수있도록
    //해당 서버프로그램을 이용해서 시간표가 중복되는지 체크
    private Schedule schedule = new Schedule();
    private List<Integer> courseIDList;

    public CourseListAdapter(Context context, List<Course> courseList, android.support.v4.app.Fragment parent) {
        this.context = context;
        this.courseList = courseList;
        this.parent = parent;
        schedule = new Schedule();
        courseIDList = new ArrayList<Integer>();

        new BackgroundTask().execute();
    }

    @Override
    public int getCount() {
        return courseList.size();
    }

    @Override
    public Object getItem(int i) {
        return courseList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context,R.layout.course, null);
        TextView courseGrade = (TextView)v.findViewById(R.id.courseGrade);
        TextView courseTitle = (TextView)v.findViewById(R.id.courseTitle);
        TextView courseCredit = (TextView)v.findViewById(R.id.courseCredit);
        TextView courseDivide = (TextView)v.findViewById(R.id.courseDivide);
        TextView coursePersonnel = (TextView)v.findViewById(R.id.coursePersonnel);
        TextView courseProfessor = (TextView)v.findViewById(R.id.courseProfessor);
        TextView courseTime = (TextView)v.findViewById(R.id.courseTime);

        if(courseList.get(i).getCourseGrade().equals("제한 없음") || courseList.get(i).getCourseGrade().equals(""))
        {
            courseGrade.setText("모든 학년");
        }
        else{
            courseGrade.setText(courseList.get(i).getCourseGrade() + "학년");
        }
        courseGrade.setText(courseList.get(i).getCourseTitle());
        courseCredit.setText(courseList.get(i).getCourseCredit() + "학점");
        courseDivide.setText(courseList.get(i).getCourseDivide() + "분반");
        if(courseList.get(i).getCoursePersonnel() == 0)
        {
            coursePersonnel.setText("인원 제한 없음");
        }
        else{
            coursePersonnel.setText("제한 인원 : " + courseList.get(i).getCoursePersonnel() + "명");
        }

        if(courseList.get(i).getCourseProfessor().equals(""))
        {
            courseProfessor.setText("개인 연구");
        }
        else
        {
            courseProfessor.setText(courseList.get(i).getCourseProfessor() + "교수님");
        }
        courseTime.setText(courseList.get(i).getCourseTime() + "");


        v.setTag(courseList.get(i).getCourseID());

        Button addButton = (Button)v.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean validate = false;
                validate = schedule.validate(courseList.get(i).getCourseTime());
                //String userID = MainActivity.userID; //이거 헷갈림..
                //만약 자기가 신청했던 강의 속에서 현재 신청하려고 하는 강의가 아이디에 포함되어있다면
                //이미 신청했기 떄문에 신청할 수 없도록 한다.
                if (!alreadyIn(courseIDList, courseList.get(i).getCourseID()))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                    AlertDialog dialog = builder.setMessage("이미 추가한 강의입니다.")
                            .setPositiveButton("다시 시도", null)
                            .create();

                    dialog.show();
                }
                else if(validate == false)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                    AlertDialog dialog = builder.setMessage("시간표가 중복됩니다.")
                            .setPositiveButton("다시 시도", null)
                            .create();

                    dialog.show();
                }
                //시간표가 중복되지 않고 이미 추가한 강의가 아닌 경우에 추가가 가능하도록 한다.
                else{
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try
                            {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                if(success){
                                    //자신을 불러낸 courseFragment에서 알림창을 띄울수 있도록한다.
                                    AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                                    AlertDialog dialog = builder.setMessage("강의가 추가되었습니다.")
                                            .setPositiveButton("확인", null)
                                            .create();

                                    dialog.show();
                                    //강의 추가
                                    courseIDList.add(courseList.get(i).getCourseID());
                                    schedule.addSchedule(courseList.get(i).getCourseTime());
                                    //finish();
                                }else{
                                    AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                                    AlertDialog dialog = builder.setMessage("강의 추가에 실패하였습니다.")
                                            .setNegativeButton("확인", null)
                                            .create();

                                    dialog.show();
                                }
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                    };
                    //이거 뭐하는건지 모르겠음..
                    //어떤 회원이 어떤 강의를 듣는다는 그런 스케쥴 데이터를 스케쥴 데이터베이스에 넣어야하기 떄문에
                    AddRequest addRequest = new AddRequest(userID, courseList.get(i).getCourseID()+"",responseListener);
                    RequestQueue queue = Volley.newRequestQueue(parent.getActivity());
                    queue.add(addRequest);
                }


            }
        });
        return v;

    }

    class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        String targert;

        @Override
        protected void onPreExecute() {

            try{
                targert = "http://alsmwsk3.dothome.co.kr/Android/ScheduleList.php?userID=" + URLEncoder.encode(userID,"UTF-8");
            }catch (Exception e)
            {
                e.printStackTrace();
            }
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


            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                String courseProfessor;
                String courseTime;
                int courseID;
                while (count < jsonArray.length())
                {
                    JSONObject object = jsonArray.getJSONObject(count);
                    courseID = object.getInt("courseID");
                    courseProfessor = object.getString("courseProfessor");
                    courseTime = object.getString("courseTime");
                    courseIDList.add(courseID);
                    schedule.addSchedule(courseTime);
                    //adapter.notifyDataSetChanged();

                    count++;

                }

            }catch (Exception e){
                e.printStackTrace();;
            }

        }
    }

    //해당 courseID에 해당하는 그러한 강의 데이터가 이미 들어가 잇는 상태라면 체크해 줄수있는 메소드
    //뭐하는 건지 모르겠음..
   public boolean alreadyIn(List<Integer> courseIDList, int item)
   {
       for (int i = 0; i < courseIDList.size(); i++)
       {
           //하나라도 일치한다면
           if(courseIDList.get(i) == item)
           {
               return false;
           }
       }
       //그렇지 않다면 해당 데이터값을 추가할수있도록 한다.
       return true;
   }
}
