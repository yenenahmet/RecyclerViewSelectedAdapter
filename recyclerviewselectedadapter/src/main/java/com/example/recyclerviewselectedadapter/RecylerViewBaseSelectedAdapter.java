package com.example.recyclerviewselectedadapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ahmet on 26.11.2017.
 */

public abstract class RecylerViewBaseSelectedAdapter <T, E extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<E>{

    private List<T> items;
    private List<Integer> SelectedList;
    private View ClickView;
    public OnLongListenerItem onLongListenerItem;

    public void setClickView(View view){
        this.ClickView = view ;
    }
    public interface OnLongListenerItem{
        void onLongClick(int position);
        void onClick(int position);
    }
    public void setOnLongListenerItem(OnLongListenerItem onLongListenerItem){
        this.onLongListenerItem = onLongListenerItem;
    }
    public RecylerViewBaseSelectedAdapter(){
        items = new ArrayList<>();
        SelectedList = new ArrayList<>();
    }
    @Override
    public void onBindViewHolder(E holder, final int position) {

        ClickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLongListenerItem.onClick(position);
            }
        });
        ClickView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onLongListenerItem.onLongClick(position);
                return true;
            }
        });
    }
    @Override
    public int getItemCount() {
        return items.size();
    }
    public void setItems(List<T> items) {
        if (items != null) {
            this.items = items;
            notifyDataSetChanged();
        }
    }
    public List<T> getItems() {
        return this.items;
    }
    public T getItem(int position){
        return  items.get(position);
    }
    public void addItem(T item) {
        this.items.add(item);
        notifyItemInserted(this.items.size()-1);
    }
    public void addItems(List<T> items) {
        if (items != null) {
            int x =this.items.size();
            this.items.addAll(items);
            notifyItemRangeInserted(x,items.size());
        }
    }
    public void removeItem(int position){
        this.items.remove(new Integer(position).intValue());
        notifyItemRemoved(position);
    }

    // Selected //
    public  List<T> RemoveSelected(){
        List<T> tmp = new ArrayList<>();
        for(Integer value:SelectedList) {
            tmp.add(getItem(value));
            removeItem(value);
        }
        SelectedList.clear();
        return tmp;
    }
    public void ClearSelectedList(){
        SelectedList.clear();
        notifyDataSetChanged();
    }
    public int SelectedItem(int position){
        int values = SelectedKontrol(position);
        if(values == -1){
            SelectedList.add(position);
        }else{
            SelectedList.remove(values);
        }
        notifyItemChanged(position);
        return SelectedList.size();
    }
    public boolean SingleSelectedAgain(int positon){
        if(!SelectedList.isEmpty() && SelectedList.get(0) == positon && SelectedList.size()==1)
            return true;
        return false;
    }
    public int SelectedKontrol(int position){
        for(int i =0; i <SelectedList.size(); i++){
            if(SelectedList.get(i) == position){
                return i;
            }
        }
        return -1;
    }
    public int getSelectedItemSize(){
        return SelectedList.size() ;
    }


}