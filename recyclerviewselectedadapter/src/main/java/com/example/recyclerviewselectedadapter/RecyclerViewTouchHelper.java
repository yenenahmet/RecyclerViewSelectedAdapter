package com.example.ahmet.multiplebarcodereader.mylib.RecyclerViewHelper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import com.example.ahmet.multiplebarcodereader.R;

public class RecyclerViewTouchHelper {
    private RecyclerView recyclerView;
    private TouchSwiped touchSwiped;
    private Paint paint;
    private Bitmap iconLeft,iconRight;

    public RecyclerViewTouchHelper(RecyclerView recyclerView,TouchSwiped touchSwiped,final Context context){
        this.recyclerView = recyclerView;
        this.touchSwiped = touchSwiped;
        paint = new Paint();
        iconLeft =BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_edit_white);
        iconRight =BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_delete_white);
    }

    public void build(){
        initView();
    }
    public void close(){
        paint =null;
        iconRight =null;
        iconLeft =null;
        touchSwiped =null;
        recyclerView =null;
    }
    private void initView(){
        final ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder,final int direction) {
                if(touchSwiped!=null){
                    final int position = viewHolder.getAdapterPosition();
                    touchSwiped.onSwiped(position,direction);
                }
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
                    final View itemView = viewHolder.itemView;
                    final float bottom = (float)itemView.getBottom();
                    final float top = (float)itemView.getTop();
                    final float left = (float)itemView.getLeft();
                    final float right = (float)itemView.getRight();
                    final float height = bottom - top;
                    final float width = height / 3;
                    if(dX > 0){
                        leftDraw(left,top,bottom,c,dX,width);
                    } else {
                        rightDraw(right,top,bottom,c,dX,width);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        attachRecyclerView(simpleItemTouchCallback);
    }

    private void leftDraw(float left,float top,float bottom,final Canvas c,float dX,float width){
        paint.setColor(Color.parseColor("#388E3C"));
        final RectF background = new RectF(left, top, dX,bottom);
        c.drawRect(background,paint);
        final RectF icon_dest = new RectF(left + width ,top + width,left+ 2*width,bottom - width);
        c.drawBitmap(iconLeft,null,icon_dest,paint);
    }
    private void rightDraw(float right,float top,float bottom,final Canvas c,float dX,float width){
        paint.setColor(Color.parseColor("#D32F2F"));
        final RectF background = new RectF(right + dX, top,right, bottom);
        c.drawRect(background,paint);
        final RectF icon_dest = new RectF(right - 2*width ,top + width,right - width,bottom - width);
        c.drawBitmap(iconRight,null,icon_dest,paint);
    }

    private void attachRecyclerView(ItemTouchHelper.SimpleCallback simpleItemTouchCallback){
        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

}
