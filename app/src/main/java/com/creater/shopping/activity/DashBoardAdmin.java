package com.creater.shopping.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.creater.shopping.R;
import com.creater.shopping.util.firebasehelp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class DashBoardAdmin extends AppCompatActivity {
    CardView createList, havingList, savedList, logout, manage;
    TextView usernameT, count;
    ImageView userimg;
    ProgressBar progressBar;
    FirebaseFirestore username = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board_admin);
        getSupportActionBar().hide();
        idData();
        count();
        onclickEvents();
        getUserName();
        getDownload();
    }

    public void idData() {
        usernameT = findViewById(R.id.Username);
        progressBar = findViewById(R.id.progressProfile);
        createList = findViewById(R.id.createlist1);
        havingList = findViewById(R.id.havinglist);
        savedList = findViewById(R.id.savedlist);
        logout = findViewById(R.id.logoutC);
        count = findViewById(R.id.count);
        userimg = findViewById(R.id.userimg);
        manage = findViewById(R.id.manage);
        userimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage();
            }
        });
    }

    public void captureImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 123);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123) {
            Bitmap bitmap = null;
            if (data!=null)
            {
                Uri selectimg = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectimg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                uploadimg(bitmap);
            }
        }
    }

    public void uploadimg(final Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String uid = firebasehelp.auth.getUid();
        userimg.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        StorageReference reference = FirebaseStorage.getInstance().getReference().child("profileimg").child(uid + ".jpeg");
        reference.putBytes(baos.toByteArray()).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Profile Uploaded", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    userimg.setVisibility(View.VISIBLE);
                    userimg.setImageBitmap(bitmap);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onclickEvents() {
        createList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoardAdmin.this, CreateList.class));
            }
        });
        havingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoardAdmin.this, MainActivity.class));
            }
        });
        savedList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoardAdmin.this, savedList.class));
            }
        });


        manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoardAdmin.this, shopping_fire_data_set.class));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                finishAffinity();

            }
        });
    }

    public void showimg(Uri uri) {
        if (uri != null) {
            new profileimg().execute(uri);
        }
    }

    class profileimg extends AsyncTask<Uri, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            userimg.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Bitmap doInBackground(Uri... uris) {
            Bitmap bitmap = null;
            try {
                String url = uris[0].toString();
                Log.i("url", url);
                InputStream stream = new URL(url).openStream();
                bitmap = BitmapFactory.decodeStream(stream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            progressBar.setVisibility(View.INVISIBLE);
            userimg.setVisibility(View.VISIBLE);
            userimg.setImageBitmap(bitmap);
        }
    }


    public void getDownload() {
        String uid = firebasehelp.auth.getUid();
        FirebaseStorage store = FirebaseStorage.getInstance();
        StorageReference reference = store.getInstance().getReference().child("profileimg").child(uid + ".jpeg");
        reference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    showimg(task.getResult());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void getUserName() {
        username.collection("User").document(auth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot data = task.getResult();
                usernameT.setText(data.getString("Name"));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void count() {
        firebasehelp.store.collection("User").document(firebasehelp.auth.getCurrentUser().getUid())
                .collection("List").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                QuerySnapshot doc = task.getResult();
                count.setText("" + doc.size());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DashBoardAdmin.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        try {
            count();
        } catch (Exception e) {
            Toast.makeText(DashBoardAdmin.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
