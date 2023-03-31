package friendlyitsolution.com.multiscanner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import friendlyitsolution.com.multiscanner.adpeter.Subject;
import friendlyitsolution.com.multiscanner.adpeter.SubjectAdapter;

public class History extends AppCompatActivity {
    RecyclerView recy;
    public static SubjectAdapter adapter;
   public static List<Subject> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ImageView backbtn=(ImageView)findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                History.super.onBackPressed();

            }
        });
        list=new ArrayList<>();
        adapter=new SubjectAdapter(list);
        recy=findViewById(R.id.recy);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(Myapp.con);
        recy.setLayoutManager(mLayoutManager);
        recy.setItemAnimator(new DefaultItemAnimator());
        recy.setAdapter(adapter);

        getData();


    }
    void getData()
    {
        if(Myapp.userdata.containsKey("mytext"))
        {
            list.clear();
            Map<String,Object> ss=(Map<String, Object>)Myapp.userdata.get("mytext");
            List<String> keys=new ArrayList<>(ss.keySet());
            for(int i=0;i<keys.size();i++)
            {
                Map<String,String> ssd=(Map<String, String>)ss.get(keys.get(i)) ;
                list.add(new Subject(keys.get(i),ssd.get("data"),ssd.get("title")));

            }

            adapter.notifyDataSetChanged();

    }
        else
        {
            list.clear();
            adapter.notifyDataSetChanged();
            Myapp.showMsg("NO HISTORY");
        }
    }

}
