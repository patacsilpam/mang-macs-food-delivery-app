package com.example.mangmacs.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mangmacs.R;
import com.example.mangmacs.SharedPreference;
import com.example.mangmacs.activities.MenuActivty;
import com.example.mangmacs.adapter.CurrentOrdersAdapter;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.model.CartModel;
import com.example.mangmacs.model.CurrentOrdersModel;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrentOrders extends Fragment {
    private RecyclerView recyclerView;
    private List<CurrentOrdersModel> currentOrdersModel;
    private CurrentOrdersAdapter currentOrdersAdapter;
    private Button addOrder;
    private TextView email;
    private ProgressBar progressBar;
    private View emptyCart;
    private int countOrders;
    public CurrentOrders() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_orders, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = view.findViewById(R.id.spin_kit);
        emptyCart = view.findViewById(R.id.emptyOrders);
        addOrder = getView().findViewById(R.id.addOrder);
        email = view.findViewById(R.id.email);
        recyclerView = view.findViewById(R.id.currentOrderLists);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        showCurrentOrders();

    }

    private void showCurrentOrders() {
        Sprite circle = new Circle();
        progressBar.setIndeterminateDrawable(circle);
        progressBar.setVisibility(View.VISIBLE);
        String emailAddress = SharedPreference.getSharedPreference(getContext()).setEmail();
        emptyCart.setVisibility(View.GONE);
        ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        Call<List<CurrentOrdersModel>> getCurrentOrder = apiInterface.getCurrentOrders(emailAddress);
        getCurrentOrder.enqueue(new Callback<List<CurrentOrdersModel>>() {
            @Override
            public void onResponse(Call<List<CurrentOrdersModel>> call, Response<List<CurrentOrdersModel>> response) {
                progressBar.setVisibility(View.GONE);
                currentOrdersModel = response.body();
                currentOrdersAdapter = new CurrentOrdersAdapter(getActivity(),currentOrdersModel);
                recyclerView.setAdapter(currentOrdersAdapter);
                countOrders = recyclerView.getAdapter().getItemCount();
                emptyCart.setVisibility(View.GONE);
                if (countOrders == 0){
                    emptyCart.setVisibility(View.VISIBLE);
                    addOrder.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(getActivity(), MenuActivty.class));
                        }
                    });

                } else{
                    emptyCart.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<CurrentOrdersModel>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}