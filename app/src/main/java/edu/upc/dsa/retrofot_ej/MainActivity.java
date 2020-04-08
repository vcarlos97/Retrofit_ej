package edu.upc.dsa.retrofot_ej;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button bt = (Button)findViewById(R.id.button);
        final TextView txt =(TextView)findViewById(R.id.textView);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                OkHttpClient client = new OkHttpClient.Builder()
                        .addInterceptor(interceptor)
                        .build();
                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl("https://api.github.com/")
                        .client(client) /*interceptor log in*/
                        .build();

                GitHubService service = retrofit.create(GitHubService.class);
                Call<List<Repo>> repos = service.listRepos("jlopezr");

                repos.enqueue(new Callback<List<Repo>>() {
                    @Override
                    public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                        List<Repo> repos = response.body();
                        String texto = "[";
                        for(Repo r: repos){
                            texto= texto + r.full_name+ " ";
                        }
                        texto= texto+"]";
                        txt.setText(texto);
                    }



                    @Override
                    public void onFailure(Call<List<Repo>> call, Throwable t) {
                        txt.setText("ERROR");
                    }
                });


                Log.d("APP", "Boton pulsado");
                //Toast.makeText(MainActivity.this, "Boton Pulsado", Toast.LENGTH_SHORT);
                txt.setText("Boton pulsado");

            }
        });
    }

    protected void onClick(View v) {

    }

}
