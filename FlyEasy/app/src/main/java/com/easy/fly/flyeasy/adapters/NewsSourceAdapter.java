package com.easy.fly.flyeasy.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.easy.fly.flyeasy.R;
import com.easy.fly.flyeasy.db.models.Source;

import org.w3c.dom.Text;

import java.util.List;

public class NewsSourceAdapter extends RecyclerView.Adapter<NewsSourceAdapter.ViewHolder> {

    private List<Source> newsSources;
    private Context context;


    public NewsSourceAdapter(List<Source> newsSources,Context context){
        this.newsSources = newsSources;
        this.context = context;
    }


    @NonNull
    @Override
    public NewsSourceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_news_source,parent,false);
        return new NewsSourceAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.newsSourceName.setText(newsSources.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return newsSources.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private TextView newsSourceName;


        public ViewHolder(View itemView){
            super(itemView);

            newsSourceName = (TextView) itemView.findViewById(R.id.news_source_name);
        }
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private NewsSourceAdapter.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final NewsSourceAdapter.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
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

}
