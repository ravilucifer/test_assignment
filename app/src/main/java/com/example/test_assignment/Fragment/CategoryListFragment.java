package com.example.test_assignment.Fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.test_assignment.Adapter.CategoryListAdapter;
import com.example.test_assignment.Model.CategoryListModel;
import com.example.test_assignment.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class CategoryListFragment extends Fragment {
RecyclerView category_recycler;
private static final String url = "https://en.wikipedia.org/w/api.php?action=query&list=allcategories&acprefix%20=List%20of&formatversion=2&format=json&accontinue=";
    ArrayList<CategoryListModel> categoryArrayList = new ArrayList<>();
    CategoryListAdapter customAdapter;
    NestedScrollView paginationScroll;
    String continueParam = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_category_list, container, false);
        initUI(v);
        return v;
    }

    private void initUI(View v) {
        category_recycler = v.findViewById(R.id.category_recycler);
        paginationScroll = v.findViewById(R.id.paginationScroll);
        bindData();
    }

    private void bindData() {
        getCategoryList(continueParam);

        paginationScroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(@NonNull NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    Log.e("TAG45", "onScrollChange: "+continueParam );
                    getCategoryList(continueParam);
                }
            }
        });
    }

    private void getCategoryList(String continue_Param) {
        try {
            final ProgressDialog loading = ProgressDialog.show(getContext(),"","Please Wait...",false,false);
            @SuppressLint("NotifyDataSetChanged") StringRequest request = new StringRequest(Request.Method.GET, url+continue_Param, response -> {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("batchcomplete").equals("true")) {
                        continueParam = jsonObject.getJSONObject("continue").getString("accontinue");
                        JSONArray categoryListArray = jsonObject.getJSONObject("query").getJSONArray("allcategories");
                        if (categoryListArray.length() > 0) {
                            categoryArrayList.clear();
                            for (int i = 0; i < categoryListArray.length(); i++) {
                                JSONObject objectCategoryList = categoryListArray.getJSONObject(i);
                                categoryArrayList.add(new CategoryListModel(
                                        objectCategoryList.getString("category")));
                            }
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                            category_recycler.setLayoutManager(linearLayoutManager);
                            customAdapter = new CategoryListAdapter(getContext(), categoryArrayList);
                            category_recycler.setAdapter(customAdapter);
                            customAdapter.notifyDataSetChanged();
                        }
                    } else {
                        Toast.makeText(getContext(), jsonObject.getString("batchcomplete"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                loading.dismiss();
            }, error -> error.printStackTrace()) {
                @Override
                protected Map<String, String> getParams() {
                    HashMap<String, String> param = new HashMap<>();
                    return param;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
            requestQueue.add(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}