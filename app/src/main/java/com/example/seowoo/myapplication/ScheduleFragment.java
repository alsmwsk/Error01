package com.example.seowoo.myapplication;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ScheduleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ScheduleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScheduleFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ScheduleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScheduleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScheduleFragment newInstance(String param1, String param2) {
        ScheduleFragment fragment = new ScheduleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    //0교시부터 13교시
    private AutoResizeTextView monday[] = new AutoResizeTextView[14];
    private AutoResizeTextView tuesday[] = new AutoResizeTextView[14];
    private AutoResizeTextView wednesday[] = new AutoResizeTextView[14];
    private AutoResizeTextView thursday[] = new AutoResizeTextView[14];
    private AutoResizeTextView friday[] = new AutoResizeTextView[14];
    private Schedule schedule = new Schedule();



    //프래그먼트가 생성될때 실행되는 함수
    //반복문을 사용하여 처리할수는 없는지 고민해봐야될거같다..
    @Override
    public void onActivityCreated(Bundle b)
    {
        super.onActivityCreated(b);

        for (int i = 0; i < 14; i++)
        {
            int getIDm = getResources().getIdentifier("monday"+i,"id","com.example.seowoo.myapplication");
            int getIDtu = getResources().getIdentifier("tuesday"+i,"id","com.example.seowoo.myapplication");
            int getIDwe = getResources().getIdentifier("wednesday"+i,"id","com.example.seowoo.myapplication");
            int getIDth = getResources().getIdentifier("thursday"+i,"id","com.example.seowoo.myapplication");
            int getIDf = getResources().getIdentifier("friday"+i,"id","com.example.seowoo.myapplication");

            monday[i] = (AutoResizeTextView)getView().findViewById(getIDm);
            tuesday[i] = (AutoResizeTextView)getView().findViewById(getIDtu);
            wednesday[i] = (AutoResizeTextView)getView().findViewById(getIDwe);
            thursday[i] = (AutoResizeTextView)getView().findViewById(getIDth);
            friday[i] = (AutoResizeTextView)getView().findViewById(getIDf);
        }

//        Button parsing = (Button)getView().findViewById(R.id.parsing);
//        parsing.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new BackgroundTask().execute();
//            }
//        });

        new BackgroundTask().execute();
//        monday[0] = (TextView)getView().findViewById(R.id.tu0);
//        monday[0] = (TextView)getView().findViewById(R.id.monday0);
//        monday[0] = (TextView)getView().findViewById(R.id.monday0);
//        monday[0] = (TextView)getView().findViewById(R.id.monday0);
//        monday[0] = (TextView)getView().findViewById(R.id.monday0);

    }

    //메인쓰레드는 ui를 못건드리고 별도의 쓰레드를 이용한다?
    class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        String targert;

        //실행하기 전에 사용되는 메소드
        @Override
        protected void onPreExecute() {

            try{
                targert = "http://alsmwsk3.dothome.co.kr/Android/ScheduleList.php?userID=" + URLEncoder.encode(MainActivity.userID,"UTF-8");
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

        //데이터가 만개 있는데 중간에 한번 실행되는 메소드
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        //해당 결과를 처리 할 수 있는 메소드..
        //데이터를 전부 다 받아온 후에 실행되는 메소드
        @Override
        protected void onPostExecute(String result) {


            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                String courseProfessor;
                String courseTime;
                String courseTitle;

                int courseID;
                while (count < jsonArray.length())
                {
                    JSONObject object = jsonArray.getJSONObject(count);
                    courseID = object.getInt("courseID");
                    courseProfessor = object.getString("courseProfessor");
                    courseTime = object.getString("courseTime");
                    courseTitle = object.getString("courseTitle");

                    schedule.addSchedule(courseTime, courseTitle, courseProfessor);
                    //adapter.notifyDataSetChanged();

                    count++;

                }

            }catch (Exception e){
                e.printStackTrace();;
            }

            schedule.setting(monday, tuesday, wednesday, thursday, friday, getContext());

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
