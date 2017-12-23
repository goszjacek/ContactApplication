package com.example.goszj.contactsapplication;

import android.content.Context;
import android.net.sip.SipSession;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by goszj on 20.12.2017.
 */

public class ContactsItemListener implements RecyclerView.OnItemTouchListener {
    private ContactTouchListener listener;
    private GestureDetector gestureDetector;

    public interface ContactTouchListener{
        public void onClickItem(View v, int position);
        public void onLongTouchItem(View v, int position);
    }

    public ContactsItemListener(Context context, final RecyclerView rv,final ContactTouchListener listener){
        this.listener = listener;
        gestureDetector = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                View v  = rv.findChildViewUnder(e.getX(),e.getY());
                listener.onClickItem(v,rv.getChildAdapterPosition(v));
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View v = rv.findChildViewUnder(e.getX(),e.getY());
                listener.onLongTouchItem(v,rv.getChildAdapterPosition(v));
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View childView = rv.findChildViewUnder(e.getX(),e.getY());
        if(gestureDetector.onTouchEvent(e)&&childView!=null&&listener!=null){
            listener.onClickItem(childView,rv.getChildAdapterPosition(childView));
            return true;
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
