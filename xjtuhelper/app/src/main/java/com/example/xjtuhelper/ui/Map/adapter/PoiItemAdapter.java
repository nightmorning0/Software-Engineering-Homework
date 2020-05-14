package com.example.xjtuhelper.ui.Map.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.services.core.PoiItem;
import com.example.xjtuhelper.R;
import com.example.xjtuhelper.ui.Map.SearchActivity;
import com.example.xjtuhelper.ui.Map.utils.Constants;

import java.util.List;



public class PoiItemAdapter extends RecyclerView.Adapter<PoiItemAdapter.ViewHolder>{

    private List<PoiItem> poiItemList;
    private SearchActivity activity;

    static class ViewHolder extends RecyclerView.ViewHolder{

        View poiItemView;
        TextView poiItemName;
        TextView poiItemAddress;

        public ViewHolder(View view){
            super(view);
            poiItemView=view;
            poiItemName=(TextView)view.findViewById(R.id.name);
            poiItemAddress=(TextView)view.findViewById(R.id.address);
        }
    }

    public PoiItemAdapter(List<PoiItem> poiItemList, SearchActivity activity){
        this.poiItemList=poiItemList;
        this.activity=activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tip_poi,
                parent, false);
        final ViewHolder holder=new ViewHolder(view);
        holder.poiItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                PoiItem poiItem=poiItemList.get(position);
                Intent intent=new Intent();
                intent.putExtra("resultType", Constants.RESULT_POIITEM);
                intent.putExtra("result", poiItem);
                activity.setResult(AppCompatActivity.RESULT_OK, intent);
                activity.finish();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PoiItem poiItem=poiItemList.get(position);
        holder.poiItemName.setText(poiItem.getTitle());
        holder.poiItemAddress.setText(poiItem.getSnippet());
    }

    @Override
    public int getItemCount() {
        return poiItemList.size();
    }
}
