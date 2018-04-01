package germusica.peedro.com.br.germusica;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddGenreActivity extends AppCompatActivity {

    //criar referencia do front com o back
    TextView textViewGenreName;
    EditText editTextGenreName;
    Button buttonAddGenre;

    ListView listViewGenres;

    // referência para a chave onde vamos criar as coisas
    // no firebase
    DatabaseReference databaseGenres;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_genre);

        // ligando as referências com as views na interface...
        textViewGenreName = (TextView) findViewById(R.id.textViewGenreName);
        editTextGenreName = (EditText) findViewById(R.id.editTextGenreName);
        buttonAddGenre    = (Button) findViewById(R.id.buttonAddGenre);
        listViewGenres    = (ListView) findViewById(R.id.listViewGenres);


        databaseGenres = FirebaseDatabase.getInstance().getReference("Genres");

        // definindo o tratador do evento de clique no botão
        buttonAddGenre.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // método para gravar os dados da trilha (mais embaixo)
                addGenre();
            }
        });

    }

    private void addGenre(){

        String genreName = editTextGenreName.getText().toString().trim();

        if(!TextUtils.isEmpty(genreName)){

            String id = databaseGenres.push().getKey();

            Genre genre = new Genre(id, genreName);

            databaseGenres.child(id).setValue(genre);

            Toast.makeText(this,"Genero adicionado com sucesso!!!", Toast.LENGTH_LONG).show();


        } else{
            Toast.makeText(this, "Ocorreu algum erro", Toast.LENGTH_SHORT).show();
        }
    }
}
