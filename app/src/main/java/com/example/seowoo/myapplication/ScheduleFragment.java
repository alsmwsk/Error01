package com.example.seowoo.myapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


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
    private TextView monday[] = new TextView[14];
    private TextView tuesday[] = new TextView[14];
    private TextView wednesday[] = new TextView[14];
    private TextView thursday[] = new TextView[14];
    private TextView friday[] = new TextView[14];
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

            monday[i] = (TextView)getView().findViewById(getIDm);
            tuesday[i] = (TextView)getView().findViewById(getIDtu);
            wednesday[i] = (TextView)getView().findViewById(getIDwe);
            thursday[i] = (TextView)getView().findViewById(getIDth);
            friday[i] = (TextView)getView().findViewById(getIDf);
        }
//        monday[0] = (TextView)getView().findViewById(R.id.tu0);
//        monday[0] = (TextView)getView().findViewById(R.id.monday0);
//        monday[0] = (TextView)getView().findViewById(R.id.monday0);
//        monday[0] = (TextView)getView().findViewById(R.id.monday0);
//        monday[0] = (TextView)getView().findViewById(R.id.monday0);

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
