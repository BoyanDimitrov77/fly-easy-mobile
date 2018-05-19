package com.easy.fly.flyeasy.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easy.fly.flyeasy.R;
import com.easy.fly.flyeasy.db.models.Article;
import com.easy.fly.flyeasy.utils.DateFormater;

import org.w3c.dom.Text;

import java.util.List;

public class NewsArticleAdapter extends RecyclerView.Adapter<NewsArticleAdapter.ViewHolder> {

    private List<Article> articles;
    private Context context;

    public NewsArticleAdapter(List<Article> articles , Context context){
        this.articles = articles;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsArticleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_news_articles,parent,false);
        return new NewsArticleAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.articleTitle.setText(articles.get(position).getTitle());
        holder.articleDate.setText(DateFormater.formatDateTimeForUI(articles.get(position).getPublishedAt()));
        holder.articleDescription.setText(articles.get(position).getDescription());

        Glide.with(context).load(articles.get(position).getUrlToImage()).into(holder.articleImage);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView articleTitle;
        private ImageView articleImage;
        private TextView articleDate;
        private TextView articleDescription;

        public ViewHolder(View itemView) {
            super(itemView);

            articleTitle = (TextView) itemView.findViewById(R.id.article_title);
            articleImage = (ImageView) itemView.findViewById(R.id.article_img);
            articleDate = (TextView) itemView.findViewById(R.id.article_date);
            articleDescription = (TextView) itemView.findViewById(R.id.article_description);
        }
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private NewsArticleAdapter.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final NewsArticleAdapter.ClickListener clickListener) {
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
