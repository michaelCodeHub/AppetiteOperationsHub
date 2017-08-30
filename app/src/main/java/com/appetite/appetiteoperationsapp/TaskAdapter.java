package com.appetite.appetiteoperationsapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appetite.appetiteoperationsapp.Database.Database;

import java.util.List;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.EventViewHolder> {
    List<TaskInfo> eventList;
    Context mContext;
    int swidth;
    int sheight;

    LruCache<Integer,Bitmap> imageCache;

    public TaskAdapter(Context context, List<TaskInfo> eventList) {
        this.mContext = context;
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        swidth = displayMetrics.widthPixels;
        sheight = displayMetrics.heightPixels;
        this.eventList = eventList;

        int maxSize = (int) (Runtime.getRuntime().maxMemory()/1024);
        int cacheSize = maxSize / 8;
        imageCache = new LruCache<>(cacheSize);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.taskadaptar, parent, false);

        new Sansation().overrideFonts(mContext,view);

        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final EventViewHolder eventViewHolder, int position) {
        final TaskInfo ei = eventList.get(position);

        Database database = new Database(mContext);
        database.open();

        eventViewHolder.title.setText(ei.name.toUpperCase());
        eventViewHolder.client.setText(database.getClientName(ei.clientid));
        eventViewHolder.deadline.setText(ei.client_deadline);

        database.close();

        new Sansation().overrideFonts(mContext, eventViewHolder.title);
        new Sansation().overrideFonts(mContext,eventViewHolder.client);
        new Sansation().overrideFonts(mContext,eventViewHolder.deadline);

        eventViewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, TaskDetails.class);
                intent.putExtra("info",ei);
                ((Home) mContext).startActivity(intent);
            }
        });
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder
    {
        TextView title,client,deadline;
        LinearLayout layout;

        public EventViewHolder(View view)
        {
            super(view);

            title = (TextView) view.findViewById(R.id.title);
            client = (TextView) view.findViewById(R.id.client);
            deadline = (TextView) view.findViewById(R.id.deadline);

            layout = view.findViewById(R.id.layout);
        }
    }
}