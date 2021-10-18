package com.example.lovebaby.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lovebaby.MainActivity;
import com.example.lovebaby.Model.MyBoardModel;
import com.example.lovebaby.Model.UserModel;
import com.example.lovebaby.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyDiaryFragment extends Fragment {

    private String babyName,birthday;
    private RecyclerView recyclerView;
    private boardRecyclerViewAdapter boardRecyclerViewAdapter;
    private FirebaseDatabase firebaseDatabase;
    private List<MyBoardModel> myBoardModels = new ArrayList<>();
    private List<MyBoardModel> contents_get = new ArrayList<>();
    private List<String> uidlist = new ArrayList<>();
    private String partneruid = null;
    private FloatingActionButton fab;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frargment_mydiary,container,false);

        firebaseDatabase = FirebaseDatabase.getInstance();

        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView_mydiary);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(inflater.getContext(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        boardRecyclerViewAdapter = new boardRecyclerViewAdapter();
        recyclerView.setAdapter(boardRecyclerViewAdapter);
        fab = view.findViewById(R.id.fab_write);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle(2);
                bundle.putString("birthday",birthday);
                bundle.putString("babyName",babyName);
                Fragment WriteDiaryFragment = new WriteDiaryFragment();
                WriteDiaryFragment.setArguments(bundle);
                ((MainActivity)getActivity()).replaceFragment(WriteDiaryFragment);
            }
        });

        Bundle bundle = getArguments();
        if(bundle != null) {
            babyName = bundle.getString("babyName");
            birthday = bundle.getString("birthday");
        }
        showBoard(babyName);
        return view;
    }

    public void showBoard(String babyName){
        String myuid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        firebaseDatabase.getReference("users").orderByChild("uid").equalTo(myuid).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                partneruid = snapshot.getValue(UserModel.class).partneruid;
                firebaseDatabase.getReference("myboard").limitToLast(20).addValueEventListener(new ValueEventListener() {
                    //데이터가 바뀔때미디 데이터가 넘어감 (옵저버 패턴)
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                        myBoardModels.clear();
                        uidlist.clear();
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            MyBoardModel myBoardModel = snapshot.getValue(MyBoardModel.class);
                            if((myBoardModel.babyname.equals(babyName) && myBoardModel.uid.equals(myuid))
                                    || (myBoardModel.uid.equals(partneruid) && myBoardModel.babyname.equals(babyName))) {
                                String uidKey = snapshot.getKey();
                                myBoardModels.add(0, myBoardModel);
                                contents_get.add(0, myBoardModel);
                                uidlist.add(uidKey);
                            }
                        }
                        Collections.reverse(uidlist);
                        boardRecyclerViewAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private class boardRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        @NonNull
        @NotNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_myboard,parent,false);
            return new boardRecyclerViewAdapter.CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
            FirebaseDatabase.getInstance().getReference().child("users").orderByChild("uid").equalTo(myBoardModels.get(position).uid).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                    Glide.with(holder.itemView.getContext()).load(myBoardModels.get(position).imageUri).into(((CustomViewHolder)holder).imageView);
                    ((CustomViewHolder)holder).imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String id = uidlist.get(position);
                            Bundle bundle = new Bundle();
                            bundle.putString("id",id);
                            Fragment DiaryDetailFragment = new DiaryDetailFragment();
                            DiaryDetailFragment.setArguments(bundle);
                            ((MainActivity)getActivity()).replaceFragment(DiaryDetailFragment);
                        }
                    });
                }

                @Override
                public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

                }

                @Override
                public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        }

        @Override
        public int getItemCount() {
            return myBoardModels == null ? 0 : myBoardModels.size();
        }

        private class CustomViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            LinearLayout linearLayout;

            public CustomViewHolder(View view) {
                super(view);
                imageView = (ImageView)view.findViewById(R.id.myboard_imageView);
                linearLayout = (LinearLayout)view.findViewById(R.id.myboard_layout);
            }
        }
    }
}
