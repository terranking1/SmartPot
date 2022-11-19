package com.example.smartpot;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AddFragment extends Fragment {


    public static AddFragment newInstance(int number) {
        AddFragment addFragment = new AddFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("number", number);
        addFragment.setArguments(bundle);
        return addFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_add, container, false);

        TextView tvFragmentNum = view.findViewById(R.id.tv_FragmentNum);
        Button btnAdd = view.findViewById(R.id.btn_Add);

        if (getArguments() != null) {
            int num = getArguments().getInt("number");
            tvFragmentNum.setText(Integer.toString(num));
        }
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("add", 1);
                startActivity(intent);

            }
        });
        return view;
    }
}