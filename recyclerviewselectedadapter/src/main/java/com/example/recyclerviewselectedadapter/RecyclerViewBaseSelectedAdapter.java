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
    private List<Integer> selectedList;

    protected RecyclerViewBaseSelectedAdapter() {
        this.selectedList = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return  this.items==null ? 0: this.items.size();
    }

    public  void setItems(List<T> items) {
        if (items != null) {
            clearItems();
            this.items = items;
            notifyDataSetChanged();
        }
    }

    public void setChanged(int position ,T item){
        if(item !=null && position>-1 &&this.items!= null &&!this.items.isEmpty() ){
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
        if (item != null && this.items !=null) {
            this.items.add(item);
            final int position =this.items.size() - 1;
            notifyItemInserted(position);
        }
    }

    public  void addItems(List<T> items) {
        if (items != null && !items.isEmpty() && this.items !=null) {
            final int x = this.items.size()-1;
            this.items.addAll(items);
            final int itemcount = items.size() -1;
            notifyItemRangeInserted(x, itemcount);
        }
    }

    private void removeItem(int position) {
        if(position>-1 && this.items !=null && !this.items.isEmpty()){
            this.items.remove(position);
            notifyItemRemoved(position);
        }
    }

    // Selected //
    public  List<T> removeSelected() {
        List<T> tmp = new ArrayList<>();
        for (Integer value : this.selectedList) {
            tmp.add(getItem(value));
        }
        Collections.sort(selectedList);
        for (int i = this.selectedList.size() - 1; i >= 0; i--) {
            removeItem(selectedList.get(i));
        }
        this.selectedList.clear();
        return tmp;
    }

    public void clearSelectedList() {
        this.selectedList.clear();
        notifyDataSetChanged();
    }

    public int selectedItem(int position) {
        final int values = selectedControl(position);
        if (values == -1) {
            this.selectedList.add(position);
        } else {
            this.selectedList.remove(values);
        }
        notifyItemChanged(position);
        return this.selectedList.size();
    }

    public boolean singleSelectedAgain(int positon) {
        return positon>-1 &&!this.selectedList.isEmpty() && this.selectedList.get(0) == positon && this.selectedList.size() == 1;
    }

    protected int selectedControl(int position) {
        if(position>-1){
            for (int i = 0; i < this.selectedList.size(); i++) {
                if (this.selectedList.get(i) == position) {
                    return i;
                }
            }
        }
        return -1;
    }

    public int getSelectedItemSize() {
        return this.selectedList.size();
    }

    protected void close(){
        clearItems();
        if(this.selectedList !=null){
            this.selectedList.clear();
            this.selectedList = null;
        }
    }

    private void clearItems(){
        if(this.items != null){
            this.items.clear();
            this.items = null;
        }
    }
}
