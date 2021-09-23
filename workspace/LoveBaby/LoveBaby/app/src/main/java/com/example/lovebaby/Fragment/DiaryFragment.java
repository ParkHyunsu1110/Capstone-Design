package com.example.lovebaby.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.lovebaby.Model.ImageDTO;
import com.example.lovebaby.MainActivity;
import com.example.lovebaby.Model.UserModel;
import com.example.lovebaby.R;
import com.example.lovebaby.chat.MessageActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DiaryFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<ImageDTO> imageDTOS= new ArrayList<>();
    private List<ImageDTO> contents_get = new ArrayList<>();
    private List<String> uidlist = new ArrayList<>();
    private String oldestPostId = null;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth auth;
    private FirebaseStorage firebaseStorage;
    private Animation fab_open, fab_close;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab1, fab2;
    private boolean isLoading = false;
    private BoardRecyclerViewAdapter boardRecyclerViewAdapter;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diary,container,false);

        firebaseDatabase = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        boardRecyclerViewAdapter = new BoardRecyclerViewAdapter();
        recyclerView.setAdapter(boardRecyclerViewAdapter);

        fab = view.findViewById(R.id.fab);
        fab1 = view.findViewById(R.id.fab1);
        fab2 = view.findViewById(R.id.fab2);

        firebaseDatabase.getReference("images").limitToLast(10).addValueEventListener(new ValueEventListener() {
            //데이터가 바뀔때미디 데이터가 넘어감 (옵저버 패턴)
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                imageDTOS.clear();
                uidlist.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ImageDTO imageDTO = snapshot.getValue(ImageDTO.class);
                    String uidKey = snapshot.getKey();

                    imageDTOS.add(0,imageDTO);
                    contents_get.add(0,imageDTO);
                    uidlist.add(uidKey);
                }
                //oldestPostId = uidlist.get(0);
                boardRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        //fab 리스너
        fab_open = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fab_close);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim();
            }
        });
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //anim();
                ((MainActivity)getActivity()).replaceFragment(new WriteDiaryFragment());
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //anim();
                Toast.makeText(getActivity(), "나의 글 보기", Toast.LENGTH_SHORT).show();
            }
        });

        //무한스크롤 페이징
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();
                if(!recyclerView.canScrollVertically(1)){
                    if(contents_get.size()==10 && !isLoading){
                        if(layoutManager != null && layoutManager.findLastCompletelyVisibleItemPosition() == imageDTOS.size()-1){
                            if(imageDTOS.size() != 0)loadMore();
                            isLoading = true;
                        }
                    }
                }
            }

            @Override
            public void onScrollStateChanged(@NonNull @NotNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        return view;
    }

    private void loadMore() {
        imageDTOS.add(null);
        boardRecyclerViewAdapter.notifyItemChanged(imageDTOS.size()-1);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                imageDTOS.remove(imageDTOS.size()-1);
                int scrollPosition = imageDTOS.size();
                boardRecyclerViewAdapter.notifyItemRemoved(scrollPosition);

                firebaseDatabase.getReference().child("images").orderByKey().endAt(uidlist.get(0)).limitToLast(10).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        contents_get.clear();
                        uidlist.clear();
                        for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                            contents_get.add(0,dataSnapshot.getValue(ImageDTO.class));
                            uidlist.add(dataSnapshot.getKey());
                        }
                        if(contents_get.size()>1){
                            contents_get.remove(0);
                            imageDTOS.addAll(contents_get);
                            //oldestPostId = uidlist.get(0);
                            boardRecyclerViewAdapter.notifyDataSetChanged();
                            isLoading = false;
                        } else{
                            Toast.makeText(getActivity(),"마지막입니다.",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }
        }, 2000);
    }

    class BoardRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        private final int VIEW_TYPE_ITEM = 0;
        private final int VIEW_TYPE_LOADING = 1;

        @NonNull
        @NotNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

            if (viewType == VIEW_TYPE_ITEM) {
                //RecyclerView에서 ViewHolder를 동적으로 만듦.
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_board,parent,false);
                return new CustomViewHolder(view);
            } else{
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading,parent,false);
                return new LoadingViewHolder(view);
            }

        }

        @Override
        public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
            if(holder instanceof LoadingViewHolder) showLoadingView((LoadingViewHolder)holder,position);
            else {
                FirebaseDatabase.getInstance().getReference().child("users").orderByChild("uid").equalTo(imageDTOS.get(position).uid).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                        Glide.with(holder.itemView.getContext())
                                .load(snapshot.getValue(UserModel.class).profileImageUrl)
                                .apply(new RequestOptions().circleCrop())
                                .into(((CustomViewHolder)holder).imageView_profile);
                        ((CustomViewHolder)holder).textView_profile.setText(snapshot.getValue(UserModel.class).nickname);
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
                ((CustomViewHolder)holder).textView.setText(imageDTOS.get(position).title);
                ((CustomViewHolder)holder).textView2.setText(imageDTOS.get(position).description);

                Glide.with(holder.itemView.getContext()).load(imageDTOS.get(position).imageUri).into(((CustomViewHolder)holder).imageView);
                ((CustomViewHolder)holder).starBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onStarClicked(firebaseDatabase.getReference("images").child(uidlist.get(position)));
                    }
                });

                if (imageDTOS.get(position).stars.containsKey(auth.getCurrentUser().getUid())) {
                    ((CustomViewHolder)holder).starBtn.setImageResource(R.drawable.favorite);
                } else
                    ((CustomViewHolder)holder).starBtn.setImageResource(R.drawable.favorite_border);

                /*((CustomViewHolder)holder).delBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delete_content(position);
                    }
                });*/
                ((CustomViewHolder)holder).linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(imageDTOS.get(position).uid.equals(auth.getCurrentUser().getUid())) {
                            Toast.makeText(getActivity(),"자신의 글입니다.",Toast.LENGTH_SHORT).show();
                        } else {
                            Intent intent = new Intent(v.getContext(), MessageActivity.class);
                            intent.putExtra("destinationUid", imageDTOS.get(position).uid);
                            startActivity(intent);
                        }
                    }
                });
            }
        }

        private void showLoadingView(LoadingViewHolder holder, int position) {
        }

        @Override
        public int getItemCount() {
            return imageDTOS == null ? 0 : imageDTOS.size();
        }

        @Override
        public int getItemViewType(int position) {
            return imageDTOS.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
        }

        //증가 카운터와 같이 동시 수정으로 인해 손상될 수 있는 데이터를 다루는 경우 트랜잭션 작업을 사용
        private void onStarClicked(DatabaseReference postRef) {
            postRef.runTransaction(new Transaction.Handler() {
                @Override
                public Transaction.Result doTransaction(MutableData mutableData) {
                    ImageDTO imageDTO = mutableData.getValue(ImageDTO.class);
                    if (imageDTO == null) {
                        return Transaction.success(mutableData);
                    }

                    if (imageDTO.stars.containsKey(auth.getCurrentUser().getUid())) {
                        // 배열에 버튼 누른 uid 있으면 discount
                        imageDTO.starCount = imageDTO.starCount - 1;
                        imageDTO.stars.remove(auth.getCurrentUser().getUid());
                    } else {
                        // 없으면 count
                        imageDTO.starCount = imageDTO.starCount + 1;
                        imageDTO.stars.put(auth.getCurrentUser().getUid(), true);
                    }

                    // Set value and report transaction success
                    mutableData.setValue(imageDTO);
                    return Transaction.success(mutableData);
                }

                @Override
                public void onComplete(DatabaseError databaseError, boolean committed,
                                       DataSnapshot currentData) {

                }
            });
        }

        private void delete_content(int position) {
            firebaseStorage.getReference().child("images").child(imageDTOS.get(position).imageName).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    firebaseDatabase.getReference().child("images").child(uidlist.get(position)).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getActivity(),"삭제 완료",Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            Toast.makeText(getActivity(),"삭제 fail",Toast.LENGTH_SHORT).show();
                        }
                    });                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    Toast.makeText(getActivity(),"삭제 실패",Toast.LENGTH_SHORT).show();
                }
            });


        }

        private class CustomViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView, starBtn, delBtn, imageView_profile;
            TextView textView, textView2, textView_profile;
            LinearLayout linearLayout;

            public CustomViewHolder(View view) {
                super(view);
                imageView = (ImageView)view.findViewById(R.id.item_imageView);
                starBtn = (ImageView)view.findViewById(R.id.starBtn);
                //delBtn = (ImageView)view.findViewById(R.id.delBtn);
                textView = (TextView)view.findViewById(R.id.item_textView);
                textView2 = (TextView)view.findViewById(R.id.item_textView2);
                imageView_profile = (ImageView)view.findViewById(R.id.board_profile_image);
                textView_profile = (TextView)view.findViewById(R.id.board_profile_textview);
                linearLayout = (LinearLayout)view.findViewById(R.id.board_profile_layout);
            }
        }

        private class LoadingViewHolder extends RecyclerView.ViewHolder {
            private ProgressBar progressBar;
            public LoadingViewHolder(View view) {
                super(view);
                progressBar = (ProgressBar)view.findViewById(R.id.loading_progressbar);
            }
        }
    }

    //fab animation
    public void anim() {

        if (isFabOpen) {
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isFabOpen = false;
        } else {
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isFabOpen = true;
        }
    }
}
