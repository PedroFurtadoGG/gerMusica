package germusica.peedro.com.br.germusica;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    EditText editTextName;
    Button buttonAddArtist;
    Button buttonGoG;
    Spinner spinnerGenres;
    ListView listViewArtists;

    List<Artist> artistList;

    DatabaseReference databaseArtists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseArtists = FirebaseDatabase.getInstance().getReference("Artists");

        editTextName = (EditText) findViewById(R.id.editTextName);
        buttonAddArtist = (Button) findViewById(R.id.buttonAddArtist);
        buttonGoG = (Button) findViewById(R.id.buttonGoG);
        spinnerGenres = (Spinner) findViewById(R.id.spinnerGenres);
        listViewArtists = (ListView) findViewById(R.id.listViewArtists);

        artistList = new ArrayList<>();

        // chama tela de generos
        buttonGoG.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, AddGenreActivity.class);
                startActivity(it);
            }
        });

        buttonAddArtist.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addArtist();
            }
        });

        //spinner genero

        // definindo um listener para chamar a activity das tracks, quando
        // for clicado um artista na lista de artistas (listViewArtists)
        listViewArtists.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                // a lista foi clicada, na posição indicada pelo parâmetro i
                // vamos então pegar o objeto artista correspndente a esta posição
                // na lista de artistas
                Artist artist = artistList.get(i);

                // vamos chamar a nova activity, para trabalhar com as musicas (tracks)
                Intent intent = new Intent(getApplicationContext(), AddTrackActivity.class);

                // guardando o nome e o id do artista na intent, para ser recuperada
                // pela nova activity
                intent.putExtra("ARTIST_ID", artist.getArtistId());
                intent.putExtra("ARTIST_NAME", artist.getArtistName());

                // enfim, chama a activity
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {

        super.onStart();

        databaseArtists.addValueEventListener( new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                artistList.clear();

                for(DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {

                    Artist artist = artistSnapshot.getValue(Artist.class);
                    artistList.add(artist);
                }

                ArtistListAdapter adapter = new ArtistListAdapter(MainActivity.this, artistList);
                listViewArtists.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });
    }


    // método para adicionar um artista no banco de dados
    private void addArtist() {

        String name = editTextName.getText().toString().trim();
        String genre = spinnerGenres.getSelectedItem().toString();

        if( !TextUtils.isEmpty(name) ) {

            String id = databaseArtists.push().getKey();
            Artist artist = new Artist(id, name, genre);
            databaseArtists.child(id).setValue(artist);

            Toast.makeText(this, "Artista adicionado", Toast.LENGTH_LONG).show();
        }
        else {

            Toast.makeText(this, "Você deve digitar um nome", Toast.LENGTH_LONG).show();
        }
    }
}