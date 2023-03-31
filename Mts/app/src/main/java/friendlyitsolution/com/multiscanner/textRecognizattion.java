package friendlyitsolution.com.multiscanner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextDetector;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;
import java.util.List;

import gun0912.tedbottompicker.TedBottomPicker;

public class textRecognizattion extends AppCompatActivity {

    ImageView iv;
    TextView tv;
    Button btn;

    Uri imguri=null;
    ProgressDialog pd;

    static String txtdata="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_recognizattion);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pd=new ProgressDialog(textRecognizattion.this);
        pd.setMessage("please wait");
        pd.setTitle("Converting");
        pd.setCancelable(false);
        iv=(ImageView)findViewById(R.id.img);
        btn=findViewById(R.id.btn);
        tv=(TextView)findViewById(R.id.txt);
        ImageView backbtn=(ImageView)findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                textRecognizattion.super.onBackPressed();

            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bits = null;
                try {
                    bits = MediaStore.Images.Media.getBitmap(getContentResolver(), imguri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //   iv.setImageBitmap(bits);
                pd.show();
                runTextRecognition(bits);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TedBottomPicker tedBottomPicker = new TedBottomPicker.Builder(textRecognizattion.this)
                        .setOnImageSelectedListener(new TedBottomPicker.OnImageSelectedListener() {
                            @Override
                            public void onImageSelected(Uri uri) {

                                // Toast.makeText(getApplicationContext(),"get : "+uri,Toast.LENGTH_LONG).show();
                                if(uri!=null)
                                {

                                    Crop(uri);

                                }
                                else
                                {
                                    btn.setVisibility(View.GONE);
                                }
                            }
                        })
                        .create();

                tedBottomPicker.show(getSupportFragmentManager());
            }
        });
    }
    private void runTextRecognition(Bitmap bp) {
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bp);
        FirebaseVisionTextDetector detector = FirebaseVision.getInstance()
                .getVisionTextDetector();
        //  mButton.setEnabled(false);
        detector.detectInImage(image)
                .addOnSuccessListener(
                        new OnSuccessListener<FirebaseVisionText>() {
                            @Override
                            public void onSuccess(FirebaseVisionText texts) {
                                //mButton.setEnabled(true);
                                processTextRecognitionResult(texts);
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Task failed with an exception
                                //  mButton.setEnabled(true);
                                e.printStackTrace();
                            }
                        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                btn.setVisibility(View.VISIBLE);
                imguri = result.getUri();
                iv.setImageURI(imguri);


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
    void Crop(Uri uri)
    {


// start cropping activity for pre-acquired image saved on the device
        CropImage.activity(uri).setCropMenuCropButtonTitle("Crop").setCropShape(CropImageView.CropShape.RECTANGLE)
                .start(this);

    }
    private void processTextRecognitionResult(FirebaseVisionText texts) {
        tv.setText("");
        pd.dismiss();
        List<FirebaseVisionText.Block> blocks = texts.getBlocks();
        if (blocks.size() == 0) {
            Toast.makeText(getApplicationContext(),"NO TEXT IN IMAGE",Toast.LENGTH_LONG).show();
            return;
        }

        for (int i = 0; i < blocks.size(); i++) {
            List<FirebaseVisionText.Line> lines = blocks.get(i).getLines();
            tv.append("\n");
            for (int j = 0; j < lines.size(); j++) {
                tv.append("\n");
                List<FirebaseVisionText.Element> elements = lines.get(j).getElements();
                for (int k = 0; k < elements.size(); k++) {
                    //  GraphicOverlay.Graphic textGraphic = new TextGraphic(mGraphicOverlay, elements.get(k));
                    //  mGraphicOverlay.add(textGraphic);

                    tv.append(" "+elements.get(k).getText());

                }
            }
        }


        txtdata=tv.getText().toString();
        Intent i=new Intent(textRecognizattion.this,Editpage.class);
        startActivity(i);

    }
}
