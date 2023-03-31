package friendlyitsolution.com.multiscanner;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.File;

public class pdfopen extends AppCompatActivity {

    ListView lv;
    ArrayAdapter<String> adapter;
    String f[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfopen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageView backbtn=(ImageView)findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pdfopen.super.onBackPressed();

            }
        });
        lv=findViewById(R.id.lv);
        File pdfFolder = new File(Environment.getExternalStorageDirectory(), "Mts/");
        if (pdfFolder.exists()) {
            f=pdfFolder.list();
            adapter=new ArrayAdapter<>(pdfopen.this,android.R.layout.simple_list_item_1,f);
            lv.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String filename=f[i];
                    Intent intent = new Intent(Intent.ACTION_VIEW);


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        File file = new File(Environment.getExternalStorageDirectory(), "Mts/"+filename);

                        Uri uri = FileProvider.getUriForFile(pdfopen.this, getPackageName() + ".provider", file);
                        intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(uri);
                        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivity(intent);
                    } else {
                        intent = new Intent(Intent.ACTION_VIEW);
                        File file = new File(Environment.getExternalStorageDirectory(), "Mts/"+filename);

                        intent.setDataAndType(Uri.parse(file.getAbsolutePath()), "application/pdf");
                        intent = Intent.createChooser(intent, "Open File");
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }



                }
            });
        }
        else
        {
            Myapp.showMsg("No pdf found");
        }


    }
}
