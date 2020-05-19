package com.example.xjtuhelper.ui.Map;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.xjtuhelper.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment {

    private View view,view2;
    private Button btn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        view = inflater.inflate(R.layout.fragment_map, null);
        btn = (Button) view.findViewById(R.id.button_map);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        btn.setOnClickListener(new View.OnClickListener() {

            //必须在onactivitycreated中写onclick的方法
            @Override
            public void onClick(View v) {
                FragmentActivity mcontent = getActivity();
                Intent intent = new Intent(mcontent,MapActivity.class);
                startActivity(intent);
            }
        });
    }

}