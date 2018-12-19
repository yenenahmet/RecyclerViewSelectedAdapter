package com.example.ahmet.benimbutcem.MyLib.RecyclerViewHelper;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Ahmet on 26.11.2017.
 */
public abstract class RecyclerViewBaseSelectedAdapter<T, E extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<E> {

    private List<T> items;
    private List<Integer> SelectedList;

    protected RecyclerViewBaseSelectedAdapter() {
        this.items = new ArrayList<T>();
        this.SelectedList = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return  items==null ? 0: items.size();
    }

    public  void setItems(List<T> items) {
        if (items != null && !items.isEmpty()) {
            this.items = items;
            notifyDataSetChanged();
        }
    }

    public void setChanged(int position ,T item){
        if(item !=null && position>-1 && !this.items.isEmpty() ){
            this.items.set(position,item);
            notifyItemChanged(position,item);
        }
    }
    protected  List<T> getItems() {
        return this.items;
    }

    public  T getItem(int position) {
        return this.items.get(position);
    }

    public  void addItem(T item) {
        if (item != null) {
            this.items.add(item);
            final int position =this.items.size() - 1;
            notifyItemInserted(position);
        }
    }

    public  void addItems(List<T> items) {
        if (items != null && !items.isEmpty()) {
            final int x = this.items.size()-1;
            this.items.addAll(items);
            final int itemcount = items.size() -1;
            notifyItemRangeInserted(x, itemcount);
        }
    }

    private void removeItem(int position) {
        if(position>-1){
            this.items.remove(position);
            notifyItemRemoved(position);
        }
    }

    // Selected //
    public  List<T> RemoveSelected() {
        List<T> tmp = new ArrayList<>();
        for (Integer value : this.SelectedList) {
            tmp.add(getItem(value));
        }
        Collections.sort(SelectedList);
        for (int i = this.SelectedList.size() - 1; i >= 0; i--) {
            removeItem(SelectedList.get(i));
        }
        this.SelectedList.clear();
        return tmp;
    }

    public void ClearSelectedList() {
        this.SelectedList.clear();
        notifyDataSetChanged();
    }

    public int SelectedItem(int position) {
        final int values = SelectedKontrol(position);
        if (values == -1) {
            this.SelectedList.add(position);
        } else {
            this.SelectedList.remove(values);
        }
        notifyItemChanged(position);
        return this.SelectedList.size();
    }

    public boolean SingleSelectedAgain(int positon) {
        return positon>-1 &&!this.SelectedList.isEmpty() && this.SelectedList.get(0) == positon && this.SelectedList.size() == 1;
    }

    protected int SelectedKontrol(int position) {
        if(position>-1){
            for (int i = 0; i < this.SelectedList.size(); i++) {
                if (this.SelectedList.get(i) == position) {
                    return i;
                }
            }
        }
        return -1;
    }

    public int getSelectedItemSize() {
        return this.SelectedList.size();
    }

    protected void close(){
        if(items != null){
            items.clear();
            items = null;
        }
        if(SelectedList !=null){
            SelectedList.clear();
            SelectedList = null;
        }
    }

}
