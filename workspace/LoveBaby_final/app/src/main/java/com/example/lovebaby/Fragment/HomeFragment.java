package com.example.lovebaby.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lovebaby.CreateBabyInfo;
import com.example.lovebaby.MainActivity;
import com.example.lovebaby.Model.BabyInfoModel;
import com.example.lovebaby.Model.UserModel;
import com.example.lovebaby.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<BabyInfoModel> babyinfoModels = new ArrayList<>();
    private List<String> uidlist = new ArrayList<>();
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;
    private String partneruid = null;
    private Dialog dialog;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        recyclerView = view.findViewById(R.id.babyinfoView);
        recyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        BoardRecyclerViewAdapter boardRecyclerViewAdapter = new BoardRecyclerViewAdapter();
        recyclerView.setAdapter(boardRecyclerViewAdapter);
        dialog = new Dialog(view.getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_detailinfo);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(view.getContext(), CreateBabyInfo.class));
            }
        });

        String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        firebaseDatabase.getReference("users").orderByChild("uid").equalTo(myUid).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                partneruid = snapshot.getValue(UserModel.class).partneruid;

                firebaseDatabase.getReference("babyinfo").addValueEventListener(new ValueEventListener() {
                    //데이터가 바뀔때미디 데이터가 넘어감 (옵저버 패턴)
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                        babyinfoModels.clear();
                        uidlist.clear();
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            BabyInfoModel babyInfoModel = snapshot.getValue(BabyInfoModel.class);
                            if(babyInfoModel.uid.equals(myUid) || babyInfoModel.uid.equals(partneruid)) {
                                String uidKey = snapshot.getKey();

                                babyinfoModels.add(babyInfoModel);
                                uidlist.add(uidKey);
                            }
                        }
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

        return view;
    }

    class BoardRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        @NonNull
        @NotNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

            //RecyclerView에서 ViewHolder를 동적으로 만듦.
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_info,parent,false);
            return new BoardRecyclerViewAdapter.CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
            Glide.with(holder.itemView.getContext()).load(babyinfoModels.get(position).imageUrl).into(((CustomViewHolder)holder).infoImg);
            ((CustomViewHolder)holder).infoName.setText(babyinfoModels.get(position).name);
            CustomViewHolder customViewHolder = ((CustomViewHolder)holder);
            customViewHolder.infoBirthday.setText(babyinfoModels.get(position).birthday);
            customViewHolder.btn_goinfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("babyname", babyinfoModels.get(position).name);
                    Fragment babymenufragment = new BabyMenuFragment();
                    babymenufragment.setArguments(bundle);
                    ((MainActivity)getActivity()).replaceFragment(babymenufragment);
                }
            });
            customViewHolder.btn_detailinfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog(babyinfoModels.get(position).name,babyinfoModels.get(position).sex,babyinfoModels.get(position).birthday,
                            babyinfoModels.get(position).birthtime,babyinfoModels.get(position).bloodtype);
                }
            });

            //delete_btn
            /*((CustomViewHolder)holder).delBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delete_content(position);
                }
            });*/
        }

        @Override
        public int getItemCount() {
            return babyinfoModels.size();
        }
        /*
                private void delete_content(int position) {
                    firebaseStorage.getReference().child("babyinfo").child(babyinfoDTOS.get(position).imageName).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            firebaseDatabase.getReference().child("babyinfo").child(uidlist.get(position)).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(HomeActivity.this,"삭제 완료",Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull @NotNull Exception e) {
                                    Toast.makeText(HomeActivity.this,"삭제 fail",Toast.LENGTH_SHORT).show();
                                }
                            });                }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            Toast.makeText(HomeActivity.this,"삭제 실패",Toast.LENGTH_SHORT).show();
                        }
                    });


                }
        */
        private class CustomViewHolder extends RecyclerView.ViewHolder {
            ImageView infoImg;
            TextView infoName,infoBirthday;
            Button btn_goinfo, btn_detailinfo;

            public CustomViewHolder(View view) {
                super(view);
                infoImg = (ImageView)view.findViewById(R.id.info_img);
                infoName = (TextView)view.findViewById(R.id.info_name);
                btn_goinfo = (Button)view.findViewById(R.id.goinfo);
                infoBirthday = (TextView)view.findViewById(R.id.info_birthday);
                btn_detailinfo = (Button)view.findViewById(R.id.detailinfo);
            }
        }
    }

    private void showDialog(String name, String sex, String birthday, String birthtime, String bloodtype){
        TextView textViewname, textViewsex, textViewbirthday, textViewbirthtime, textViewbloodtype;
        textViewname = dialog.findViewById(R.id.detailinfo_name);
        textViewsex = dialog.findViewById(R.id.detailinfo_sex);
        textViewbirthday = dialog.findViewById(R.id.detailinfo_birthday);
        textViewbirthtime = dialog.findViewById(R.id.detailinfo_birthtime);
        textViewbloodtype = dialog.findViewById(R.id.detailinfo_bloodtype);
        textViewname.setText(name+"의 정보");
        textViewbirthday.setText("생일: "+birthday);
        textViewbirthtime.setText("태어난 시간: "+birthtime);
        textViewbloodtype.setText("혈액형: "+ bloodtype);
        textViewsex.setText("성별: "+sex);
        dialog.show();
        dialog.findViewById(R.id.detailinfo_noBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
