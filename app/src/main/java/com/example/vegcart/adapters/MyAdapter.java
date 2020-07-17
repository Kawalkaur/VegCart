package com.example.vegcart.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.vegcart.R;
import com.example.vegcart.activities.DetailActivity;
import com.example.vegcart.activities.VegetablesActivity;
import com.example.vegcart.models.Gmart;
import com.example.vegcart.models.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context mcontext;
    private List<Gmart> mData;
    RequestOptions option;
    int num = 1;
    String pricemultiply, VegId, VegName, VegNameHolder ;
    int Vegprice;
    public MyAdapter(Context mcontext, List<Gmart> mData) {
        this.mcontext = mcontext;
        this.mData = mData;
        option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(
                R.drawable.loading_shape);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mcontext);
        view = inflater.inflate(R.layout.my_vegetables_row,parent,false);
       // return new MyViewHolder(view);
       final MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
  //  final Gmart gmart =mData.get(position);
    holder.getAdapterPosition();
    Log.i("posgmart", String.valueOf(holder.getAdapterPosition()));
        String IDholder = mData.get(position).getProductID();
        holder.ProductID.setText(IDholder);
        Log.i("IDholder",IDholder);
        //holder.ProductID.setText(mData.get(position).getProductID());
        //Log.i("idholder",holder.ProductID.getText().toString());
        holder.VegetableName.setText(mData.get(position).getProductName());
        holder.VegetableNameHindi.setText(mData.get(position).getProductNameHindi());
        Glide.with(mcontext).load(mData.get(position).getImage_url()).apply(option).into(holder.imageViewVeg);

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext, DetailActivity.class);
                intent.putExtra("veg1",mData.get(position).getProductName());
                intent.putExtra("price1",mData.get(position).getProductPrice());
                intent.putExtra("image",mData.get(position).getImage_url());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mcontext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public long getItemId(int position) {
        Log.i("positem", String.valueOf(position));
        return (position);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView ProductID, VegetableName, VegetableNameHindi;
        Spinner spinner_Rate;
        ImageView imageViewVeg,Plus, Minus;
        TextView TextPrice,showCount;
        LinearLayout mainLayout, QuantityLayout;;
        ArrayAdapter<CharSequence> adapterRate;
        String selectedRate,priceStr,weightStr, ProductIDStr, getProductID,selectedPrice;
        int priceInt,weightInt,ratePosition = 0,PriceStr, WeightStr, TotalPrice, TotalWeight;
        ArrayList<String> WeightList, PriceList;
        Button AddBtn;
        RequestQueue requestQueue;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ProductID = itemView.findViewById(R.id.product_id);
            VegetableName = itemView.findViewById(R.id.product_name);
            VegetableNameHindi = itemView.findViewById(R.id.product_name_hindi);
            spinner_Rate = itemView.findViewById(R.id.spinner_rate);
            AddBtn = itemView.findViewById(R.id.addBtn);
            Plus = itemView.findViewById(R.id.cart_plus_img);
            Minus = itemView.findViewById(R.id.cart_minus_img);
            showCount = itemView.findViewById(R.id.cart_product_quantity_tv);
            TextPrice  = itemView.findViewById(R.id.textPrice);
            imageViewVeg = itemView.findViewById(R.id.thumbnail);
            mainLayout = itemView.findViewById(R.id.container);
            QuantityLayout = itemView.findViewById(R.id.quantityLayout);
            WeightList = new ArrayList<>();
            WeightList.add("Quantity");
            PriceList = new ArrayList<>();
            spinner_Rate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedPrice = spinner_Rate.getItemAtPosition(spinner_Rate.
                    getSelectedItemPosition()).toString();
                    Log.i("selectedPrice",selectedPrice);

                    priceStr  = selectedPrice.substring(2,4);
                    Log.i("s",priceStr);
                    priceInt = Integer.parseInt(priceStr);
                    Log.i("p",priceInt+"");

                 // if (WeightStr == 500) {
                 //      weightStr = selectedPrice.substring(7, 10);
                 //  }else {
                        weightStr =selectedPrice.substring(7,8);
                  //  }

                    Log.i("weightstr1", weightStr);

                    weightInt = Integer.parseInt(weightStr);
                    Log.i("weightInt",weightInt+"");

                    showCount.setText("");
                    QuantityLayout.setVisibility(View.GONE);
                    AddBtn.setVisibility(View.VISIBLE);
                    //getPrice();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            requestQueue = Volley.newRequestQueue(mcontext);
            getSpinner();

            AddBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    num = 1;
                    showCount.setText(num+"");
                    QuantityLayout.setVisibility(View.VISIBLE);
                    AddBtn.setVisibility(View.GONE);
                    TotalWeight = num * weightInt;

                    Log.i("totalWeight", TotalWeight+" ="+ num +"*"+ weightInt);

                    Log.i("weghtstr", String.valueOf(weightInt));
                    Log.i("nummm", String.valueOf(num));

                    TotalPrice = num * priceInt;
                    Log.i("Prc", String.valueOf(priceInt));
                    Log.i("totalPrice", TotalPrice+" ="+ num +"*"+ priceInt);
                    Toast.makeText(mcontext, "TotalWeight= " + TotalWeight+"\n"+"TotalPrice= "+ TotalPrice, Toast.LENGTH_SHORT).show();
                }
            });
            PlusMinus();
        }

        public void PlusMinus(){
                Plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    num = num+1;
                    showCount.setText(num +"");
                    TotalWeight = num * weightInt;
                    Log.i("totaladd", TotalWeight+" ="+ num +"*"+ weightInt);
                    Log.i("weghtstr", String.valueOf(weightInt));
                    TotalPrice = num * priceInt;
                    Log.i("Prc", String.valueOf(priceInt));
                    Log.i("totalPricea", TotalPrice+" ="+ num +"*"+ priceInt);
                    Toast.makeText(mcontext, "TotalWeight= " + TotalWeight+"\n"+"TotalPrice= "+
                            TotalPrice, Toast.LENGTH_SHORT).show();
                }
            });
            Minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*if (num < 0) {
                        num = 0;
                        showCount.setText(num + "");
                    }*/

                    if (num > 0) {
                        num = num-1;
                        showCount.setText(num + "");
                        TotalWeight = num * weightInt;
                        Log.i("totalminus", TotalWeight+" ="+ num +"*"+ weightInt);
                        TotalPrice = num * priceInt;
                        Log.i("Prc", String.valueOf(priceInt));
                        Log.i("totalPricem", TotalPrice+" ="+ num +"*"+ priceInt);
                        Toast.makeText(mcontext, "TotalWeight= " + TotalWeight+"\n"+"TotalPrice= "+
                                TotalPrice, Toast.LENGTH_SHORT).show();

                    }
                    if (num <= 0){
                        num = 0;
                        showCount.setText(num + "");
                        AddBtn.setVisibility(View.VISIBLE);
                        QuantityLayout.setVisibility(View.GONE);

                    }
                }
            });
        }

        void getSpinner(){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Util.Weight_PriceList,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray ja = new JSONArray(response);
                                if (ja.length()>0){
                                     for (int i=0; i< ja.length(); i++){
                                        JSONObject jsonObject = ja.getJSONObject(i);
                                        ProductIDStr =jsonObject.getString("Product_ID");
                                        WeightStr = jsonObject.getInt("Veg_Weight");
                                        PriceStr = jsonObject.getInt("Veg_Price");
                                        Log.i("pricestr", String.valueOf(PriceStr));
                                        Log.i("weightstr", String.valueOf(WeightStr));
                                        if (WeightStr == 500){
                                            WeightList.add("₹ "+PriceStr+" / "+WeightStr+" gram");
                                        }else {
                                            WeightList.add("₹ "+PriceStr+" / "+WeightStr+" Kg");
                                        }
                                  //     WeightList.add(""+WeightStr);
                                        spinner_Rate.setAdapter(new ArrayAdapter<>(mcontext,
                                                R.layout.support_simple_spinner_dropdown_item,
                                                WeightList));
                                        spinner_Rate.setSelection(1);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("ProId", ProductID.getText().toString());
                    return map;
                }
            };
            requestQueue.add(stringRequest);
        }
    }
        }


