package com.example.ahmet.benimbutcem.MyLib;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Ahmet on 26.11.2017.
 */
public abstract class RecyclerViewBaseSelectedAdapter <T, E extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<E>{

    private List<T> items;
    private List<Integer> SelectedList;

    public  RecyclerViewBaseSelectedAdapter(){
        this.items = new ArrayList<T>();
        this.SelectedList = new ArrayList<>();
    }

    @Override
    public synchronized int getItemCount() {
        return this.items.size();
    }
    public synchronized void setItems(List<T> items) {
        if (items != null) {
            this.items = items;
            notifyDataSetChanged();
        }
    }
    public synchronized List<T> getItems() {
        return this.items;
    }
    public synchronized T getItem(int position){
        return  this.items.get(new Integer(position).intValue());
    }
    public synchronized void addItem(T item) {
        this.items.add(item);
        notifyItemInserted(this.items.size()-1);
    }
    public synchronized void addItems(List<T> items) {
        if (items != null) {
            int x =this.items.size();
            this.items.addAll(items);
            notifyItemRangeInserted(x,items.size());
        }
    }
    public synchronized void removeItem(int position){
        this.items.remove(new Integer(position).intValue());
        notifyItemRemoved(position);
    }

    // Selected //
    public  synchronized List<T> RemoveSelected(){
        List<T> tmp = new ArrayList<>();
        for(Integer value:this.SelectedList) {
            tmp.add(getItem(value));
        }
        for(int i = this.SelectedList.size()-1; i>=0; i--){
            removeItem(SelectedList.get(i));
        }
        this.SelectedList.clear();
        return tmp;
    }
    public synchronized void ClearSelectedList(){
        this.SelectedList.clear();
        notifyDataSetChanged();
    }
    public synchronized int SelectedItem(int position){
        int values = SelectedKontrol(position);
        if(values == -1){
            this.SelectedList.add(position);
        }else{
            this.SelectedList.remove(values);
        }
        notifyItemChanged(position);
        return this.SelectedList.size();
    }
    public synchronized boolean SingleSelectedAgain(int positon){
        if(!this.SelectedList.isEmpty() && this.SelectedList.get(0) == positon && this.SelectedList.size()==1)
            return true;
        return false;
    }
    public synchronized int SelectedKontrol(int position){
        for(int i =0; i <this.SelectedList.size(); i++){
            if(this.SelectedList.get(i) == position){
                return i;
            }
        }
        return -1;
    }
    public synchronized int getSelectedItemSize(){
        return this.SelectedList.size() ;
    }

}
