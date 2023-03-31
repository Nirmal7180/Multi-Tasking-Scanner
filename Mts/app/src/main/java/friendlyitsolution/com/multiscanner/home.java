package friendlyitsolution.com.multiscanner;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class home extends AppCompatActivity {
    RelativeLayout cartr, ewalletr, settingr, offerr;
    ImageView iv;
    TextView tv, editpro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageView lgout = (ImageView) findViewById(R.id.lgbtn);
        ImageView backbtn = (ImageView) findViewById(R.id.backbtn);
        lgout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                logOut();
            }
        });
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                home.super.onBackPressed();
            }
        });

        cartr = (RelativeLayout) findViewById(R.id.nfeed);
        ewalletr = (RelativeLayout) findViewById(R.id.history);
        offerr = (RelativeLayout) findViewById(R.id.neword);
        settingr = (RelativeLayout) findViewById(R.id.dash);
        iv = (ImageView) findViewById(R.id.profile_image);
        tv = (TextView) findViewById(R.id.name);
        editpro = (TextView) findViewById(R.id.editpro);
        setprofile();

        editpro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent i=new Intent(home.this,profile.class);
                startActivity(i);
            }
        });

        cartr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(home.this,textRecognizattion.class);
                  startActivity(i);
            }
        });


        ewalletr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  Intent i=new Intent(home.this,pdfopen.class);
                  startActivity(i);
            }
        });


        offerr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(home.this,History.class);
                  startActivity(i);
            }
        });


        settingr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   Intent i=new Intent(home.this,myorders.class);
                //    startActivity(i);
                Intent i=new Intent(home.this,ImageLabel.class);
                startActivity(i);
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        setprofile();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setprofile();
    }

    void setprofile()
    {
        try
        {

            if(!Myapp.userdata.get("imgurl").toString().equals(" "))
            {
                Glide.with(iv.getContext()).load(Myapp.userdata.get("imgurl").toString())
                        .override(200, 200)
                        .fitCenter()
                        .into(iv);
            }
            tv.setText(Myapp.myname);

        }
        catch(Exception e)
        {
            Toast.makeText(getApplicationContext(),"Something went wrong please reopen app",Toast.LENGTH_LONG).show();
        }

    }
    void logOut()
    {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialoug_logout);
        dialog.show();

        final Button btnInDialog = (Button) dialog.findViewById(R.id.btn);
        btnInDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = Myapp.pref.edit();
                editor.clear();
                editor.commit();
                finish();
                Myapp.mynumber = "";
                Myapp.myname = "";
                Myapp.userdata = null;

                Intent i = new Intent(home.this, MainActivity.class);
                startActivity(i);

                finish();
            }
        });
        final ImageView btnClose = (ImageView) dialog.findViewById(R.id.canclebtn);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


    }
}
