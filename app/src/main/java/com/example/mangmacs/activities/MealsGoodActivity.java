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

import com.example.mangmacs.adapter.BeefAdapter;
import com.example.mangmacs.adapter.ChickenAdapter;
import com.example.mangmacs.adapter.PigarPigarAdapter;
import com.example.mangmacs.adapter.PorkAdapter;
import com.example.mangmacs.R;
import com.example.mangmacs.adapter.RiceAdapter;
import com.example.mangmacs.adapter.SeafoodsAdapter;
import com.example.mangmacs.adapter.VegetableAdapter;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.model.ProductListModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealsGoodActivity extends AppCompatActivity {
    private RecyclerView recyclerViewPork,recyclerViewChicken,recyclerViewBeef,recyclerViewPigar,recyclerViewSeafoods,recyclerViewVegetable,recyclerViewRice;
    private LinearLayout porkLayout,chickenLayout,beefLayout,pigarPigarLayout,seafoodsLayout,vegetableLayout,riceLayout;
    private List<ProductListModel> mealsGoodList;
    private ApiInterface apiInterface;
    private PorkAdapter porkAdapter;
    private ChickenAdapter chickenAdapter;
    private BeefAdapter beefAdapter;
    private PigarPigarAdapter pigarPigarAdapter;
    private SeafoodsAdapter seafoodsAdapter;
    private VegetableAdapter vegetableAdapter;
    private RiceAdapter riceAdapter;
    private TextView btnArrowBack;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int countPork,countChicken,countBeef,countPigarPigar,countSeafoods,countVegetable,countRice = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals_good);
        btnArrowBack = findViewById(R.id.arrow_back);
        porkLayout = findViewById(R.id.porkLayout);
        chickenLayout = findViewById(R.id.chickenLayout);
        beefLayout = findViewById(R.id.beefLayout);
        pigarPigarLayout = findViewById(R.id.pigarPigarLayout);
        seafoodsLayout = findViewById(R.id.seafoodsLayout);
        vegetableLayout = findViewById(R.id.vegetableLayout);
        riceLayout = findViewById(R.id.riceLayout);
        recyclerViewPork = findViewById(R.id.recyclerViewPork);
        recyclerViewChicken = findViewById(R.id.recyclerViewChicken);
        recyclerViewBeef = findViewById(R.id.recyclerViewBeef);
        recyclerViewPigar = findViewById(R.id.recyclerViewPigarPigar);
        recyclerViewSeafoods = findViewById(R.id.recyclerViewSeafoods);
        recyclerViewVegetable = findViewById(R.id.recyclerViewVegatables);
        recyclerViewRice = findViewById(R.id.recyclerViewRice);
        recyclerViewPork.setHasFixedSize(true);
        recyclerViewChicken.setHasFixedSize(true);
        recyclerViewBeef.setHasFixedSize(true);
        recyclerViewPigar.setHasFixedSize(true);
        recyclerViewSeafoods.setHasFixedSize(true);
        recyclerViewVegetable.setHasFixedSize(true);
        recyclerViewRice.setHasFixedSize(true);
        recyclerViewPork.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewChicken.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewBeef.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewPigar.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewSeafoods.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewVegetable.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewRice.setLayoutManager(new LinearLayoutManager(this));
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        ShowPorkLists();
        ShowChickenLists();
        ShowBeefLists();
        ShowPigarPigarLists();
        ShowSeafoodsLists();
        ShowVegetableLists();
        ShowRiceLists();
        Back();
    }

    private void Back() {
        btnArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MealsGoodActivity.this,home_activity.class));
            }
        });
    }

    private void ShowPorkLists() {
        Call<List<ProductListModel>> call= apiInterface.getMealsGood();
        call.enqueue(new Callback<List<ProductListModel>>() {
            @Override
            public void onResponse(Call<List<ProductListModel>> call, Response<List<ProductListModel>> response) {
                mealsGoodList = response.body();
                porkAdapter = new PorkAdapter(MealsGoodActivity.this,mealsGoodList);
                recyclerViewPork.setAdapter(porkAdapter);
                countPork = porkAdapter.getItemCount();
                if (countPork == 0){
                    porkLayout.setVisibility(View.GONE);
                }
                else{
                    porkLayout.setVisibility(View.VISIBLE);
                }
                refresh();
            }

            @Override
            public void onFailure(Call<List<ProductListModel>> call, Throwable t) {

            }
        });
    }
    private void ShowChickenLists(){
        Call<List<ProductListModel>> call= apiInterface.getChicken();
        call.enqueue(new Callback<List<ProductListModel>>() {
            @Override
            public void onResponse(Call<List<ProductListModel>> call, Response<List<ProductListModel>> response) {
                mealsGoodList = response.body();
                chickenAdapter = new ChickenAdapter(MealsGoodActivity.this,mealsGoodList);
                recyclerViewChicken.setAdapter(chickenAdapter);
                countChicken = chickenAdapter.getItemCount();
                if(countChicken == 0){
                    chickenLayout.setVisibility(View.GONE);
                }
                else{
                    chickenLayout.setVisibility(View.VISIBLE);
                }
                refresh();
            }

            @Override
            public void onFailure(Call<List<ProductListModel>> call, Throwable t) {

            }
        });
    }
    private void ShowBeefLists(){
        Call<List<ProductListModel>> call= apiInterface.getBeef();
        call.enqueue(new Callback<List<ProductListModel>>() {
            @Override
            public void onResponse(Call<List<ProductListModel>> call, Response<List<ProductListModel>> response) {
                mealsGoodList = response.body();
                beefAdapter = new BeefAdapter(MealsGoodActivity.this,mealsGoodList);
                recyclerViewBeef.setAdapter(beefAdapter);
                countBeef = beefAdapter.getItemCount();
                if(countBeef == 0){
                    beefLayout.setVisibility(View.GONE);
                }
                else{
                    beefLayout.setVisibility(View.VISIBLE);
                }
                refresh();
            }

            @Override
            public void onFailure(Call<List<ProductListModel>> call, Throwable t) {

            }
        });
    }
    private void ShowPigarPigarLists(){
        Call<List<ProductListModel>> call= apiInterface.getPigarPigar();
        call.enqueue(new Callback<List<ProductListModel>>() {
            @Override
            public void onResponse(Call<List<ProductListModel>> call, Response<List<ProductListModel>> response) {
                mealsGoodList = response.body();
                pigarPigarAdapter = new PigarPigarAdapter(MealsGoodActivity.this,mealsGoodList);
                recyclerViewPigar.setAdapter(pigarPigarAdapter);
                countPigarPigar = pigarPigarAdapter.getItemCount();
                if(countPigarPigar == 0){
                    pigarPigarLayout.setVisibility(View.GONE);
                }
                else{
                    pigarPigarLayout.setVisibility(View.VISIBLE);
                }
                refresh();
            }

            @Override
            public void onFailure(Call<List<ProductListModel>> call, Throwable t) {

            }
        });
    }
    private void ShowSeafoodsLists(){
        Call<List<ProductListModel>> call= apiInterface.getSeafoods();
        call.enqueue(new Callback<List<ProductListModel>>() {
            @Override
            public void onResponse(Call<List<ProductListModel>> call, Response<List<ProductListModel>> response) {
                mealsGoodList = response.body();
                seafoodsAdapter = new SeafoodsAdapter(MealsGoodActivity.this,mealsGoodList);
                recyclerViewSeafoods.setAdapter(seafoodsAdapter);
                countSeafoods = seafoodsAdapter.getItemCount();
                if(countSeafoods == 0){
                    seafoodsLayout.setVisibility(View.GONE);
                }
                else{
                    seafoodsLayout.setVisibility(View.VISIBLE);
                }
                refresh();
            }

            @Override
            public void onFailure(Call<List<ProductListModel>> call, Throwable t) {

            }
        });
    }
    private void ShowVegetableLists(){
        Call<List<ProductListModel>> call= apiInterface.getVegetable();
        call.enqueue(new Callback<List<ProductListModel>>() {
            @Override
            public void onResponse(Call<List<ProductListModel>> call, Response<List<ProductListModel>> response) {
                mealsGoodList = response.body();
                vegetableAdapter = new VegetableAdapter(MealsGoodActivity.this,mealsGoodList);
                recyclerViewVegetable.setAdapter(vegetableAdapter);
                countVegetable = vegetableAdapter.getItemCount();
                if(countVegetable == 0){
                    vegetableLayout.setVisibility(View.GONE);
                }
                else{
                    vegetableLayout.setVisibility(View.VISIBLE);
                }
                refresh();
            }

            @Override
            public void onFailure(Call<List<ProductListModel>> call, Throwable t) {

            }
        });
    }
    private void ShowRiceLists(){
        Call<List<ProductListModel>> call= apiInterface.getRice();
        call.enqueue(new Callback<List<ProductListModel>>() {
            @Override
            public void onResponse(Call<List<ProductListModel>> call, Response<List<ProductListModel>> response) {
                mealsGoodList = response.body();
                riceAdapter = new RiceAdapter(MealsGoodActivity.this,mealsGoodList);
                recyclerViewRice.setAdapter(riceAdapter);
                countRice = riceAdapter.getItemCount();
                if(countRice == 0){
                    riceLayout.setVisibility(View.GONE);
                }
                else{
                    riceLayout.setVisibility(View.VISIBLE);
                }
                refresh();
            }

            @Override
            public void onFailure(Call<List<ProductListModel>> call, Throwable t) {

            }
        });
    }
    public void refresh(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //Call Pork
                Call<List<ProductListModel>> call= apiInterface.getMealsGood();
                call.enqueue(new Callback<List<ProductListModel>>() {
                    @Override
                    public void onResponse(Call<List<ProductListModel>> call, Response<List<ProductListModel>> response) {
                        mealsGoodList = response.body();
                        porkAdapter = new PorkAdapter(MealsGoodActivity.this,mealsGoodList);
                        recyclerViewPork.setAdapter(porkAdapter);
                        countPork = porkAdapter.getItemCount();
                        if (countPork == 0){
                            porkLayout.setVisibility(View.GONE);
                        }
                        else{
                            porkLayout.setVisibility(View.VISIBLE);
                        }
                        refresh();
                    }

                    @Override
                    public void onFailure(Call<List<ProductListModel>> call, Throwable t) {

                    }
                });
                //call chicken
                Call<List<ProductListModel>> callChicken= apiInterface.getMealsGood();
                callChicken.enqueue(new Callback<List<ProductListModel>>() {
                    @Override
                    public void onResponse(Call<List<ProductListModel>> call, Response<List<ProductListModel>> response) {
                        mealsGoodList = response.body();
                        chickenAdapter = new ChickenAdapter(MealsGoodActivity.this,mealsGoodList);
                        recyclerViewChicken.setAdapter(chickenAdapter);
                        countChicken = porkAdapter.getItemCount();
                        if(countChicken == 0){
                            chickenLayout.setVisibility(View.GONE);
                        }
                        else{
                            chickenLayout.setVisibility(View.VISIBLE);
                        }
                        refresh();
                    }

                    @Override
                    public void onFailure(Call<List<ProductListModel>> call, Throwable t) {

                    }
                });
                //call beef
                Call<List<ProductListModel>> callBeef= apiInterface.getBeef();
                callBeef.enqueue(new Callback<List<ProductListModel>>() {
                    @Override
                    public void onResponse(Call<List<ProductListModel>> call, Response<List<ProductListModel>> response) {
                        mealsGoodList = response.body();
                        beefAdapter = new BeefAdapter(MealsGoodActivity.this,mealsGoodList);
                        recyclerViewBeef.setAdapter(beefAdapter);
                        countBeef = beefAdapter.getItemCount();
                        if(countBeef == 0){
                            beefLayout.setVisibility(View.GONE);
                        }
                        else{
                            beefLayout.setVisibility(View.VISIBLE);
                        }
                        refresh();
                    }

                    @Override
                    public void onFailure(Call<List<ProductListModel>> call, Throwable t) {

                    }
                });
                //call pigar pigar
                Call<List<ProductListModel>> callPigarPigar= apiInterface.getPigarPigar();
                callPigarPigar.enqueue(new Callback<List<ProductListModel>>() {
                    @Override
                    public void onResponse(Call<List<ProductListModel>> call, Response<List<ProductListModel>> response) {
                        mealsGoodList = response.body();
                        pigarPigarAdapter = new PigarPigarAdapter(MealsGoodActivity.this,mealsGoodList);
                        recyclerViewPigar.setAdapter(pigarPigarAdapter);
                        countPigarPigar = pigarPigarAdapter.getItemCount();
                        if(countPigarPigar == 0){
                            pigarPigarLayout.setVisibility(View.GONE);
                        }
                        else{
                            pigarPigarLayout.setVisibility(View.VISIBLE);
                        }
                        refresh();
                    }

                    @Override
                    public void onFailure(Call<List<ProductListModel>> call, Throwable t) {

                    }
                });
                //call seafoods
                Call<List<ProductListModel>> callSeafoods= apiInterface.getSeafoods();
                callSeafoods.enqueue(new Callback<List<ProductListModel>>() {
                    @Override
                    public void onResponse(Call<List<ProductListModel>> call, Response<List<ProductListModel>> response) {
                        mealsGoodList = response.body();
                        seafoodsAdapter = new SeafoodsAdapter(MealsGoodActivity.this,mealsGoodList);
                        recyclerViewSeafoods.setAdapter(seafoodsAdapter);
                        countSeafoods = seafoodsAdapter.getItemCount();
                        if(countSeafoods == 0){
                            seafoodsLayout.setVisibility(View.GONE);
                        }
                        else{
                            seafoodsLayout.setVisibility(View.VISIBLE);
                        }
                        refresh();
                    }

                    @Override
                    public void onFailure(Call<List<ProductListModel>> call, Throwable t) {

                    }
                });
                //call vegetable
                Call<List<ProductListModel>> callVegetable= apiInterface.getVegetable();
                callVegetable.enqueue(new Callback<List<ProductListModel>>() {
                    @Override
                    public void onResponse(Call<List<ProductListModel>> call, Response<List<ProductListModel>> response) {
                        mealsGoodList = response.body();
                        vegetableAdapter = new VegetableAdapter(MealsGoodActivity.this,mealsGoodList);
                        recyclerViewVegetable.setAdapter(vegetableAdapter);
                        countVegetable = vegetableAdapter.getItemCount();
                        if(countVegetable == 0){
                            vegetableLayout.setVisibility(View.GONE);
                        }
                        else{
                            vegetableLayout.setVisibility(View.VISIBLE);
                        }
                        refresh();
                    }

                    @Override
                    public void onFailure(Call<List<ProductListModel>> call, Throwable t) {

                    }
                });
                //call rice
                Call<List<ProductListModel>> callRice= apiInterface.getRice();
                callRice.enqueue(new Callback<List<ProductListModel>>() {
                    @Override
                    public void onResponse(Call<List<ProductListModel>> call, Response<List<ProductListModel>> response) {
                        mealsGoodList = response.body();
                        riceAdapter = new RiceAdapter(MealsGoodActivity.this,mealsGoodList);
                        recyclerViewRice.setAdapter(riceAdapter);
                        countRice = riceAdapter.getItemCount();
                        if(countRice == 0){
                            riceLayout.setVisibility(View.GONE);
                        }
                        else{
                            riceLayout.setVisibility(View.VISIBLE);
                        }
                        refresh();
                    }

                    @Override
                    public void onFailure(Call<List<ProductListModel>> call, Throwable t) {

                    }
                });
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}