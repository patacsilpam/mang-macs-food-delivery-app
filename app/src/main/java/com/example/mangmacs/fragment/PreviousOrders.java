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

import com.example.mangmacs.R;
import com.example.mangmacs.SharedPreference;
import com.example.mangmacs.activities.MenuActivty;
import com.example.mangmacs.adapter.PreviousOrderAdapter;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.model.CurrentOrdersModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreviousOrders extends Fragment {
    private RecyclerView previousOrderLists;
    private List<CurrentOrdersModel> previousOrderModel;
    private PreviousOrderAdapter previousOrderAdapter;
    private View emptyOrders;
    private Button addOrder;
    private int countCart;
    public PreviousOrders() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_previous_orders, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        emptyOrders = view.findViewById(R.id.emptyOrders);
        addOrder = getView().findViewById(R.id.addOrder);
        previousOrderLists = view.findViewById(R.id.previousOrderLists);
        previousOrderLists.setHasFixedSize(true);
        previousOrderLists.setLayoutManager(new LinearLayoutManager(getActivity()));
        String email = SharedPreference.getSharedPreference(getActivity()).setEmail();
        ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        Call<List<CurrentOrdersModel>> callDeliveredOrders = apiInterface.getDeliveredOrders(email);
        callDeliveredOrders.enqueue(new Callback<List<CurrentOrdersModel>>() {
            @Override
            public void onResponse(Call<List<CurrentOrdersModel>> call, Response<List<CurrentOrdersModel>> response) {
                previousOrderModel = response.body();
                previousOrderAdapter = new PreviousOrderAdapter(getActivity(),previousOrderModel);
                previousOrderLists.setAdapter(previousOrderAdapter);
                countCart =  previousOrderLists.getAdapter().getItemCount();
                emptyOrders.setVisibility(View.GONE);
                if (countCart == 0){
                    emptyOrders.setVisibility(View.VISIBLE);
                    addOrder.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(getActivity(), MenuActivty.class));
                        }
                    });

                } else{
                    emptyOrders.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<List<CurrentOrdersModel>> call, Throwable t) {

            }
        });
    }
}