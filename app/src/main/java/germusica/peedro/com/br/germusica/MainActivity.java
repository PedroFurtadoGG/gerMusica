package germusica.peedro.com.br.germusica;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    EditText editTextName;
    Button buttonAddArtist;
    Spinner spinnerGenres;

    DatabaseReference databaseArtists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


       databaseArtists = FirebaseDatabase.getInstance().getReference("Artists");

       editTextName     =  (EditText) findViewById(R.id.editTextName);
       buttonAddArtist  =  (Button) findViewById(R.id.buttonAddArtist);
       spinnerGenres    =  (Spinner) findViewById(R.id.spinnerGenres);

       buttonAddArtist.setOnClickListener( new View.OnClickListener(){

           @Override
           public void onClick(View view) {
               addArtist();
           }
       });
    }

    private void addArtist(){
        String name = editTextName.getText().toString().trim();
        String genre = spinnerGenres.getSelectedItem().toString().trim();

        if(!TextUtils.isEmpty(name)){
            String id = databaseArtists.push().getKey();

            Artist artist = new Artist(id, name, genre);

            databaseArtists.child(id).setValue(artist);

            Toast.makeText(this, "Artista Adicionado", Toast.LENGTH_LONG).show();
        } else{
            Toast.makeText(this, "Digite um nome para concluir.", Toast.LENGTH_LONG).show();
        }
    }
}
