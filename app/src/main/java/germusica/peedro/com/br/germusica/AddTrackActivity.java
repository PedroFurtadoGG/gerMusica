package germusica.peedro.com.br.germusica;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddTrackActivity extends AppCompatActivity {

    // criando referências para as views na interface
    TextView textViewArtistName;
    EditText editTextTrackName;
    SeekBar seekBarRating;
    Button buttonAddTrack;

    ListView listViewTracks;

    // referência para a chave onde vamos criar as coisas
    // no firebase
    DatabaseReference databaseTracks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_track);

        // ligando as referências com as views na interface...
        textViewArtistName = (TextView) findViewById(R.id.textViewGenreName);
        editTextTrackName = (EditText) findViewById(R.id.editTextTrackName);
        seekBarRating = (SeekBar) findViewById(R.id.seekBarRating);
        buttonAddTrack = (Button) findViewById(R.id.buttonAddTrack);

        listViewTracks = (ListView) findViewById(R.id.listViewTracks);

        // vamos mostrar o nome do artista, que foi selecionado na activity anterior
        // lembra que colocamos no intent ?
        // primeiro recuperamos aquele intent...
        Intent intent = getIntent();

        // agora recuperamos as strings que armazenamos no intent
        String id = intent.getStringExtra("ARTIST_ID");
        String name = intent.getStringExtra("ARTIST_NAME");

        // agora podemos colocar o nome do artista na interface
        textViewArtistName.setText(name);

        // definiremos a referência para o local (chave) debaixo da qual vamos
        // gravar as coisas no firebase.
        // Porém, diferentemente do que fizemos com os artistas (que ficavam
        // diretamente "debaixo" da chave "Artists", aqui, além da chave "Tracks",
        // teremos também a chave com o id do artista. Isto é, todas as músicas
        // de um determinado artista ficarão "debaixo" de uma chave com seu id.
        // Aqui, portanto, estamos definindo o local principal (getReference) como
        // Tracks, e debaixo dele, um filho com chave que é o id do artista.
        // Se nada disso existe ainda no banco de dados, será criado.
        databaseTracks = FirebaseDatabase.getInstance().getReference("Tracks").child(id);

        // definindo o tratador do evento de clique no botão
        buttonAddTrack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // método para gravar os dados da trilha (mais embaixo)
                saveTrack();
            }
        });
    }

    // método para gravar os dados de uma trilha no banco de dados
    private void saveTrack() {

        // vamos pegar os valores da interface, para criar uma nova trilha
        String trackName = editTextTrackName.getText().toString().trim();
        int rating = seekBarRating.getProgress();

        // verifica se o nome está vazio
        if(!TextUtils.isEmpty(trackName)) {

            // não está vazio;
            // vamos então gerar um id único desta trilha, vai ser a chave para
            // gravação de dados no banco de dados;
            // detalhes desses métodos podem ser vistos no código comentado no post 4.
            // Mutatis mutandis, é a mesma coisa que fizemos para gravar um artista.
            String id = databaseTracks.push().getKey();

            // já temos id, nome da trilha e o rating...
            // podemos criar o objeto Track
            Track track = new Track(id, trackName, rating);

            // enfim, gravamos o objeto no banco de dados, "debaixo" da chave id
            databaseTracks.child(id).setValue(track);

            // sucesso! toast falando disso
            Toast.makeText(this, "Trilha gravada com sucesso!", Toast.LENGTH_LONG).show();

        }
        else {

            // caixa vazia, vamos mostrar um toast com o erro
            Toast.makeText(this, "Precisa digitar o nome da trilha!", Toast.LENGTH_SHORT).show();
        }
    }
}
