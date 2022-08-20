package com.example.mangmacs.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mangmacs.adapter.AddOnsAdapter;
import com.example.mangmacs.adapter.BarbequeAdapter;
import com.example.mangmacs.adapter.GrilledAdapter;
import com.example.mangmacs.R;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.model.ProductListModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GrilledActivity extends AppCompatActivity {
    private RecyclerView recyclerView,recyclerViewBbq,recyclerViewAddOns;
    private LinearLayout grilledSiomaiLayout, barbequeLayout, addOnsLayout;
    private List<ProductListModel> grilledList;
    private ApiInterface apiInterface;
    private GrilledAdapter grilledAdapter;
    private BarbequeAdapter barbequeAdapter;
    private AddOnsAdapter addOnsAdapter;
    private TextView btnArrowBack;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int countGrilledSiomai = 0;
    private int countBarbeque = 0;
    private int countAddOns = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grilled);
        grilledSiomaiLayout = findViewById(R.id.grilledSiomaiLayout);
        barbequeLayout = findViewById(R.id.barbequeLayout);
        addOnsLayout = findViewById(R.id.addOnsLayout);
        btnArrowBack = findViewById(R.id.arrow_back);
        //emptyProduct = findViewById(R.id.emptyProduct);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerViewBbq = findViewById(R.id.recyclerViewBbq);
        recyclerViewAddOns = findViewById(R.id.recyclerViewAddOns);
        recyclerViewBbq.setHasFixedSize(true);
        recyclerView.setHasFixedSize(true);
        recyclerViewAddOns.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewBbq.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAddOns.setLayoutManager(new LinearLayoutManager(this));
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        ShowGrilledSiomaiLists();
        ShowBarbequeLists();
        ShowAddOnsLists();
        Back();

    }

    private void Back() {
        //back arrow button
        btnArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GrilledActivity.this,home_activity.class));
            }
        });
    }
    private void  ShowGrilledSiomaiLists() {
        grilledSiomaiLayout.setVisibility(View.GONE);
        Call<List<ProductListModel>> call= apiInterface.getGrilled();
        call.enqueue(new Callback<List<ProductListModel>>() {
            @Override
            public void onResponse(Call<List<ProductListModel>> call, Response<List<ProductListModel>> response) {
                grilledList = response.body();
                grilledAdapter = new GrilledAdapter(GrilledActivity.this,grilledList);
                recyclerView.setAdapter(grilledAdapter);
                countGrilledSiomai = grilledAdapter.getItemCount();
                if(countGrilledSiomai == 0){
                    grilledSiomaiLayout.setVisibility(View.GONE);
                }else{
                    grilledSiomaiLayout.setVisibility(View.VISIBLE);
                }
                refreshGrilledSiomai();
            }

            @Override
            public void onFailure(Call<List<ProductListModel>> call, Throwable t) {

            }
        });
    }
    private void ShowBarbequeLists() {
        barbequeLayout.setVisibility(View.GONE);
        Call<List<ProductListModel>> call= apiInterface.getBarbeque();
        call.enqueue(new Callback<List<ProductListModel>>() {
            @Override
            public void onResponse(Call<List<ProductListModel>> call, Response<List<ProductListModel>> response) {
                grilledList = response.body();
                barbequeAdapter = new BarbequeAdapter(GrilledActivity.this,grilledList);
                recyclerViewBbq.setAdapter(barbequeAdapter);
                countBarbeque = barbequeAdapter.getItemCount();
                if(countBarbeque == 0){
                    barbequeLayout.setVisibility(View.GONE);
                }else{
                    barbequeLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<ProductListModel>> call, Throwable t) {

            }
        });
    }
    private void ShowAddOnsLists() {
        addOnsLayout.setVisibility(View.GONE);
        Call<List<ProductListModel>> call = apiInterface.getAddOns();
        call.enqueue(new Callback<List<ProductListModel>>() {
            @Override
            public void onResponse(Call<List<ProductListModel>> call, Response<List<ProductListModel>> response) {
                grilledList = response.body();
                addOnsAdapter = new AddOnsAdapter(GrilledActivity.this,grilledList);
                recyclerViewAddOns.setAdapter(addOnsAdapter);
                countAddOns = addOnsAdapter.getItemCount();
                if(countAddOns == 0){
                    addOnsLayout.setVisibility(View.GONE);
                }else{
                    addOnsLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<ProductListModel>> call, Throwable t) {

            }
        });
    }
   private void refreshGrilledSiomai() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Call<List<ProductListModel>> call= apiInterface.getGrilled();
                call.enqueue(new Callback<List<ProductListModel>>() {
                    @Override
                    public void onResponse(Call<List<ProductListModel>> call, Response<List<ProductListModel>> response) {
                        grilledList = response.body();
                        grilledAdapter = new GrilledAdapter(GrilledActivity.this,grilledList);
                        recyclerView.setAdapter(grilledAdapter);
                        countGrilledSiomai = grilledAdapter.getItemCount();
                        if(countGrilledSiomai == 0){
                            grilledSiomaiLayout.setVisibility(View.GONE);
                        }else{
                            grilledSiomaiLayout.setVisibility(View.VISIBLE);
                        }
                        refreshGrilledSiomai();
                    }

                    @Override
                    public void onFailure(Call<List<ProductListModel>> call, Throwable t) {

                    }
                });
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        //
       Call<List<ProductListModel>> callBbq= apiInterface.getBarbeque();
       callBbq.enqueue(new Callback<List<ProductListModel>>() {
           @Override
           public void onResponse(Call<List<ProductListModel>> call, Response<List<ProductListModel>> response) {
               grilledList = response.body();
               barbequeAdapter = new BarbequeAdapter(GrilledActivity.this,grilledList);
               recyclerViewBbq.setAdapter(barbequeAdapter);
               countBarbeque = barbequeAdapter.getItemCount();
               if(countBarbeque == 0){
                   barbequeLayout.setVisibility(View.GONE);
               }else{
                   barbequeLayout.setVisibility(View.VISIBLE);
               }

           }

           @Override
           public void onFailure(Call<List<ProductListModel>> call, Throwable t) {

           }
       });
        //
       Call<List<ProductListModel>> callAddOns = apiInterface.getAddOns();
       callAddOns.enqueue(new Callback<List<ProductListModel>>() {
           @Override
           public void onResponse(Call<List<ProductListModel>> call, Response<List<ProductListModel>> response) {
               grilledList = response.body();
               addOnsAdapter = new AddOnsAdapter(GrilledActivity.this,grilledList);
               recyclerViewAddOns.setAdapter(addOnsAdapter);
               countAddOns = addOnsAdapter.getItemCount();
               if(countAddOns == 0){
                   addOnsLayout.setVisibility(View.GONE);
               }else{
                   addOnsLayout.setVisibility(View.VISIBLE);
               }

           }

           @Override
           public void onFailure(Call<List<ProductListModel>> call, Throwable t) {

           }
       });
    }
}