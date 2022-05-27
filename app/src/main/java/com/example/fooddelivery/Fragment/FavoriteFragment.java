package com.example.fooddelivery.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fooddelivery.Adapter.FavoriteAdapter;
import com.example.fooddelivery.Adapter.OrderAdapter;
import com.example.fooddelivery.R;
import com.example.fooddelivery.model.Favorite;
import com.example.fooddelivery.model.FoodItem;
import com.example.fooddelivery.model.Order;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    RecyclerView recyclerView;
    FavoriteAdapter adapter;
    private ArrayList<Favorite> favorites;
    private FirebaseFirestore firestore;
    private FirebaseAuth auth;
    private DatabaseReference reference;
    private FirebaseUser firebaseUser;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView=view.findViewById(R.id.rec_favorite);
        firestore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        firestore.setFirestoreSettings(new FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true).build());
        favorites=new ArrayList<>();
        adapter=new FavoriteAdapter(favorites);
        recyclerView.setAdapter(adapter);
        FoodItem item=new FoodItem();

        reference= FirebaseDatabase.getInstance().getReference("FavoriteList");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
//                    Favorite favorite=dataSnapshot.getValue(Favorite.class);
//                    favorites.add(favorite);
//                }
//                adapter.notifyDataSetChanged();
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });


        firestore.collection("Favorite")
                .whereEqualTo("UidFav",auth.getCurrentUser().getUid())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot snapshot:task.getResult().getDocuments()){
                        Favorite fav=snapshot.toObject(Favorite.class);
                        favorites.add(fav);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}