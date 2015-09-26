package com.example.elijah.hw3_team8a;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class CreateQuestionActivity extends AppCompatActivity {

    private final int PICK_IMAGE_REQUEST = 1;
    private static int counter = 0; //Counts number of user answer options, used to validate if there are between 2-7 options
    private static int optionId = 1; //ID for options that will be used later to test answer

    final int REQUIRED_IMG_SIZE = 150;

    String decoded;

    EditText questionText;
    EditText optionText;
    static RadioGroup radioGroup;

    UserNewQuiz quiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quiz);

        quiz = new UserNewQuiz();

        questionText = (EditText) findViewById(R.id.editText_question);
        optionText = (EditText) findViewById(R.id.editText_option);
        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);

        findViewById(R.id.button_select_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchGallery = new Intent(Intent.ACTION_PICK);
                searchGallery.setType("image/*");
                startActivityForResult(searchGallery, PICK_IMAGE_REQUEST);
            }
        });

        findViewById(R.id.imgBtn_addOption).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (counter > 6) {
                    Toast.makeText(CreateQuestionActivity.this, "Seven is the maximum number of options", Toast.LENGTH_SHORT).show();
                } else {
                    if (questionText.getText().toString().matches("")) {
                        Toast.makeText(CreateQuestionActivity.this, "Please enter a question", Toast.LENGTH_LONG).show();
                    } else {
                        if (optionText.getText().toString().matches("")) {
                            Toast.makeText(CreateQuestionActivity.this, "Please enter an option", Toast.LENGTH_SHORT).show();
                        } else {

                            RadioButton rdbtn = new RadioButton(CreateQuestionActivity.this);
                            rdbtn.setText(optionId + " " + optionText.getText().toString());

                            quiz.addOption(optionText.getText().toString());
                            radioGroup.addView(rdbtn);

                            counter++;
                            optionId++;
                        }
                    }
                }
            }
        });

        findViewById(R.id.button_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                //Need to process user information
                if (questionText.getText().toString().matches("") || counter < 2) {
                    Toast.makeText(CreateQuestionActivity.this, "Please enter a question and have at least two options", Toast.LENGTH_LONG).show();
                } else {
                    if (radioGroup.getCheckedRadioButtonId() == -1) {
                        Toast.makeText(CreateQuestionActivity.this, "Please check an option as the answer.", Toast.LENGTH_LONG).show();
                    } else {
                        quiz.setAnswerID(radioGroup.getCheckedRadioButtonId());
                        quiz.setUserQuestion(questionText.getText().toString());

                        Log.d("Demo", "Question: " + quiz.getUserQuestionFinal());
                        Log.d("Demo", "Options: " + quiz.getUserOptionsFinal());
                        Log.d("Demo", "Answer: " + quiz.getAnswerIDFinal());
                        Log.d("Demo", "URL: " + quiz.getFilePathFinal());


                        counter = 0;
                        finish();
                    }
                }

            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            switch (requestCode) {
                case PICK_IMAGE_REQUEST:
                    if (resultCode == RESULT_OK) {
                        /*  // Use this if your not going to resize image
                        Uri selectedImage = data.getData();
                        InputStream imageStream = getContentResolver().openInputStream(selectedImage);
                        Bitmap bitmap = BitmapFactory.decodeStream((imageStream));
                        */

                        Uri selectedImage = data.getData();         //  Uncomment top, comment out these two lines,
                        Bitmap bitmap = decodeUri(selectedImage);    // If not using image resizer

                        File myFile = new File(selectedImage.getPath());
                        Log.d("Demo", "File Path: " + myFile.getPath());

                        ImageView imageView = (ImageView) findViewById(R.id.imageView_userInput);
                        imageView.setImageBitmap(bitmap);
                    }
                    break;
            }

        } catch (Exception e) {
            Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show();
        }
    }

    private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage));

        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_IMG_SIZE
                    || height_tmp / 2 < REQUIRED_IMG_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o2);
    }

}
