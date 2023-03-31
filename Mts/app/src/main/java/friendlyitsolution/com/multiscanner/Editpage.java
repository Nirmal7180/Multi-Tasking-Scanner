package friendlyitsolution.com.multiscanner;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Editpage extends AppCompatActivity {

    MaterialEditText et,tit;
    Button btn;

    Spinner fontsp,sizesp;
    List<String> size,fonts;

    int fontnumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editpage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fontsp=findViewById(R.id.fontsp);
        sizesp=findViewById(R.id.sizesp);

        fonts=new ArrayList<>();
        size=new ArrayList<>();
        size.add("10");
        size.add("15");
        size.add("20");
        size.add("25");
        size.add("30");
        size.add("35");
        size.add("40");

        fonts.add("font1");
        fonts.add("font2");
        fonts.add("font3");
        fonts.add("font4");
        fonts.add("font5");
        fonts.add("font6");
        fonts.add("font7");

        ArrayAdapter<String> fontad=new ArrayAdapter<>(Editpage.this,android.R.layout.simple_spinner_dropdown_item,fonts);
        ArrayAdapter<String> sizead=new ArrayAdapter<>(Editpage.this,android.R.layout.simple_spinner_dropdown_item,size);

        sizesp.setAdapter(sizead);
        fontsp.setAdapter(fontad);

        sizesp.setSelection(1);
        fontsp.setSelection(1);
        btn=findViewById(R.id.btn);
        et=findViewById(R.id.ettxt);
        tit=findViewById(R.id.etname);
        et.setText(textRecognizattion.txtdata);
        sizesp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                int si=Integer.parseInt(sizesp.getSelectedItem()+"");
                et.setTextSize(si);



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        fontsp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int ii, long l) {

                int i=ii+1;

                fontnumber=i;
                Typeface tp;


                if(i==1) {

                    tp= Typeface.createFromAsset(getAssets(),"font_reg_light.ttf");

                }
                else if(i==2)
                {tp= Typeface.createFromAsset(getAssets(),"font_reg_light1.ttf");


                }
                else if(i==3)
                {tp= Typeface.createFromAsset(getAssets(),"font_reg_semibold.ttf");


                }
                else if(i==4)
                {tp= Typeface.createFromAsset(getAssets(),"font_reg_semibold1.ttf");


                }
                else if(i==5)
                {tp= Typeface.createFromAsset(getAssets(),"fontregular.ttf");


                }
                else if(i==6)
                {tp= Typeface.createFromAsset(getAssets(),"fontrs.ttf");


                }
                else
                {tp= Typeface.createFromAsset(getAssets(),"fontwin.ttf");


                }
                et.setTypeface(tp);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!et.getText().toString().equals("")&&!tit.getText().toString().equals(""))
                {

                    final Map<String,String> dd=new HashMap<>();
                    dd.put("title",tit.getText().toString());
                    dd.put("data",et.getText().toString());


                    Myapp.myref.child("mytext").push().setValue(dd).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {


                            Myapp.ref.child("text").setValue(dd);

                            try {
                                createPdf(fontnumber);
                            } catch (FileNotFoundException e) {

                                Myapp.showMsg("TRY AGAIN LATER "+e.getMessage());
                                e.printStackTrace();
                            }
                            Myapp.showMsg("SUCCESSFULLY SAVED");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Myapp.showMsg("TRY AGAIN LATER");
                        }
                    });

                }
                else
                {
                    Myapp.showMsg("NO DATA FOUND TO SEND");
                }


            }
        });


        ImageView backbtn=(ImageView)findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Editpage.super.onBackPressed();

            }
        });

    }
    private void createPdf(int i) throws FileNotFoundException {

        try {

            BaseFont urName;

            if(i==1) {
                urName = BaseFont.createFont("assets/font_reg_light.ttf", "UTF-8", BaseFont.EMBEDDED);
            }
            else if(i==2)
            {
                urName = BaseFont.createFont("assets/font_reg_light1.ttf", "UTF-8", BaseFont.EMBEDDED);

            }
            else if(i==3)
            {
                urName = BaseFont.createFont("assets/font_reg_semibold.ttf", "UTF-8", BaseFont.EMBEDDED);

            }
            else if(i==4)
            {
                urName = BaseFont.createFont("assets/font_reg_semibold1.ttf", "UTF-8", BaseFont.EMBEDDED);

            }
            else if(i==5)
            {
                urName = BaseFont.createFont("assets/fontregular.ttf", "UTF-8", BaseFont.EMBEDDED);

            }
            else if(i==6)
            {
                urName = BaseFont.createFont("assets/fontrs.ttf", "UTF-8", BaseFont.EMBEDDED);

            }
            else
            {
                urName = BaseFont.createFont("assets/fontwin.ttf", "UTF-8", BaseFont.EMBEDDED);

            }
            int si=Integer.parseInt(sizesp.getSelectedItem()+"");

            Font urFontName = new Font(urName, si);
            File pdfFolder = new File(Environment.getExternalStorageDirectory(), "Mts/");
            if (!pdfFolder.exists()) {
                pdfFolder.mkdir();
            }

            //Create time stamp
            Date date = new Date();
            String timeStamp =tit.getText().toString(); //new SimpleDateFormat("yyyyMMdd_HHmmss").format(date);

            File myFile = new File(Environment.getExternalStorageDirectory(), "Mts/"+tit.getText().toString()+".pdf");

            OutputStream output = new FileOutputStream(myFile);

            //Step 1
            Document document = new Document();

            //Step 2
            PdfWriter.getInstance(document, output);

            //Step 3
            document.open();

            //Step 4 Add content
            document.add(new Paragraph(et.getText().toString(),urFontName));
            // document.add(new Paragraph(mBodyEditText.getText().toString()));

            //Step 5: Close the document
            document.close();
        }
        catch(Exception e)
        {
            Toast.makeText(getApplicationContext(),"Error : "+e.getMessage(),Toast.LENGTH_LONG).show();

        }

    }

}
