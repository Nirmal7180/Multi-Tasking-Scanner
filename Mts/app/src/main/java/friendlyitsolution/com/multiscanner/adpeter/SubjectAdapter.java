package friendlyitsolution.com.multiscanner.adpeter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

import friendlyitsolution.com.multiscanner.History;
import friendlyitsolution.com.multiscanner.Myapp;
import friendlyitsolution.com.multiscanner.R;


/**
 * Created by Meet on 16-10-2017.
 */

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.MyViewHolder> {

private List<Subject> moviesList;

public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView name,data;
    public ImageView btn;

    public MyViewHolder(View view) {
        super(view);
        btn=view.findViewById(R.id.btn);
        name=(TextView)view.findViewById(R.id.name);
        data=(TextView)view.findViewById(R.id.data);
    }
}


    public SubjectAdapter(List<Subject> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hitoryitem, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Subject movie = moviesList.get(position);
        holder.name.setText(movie.title);
        holder.data.setText(movie.data);
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Myapp.myref.child("mytext").child(movie.id).removeValue().addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                   Myapp.showMsg("Try again");
                    }
                }).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                     Myapp.showMsg("Deleted");
                     History.list.remove(movie);
                        History.adapter.notifyDataSetChanged();
                    }
                });


            }
        });


}

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
