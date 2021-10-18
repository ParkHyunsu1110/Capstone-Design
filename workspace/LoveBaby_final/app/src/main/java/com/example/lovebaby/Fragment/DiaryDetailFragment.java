package com.example.lovebaby.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.lovebaby.Model.MyBoardModel;
import com.example.lovebaby.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class DiaryDetailFragment extends Fragment {

    private String boardId = null;
    private FirebaseDatabase firebaseDatabase;
    private TextView textView_title, textView_description;
    private ImageView imageView;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diary_detail,container,false);

        imageView = (ImageView)view.findViewById(R.id.detail_imageView);
        textView_title = (TextView)view.findViewById(R.id.detail_title);
        textView_description = (TextView)view.findViewById(R.id.detail_content);

        Bundle bundle = getArguments();
        if(bundle != null) {
            boardId = bundle.getString("id");
            Log.i("아이디",boardId);
        }
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.getReference("myboard").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String uidKey = snapshot.getKey();
                    if(uidKey.equals(boardId)){
                        MyBoardModel myBoardModel = snapshot.getValue(MyBoardModel.class);
                        String description = myBoardModel.description;
                        String title = myBoardModel.title;
                        textView_description.setText(description);
                        textView_title.setText(title);
                        Glide.with(getContext()).load(myBoardModel.imageUri).into(imageView);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        return view;
    }
}
