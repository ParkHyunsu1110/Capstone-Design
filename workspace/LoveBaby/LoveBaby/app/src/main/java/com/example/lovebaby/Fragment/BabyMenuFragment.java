package com.example.lovebaby.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lovebaby.HWActivity;
import com.example.lovebaby.MainActivity;
import com.example.lovebaby.Model.BabyInfoDTO;
import com.example.lovebaby.Model.UserModel;
import com.example.lovebaby.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BabyMenuFragment extends Fragment {

    private String babyName;
    private Button vaccine, babymenu_hwgraph;
    private String birthday;
    private RecyclerView recyclerView2;
    private List<BabyInfoDTO> babyinfoDTOS= new ArrayList<>();
    private List<String> uidlist = new ArrayList<>();
    private FirebaseDatabase firebaseDatabase2;
    private FirebaseStorage firebaseStorage2;
    private String partneruid = null;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_babymenu,container,false);

        vaccine = (Button)view.findViewById(R.id.babymenu_vaccine);
        babymenu_hwgraph = (Button)view.findViewById(R.id.babymenu_hwgraph);

        firebaseDatabase2 = FirebaseDatabase.getInstance();
        firebaseStorage2 = FirebaseStorage.getInstance();

        recyclerView2 = view.findViewById(R.id.babyinfoView2);

        recyclerView2.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        BabyMenuFragment.BoardRecyclerViewAdapter boardRecyclerViewAdapter = new BabyMenuFragment.BoardRecyclerViewAdapter();
        recyclerView2.setAdapter(boardRecyclerViewAdapter);

        Bundle bundle = getArguments();
        if(bundle != null) babyName = bundle.getString("babyname");

        String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        firebaseDatabase2.getInstance().getReference("babyinfo").orderByChild("name").equalTo(babyName).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                //birthday = snapshot.getValue(BabyInfoDTO.class).birthday;
                //textView_babyname.setText(diffMonth(birthday)+"개월");

                partneruid = snapshot.getValue(UserModel.class).partneruid;

                firebaseDatabase2.getReference("babyinfo").addValueEventListener(new ValueEventListener() {
                    //데이터가 바뀔때마다 데이터가 넘어감 (옵저버 패턴)
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                        babyinfoDTOS.clear();
                        uidlist.clear();
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            BabyInfoDTO babyInfoDTO = snapshot.getValue(BabyInfoDTO.class);
                            if(babyInfoDTO.uid.equals(myUid) || babyInfoDTO.uid.equals(partneruid)) {
                                String uidKey = snapshot.getKey();

                                babyinfoDTOS.add(babyInfoDTO);
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

        vaccine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle(2);
                bundle.putString("babyName",babyName);
                bundle.putString("birthday",birthday);
                Fragment VaccineFragment = new VaccineFragment();
                VaccineFragment.setArguments(bundle);
                ((MainActivity)getActivity()).replaceFragment(VaccineFragment);
            }
        });

        babymenu_hwgraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),HWActivity.class);
                startActivity(intent);
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_info2,parent,false);
            return new BabyMenuFragment.BoardRecyclerViewAdapter.CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
            Glide.with(holder.itemView.getContext()).load(babyinfoDTOS.get(position).imageUrl).into(((BabyMenuFragment.BoardRecyclerViewAdapter.CustomViewHolder)holder).infoImg);
            ((BabyMenuFragment.BoardRecyclerViewAdapter.CustomViewHolder)holder).infoName.setText(babyinfoDTOS.get(position).name);
            ((CustomViewHolder)holder).infoAge.setText(diffMonth(birthday) + "개월");
            CustomViewHolder customViewHolder = ((CustomViewHolder)holder);

            /*customViewHolder.btn_goinfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("babyname",babyinfoDTOS.get(position).name);
                    Fragment babymenufragment = new BabyMenuFragment();
                    babymenufragment.setArguments(bundle);
                    ((MainActivity)getActivity()).replaceFragment(babymenufragment);
                }
            });*/

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
            return babyinfoDTOS.size();
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
            TextView infoName;
            TextView infoAge;
            public CustomViewHolder(View view) {
                super(view);
                infoImg = (ImageView)view.findViewById(R.id.info_img);
                infoName = (TextView)view.findViewById(R.id.info_name);
                infoAge = (TextView) view.findViewById(R.id.info_age);
            }
        }
    }

    public String diffMonth(String startDate){
        long allMonth = 0;
        try {
            SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
            String endDate = fm.format(System.currentTimeMillis());
            Date sDate = fm.parse(startDate);
            Date eDate = fm.parse(endDate);
            int sd2 = sDate.getMonth();

            long diff = eDate.getTime() - sDate.getTime();
            long diffDays = diff / (24 * 60 * 60 * 1000) ;

            long difMonth = (diffDays+1)/30;
            long chkNum = 0;
            int j=0;

            for(int i=sd2; j<difMonth; i++) {
                if(i==1 || i==3 || i==5 || i==7 || i==8 || i==10 || i==12) {
                    chkNum += 31;
                }else if(i==4 || i==6 || i==9 || i==11) {
                    chkNum += 30;
                }
                if(i==2) {
                    //윤달체크
                    if((sd2%400) == 0) {
                        chkNum+=29;
                    } else {
                        chkNum+=28;
                    }
                }
                j++;
                if(i>12) { i=1; j=j-1;}
            }
            allMonth = (chkNum+1)/30;

            if(diffDays < chkNum) {
                allMonth = allMonth-1;
            }

            System.out.println("날짜차이 =" + diffDays);
            System.out.println("총 차이  =" + chkNum);
            System.out.println("개월수 =" + difMonth);
            System.out.println("진짜개월수 =" + allMonth);

        } catch (Exception e) { }
        return Long.toString(allMonth);
    }
}
