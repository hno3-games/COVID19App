package com.example.covid19app.oct;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

import com.example.covid19app.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link q_fragment10#newInstance} factory method to
 * create an instance of this fragment.
 */
public class q_fragment10 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RadioButton rb1;
    RadioButton rb2;
    Button button;
    int point;

    public q_fragment10() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment q_fragment10.
     */
    // TODO: Rename and change types and number of parameters
    public static q_fragment10 newInstance(String param1, String param2) {
        q_fragment10 fragment = new q_fragment10();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_q_fragment10, container, false);
        Bundle bundle = this.getArguments();
        point = bundle.getInt("point");
        System.out.println(point);
        rb1 = view.findViewById(R.id.radioButton);
        rb2 = view.findViewById(R.id.radioButton2);
        button = view.findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                execute();
            }
        });

        return view;
    }
    private void show_alert(String title, String message, String button_text) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setPositiveButton(button_text, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alert.show();
    }
    private boolean check_radios() {
        if (rb1.isChecked() || rb2.isChecked()) {
            return true;
        }
        return false;
    }
    private void replace_fragment() {
        Bundle bundle = new Bundle();
        bundle.putInt("point",point);
        Fragment fragment = new result_fragment();
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    private void increase_point() {
        Bundle bundle = this.getArguments();
        if (rb1.isChecked()) {
            try {
                point = bundle.getInt("point");
                point+=1;
            } catch (Exception e) {

            }
        }
    }
    public void execute() {
        boolean state = check_radios();
        if(state){
            increase_point();
            replace_fragment();
        }
        else{
            show_alert("Warning!","Please choose an option.","OK");
        }
    }
}