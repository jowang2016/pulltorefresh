package com.example.jowang.pulltorefresh805;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import android.os.Handler;
import java.util.Random;
/**
 * Created by jowang on 16/8/5.
 */
public class Adapter extends RecyclerView.Adapter<Adapter.Holder> {
    Context context;
    ArrayList<Movie> movie;
    SwipeRefreshLayout swipeRefreshLayout;
    MyClickListner clickListner;

    public Adapter(Context context, ArrayList<Movie> movie, SwipeRefreshLayout swipeRefreshLayout) {
        this.context = context;
        this.movie = movie;
        this.swipeRefreshLayout = swipeRefreshLayout;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_lay,parent,false);
        Holder holder=new Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        holder.textView.setText(movie.get(position).title);
        holder.imageView.setImageResource(movie.get(position).imgId);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    @Override
    public int getItemCount() {
        return movie.size();
    }
    private void refresh(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                movie.add(0,movie.get(new Random().nextInt(movie.size())));
                Adapter.this.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        },3000);
    }
    public void setMyClickListner(MyClickListner clickListner){
        this.clickListner=clickListner;
    }
    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView;
        TextView textView;

        public Holder(View itemView) {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.mimg);
            textView=(TextView)itemView.findViewById(R.id.mtitle);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListner!=null){
                clickListner.MyClick(getLayoutPosition());
            }
        }
    }
    public interface MyClickListner{
        void MyClick(int position);
    }
}
