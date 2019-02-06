package com.example.ahmet.multiplebarcodereader.mylib.RecyclerViewHelper;

import android.support.annotation.NonNull;
import android.widget.Filter;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseFilter<T> extends Filter {

    private List<T> allItems;
    private final Object mLock = new Object();

    protected BaseFilter(List<T> filterItems) {
        if(filterItems==null){
            throw new NullPointerException();
        }
        this.allItems = new ArrayList<>(filterItems);
    }

    @Override
    protected FilterResults performFiltering(final CharSequence constraint) {
        synchronized (mLock) {
            final FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 2 ) {
                final List<T> arrayList = new ArrayList<>();
                final String constLowerCase = constraint.toString().toLowerCase();
                final String controlParameter =constLowerCase.substring(0,2);
                final String lowerCase= constLowerCase.substring(2,constLowerCase.length());
                for (T values : allItems) {
                    if (values != null) {
                        final T model = getFilterItem(lowerCase, values,controlParameter);
                        if (model != null) {
                            arrayList.add(model);
                        }
                    }
                }
                results.values = arrayList;
                results.count = arrayList.size();
            } else {
                results.values = allItems;
                results.count = allItems.size();
            }
            return results;
        }
    }

    @Override
    protected void publishResults(CharSequence constraint, final FilterResults results) {
        synchronized (mLock) {
            if (results != null && results.values != null) {
                final ArrayList<T> arrayList = new ArrayList<>((ArrayList<T>)results.values);
                pubslishResults(arrayList);
            }
        }
    }


    protected abstract void pubslishResults(@NonNull ArrayList<T> results);

    protected abstract T getFilterItem(@NonNull String constLowerCase, @NonNull T value,@NonNull String controlParameter);

    public void clear() {
        if(allItems!=null){
            allItems.clear();
            allItems =null;
        }
    }

    protected void setAllItems(List<T> items){
        if(items!=null){
            clear();
            this.allItems = new ArrayList<>(items);
        }
    }

    protected T isContainsLower(final T model ,final String value ,final String constLowerCase){
         if(value.toLowerCase().contains(constLowerCase)){
             return model;
         }
         return null;
    }
    protected T isEqualsLower(final T model, final String value ,final String constLowerCase){
        if(value.toLowerCase().equals(constLowerCase)){
            return model;
        }
        return null;
    }
	    public void removeItem(final int pos){
        if(pos>-1 && !allItems.isEmpty()){
            allItems.remove(pos);
        }
    }
    public int getItemPosition(final T item){
        if(item!=null && !allItems.isEmpty()){
            int i=0,pos=-1;
            for(T value:allItems){
                if(item.equals(value)){
                    pos=i;
                    break;
                }
                i++;
            }
            return pos;
        }
        return -1;
    }
    public int removeItem(final T item){
        if(item!=null && !allItems.isEmpty()){
            allItems.remove(item);
            return getItemPosition(item);
        }
        return -1;
    }

    public void restoreItem(final int pos, final T item){
        if(pos>-1 && item!=null){
            allItems.add(pos,item);
        }
    }
}
