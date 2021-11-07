package com.example.asac_test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllTasksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllTasksFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "body";
    private static final String ARG_PARAM2 = "status";
    private static final String ARG_PARAM3 = "title";


    // TODO: Rename and change types of parameters
    private String body;
    private String status;
    private String title;

    public AllTasksFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param body Parameter 1.
     * @param status Parameter 2.
     * @param title parameter 3.
     * @return A new instance of fragment AllTasksFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AllTasksFragment newInstance(String title,String body, String status) {
        AllTasksFragment fragment = new AllTasksFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, body);
        args.putString(ARG_PARAM2, status);
        args.putString(ARG_PARAM3, title);

        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            body = getArguments().getString(ARG_PARAM1);
            status = getArguments().getString(ARG_PARAM2);
            title = getArguments().getString(ARG_PARAM3);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_tasks, container, false);
    }
}