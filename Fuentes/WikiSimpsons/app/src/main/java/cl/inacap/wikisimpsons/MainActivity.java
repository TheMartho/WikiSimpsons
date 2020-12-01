package cl.inacap.wikisimpsons;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;



import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cl.inacap.wikisimpsons.adapters.ConsejoAdapter;
import cl.inacap.wikisimpsons.dto.Consejo;

public class MainActivity extends AppCompatActivity {

    private List<Consejo> consejos = new ArrayList<>();
    private ListView consejosList;
    private ConsejoAdapter consejoAdapter;
    private RequestQueue queue;
    private Spinner cantidadSpinner;
    private Button solicitarConsejo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        this.cantidadSpinner = findViewById(R.id.cantidad_sp);
        this.solicitarConsejo = findViewById(R.id.solicitar_btn);
        String [] numeros = {"1","2","3","4","5","6","7","8","9","10"};
        this.cantidadSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,numeros));

        this.solicitarConsejo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cantidad = cantidadSpinner.getSelectedItem().toString();
                sendRequest(cantidad);

            }
        });


    }

    public void sendRequest(String num){
        queue = Volley.newRequestQueue(this);
        this.consejosList = findViewById(R.id.consejos_lv);
        this.consejoAdapter = new ConsejoAdapter(this, R.layout.list_consejos,this.consejos);
        this.consejosList.setAdapter(this.consejoAdapter);
        JsonArrayRequest jsonReq = new JsonArrayRequest(Request.Method.GET, "https://thesimpsonsquoteapi.glitch.me/quotes?count="+num
                , null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try{
                    consejos.clear();
                    for(int i=0; i<response.length(); i++){
                        JSONObject consultaApi = response.getJSONObject(i);

                        Consejo c = new Consejo();
                        c.setCharacter(consultaApi.getString("character"));
                        c.setQuote(consultaApi.getString("quote"));
                        c.setImage(consultaApi.getString("image"));
                        consejos.add(c);

                    }
                    consejoAdapter.notifyDataSetChanged();
                }catch (Exception ex){
                    consejos.clear();
                }finally {
                    consejoAdapter.notifyDataSetChanged();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("MAIN_ACTIVITY","Dios nos a abandonado");
                consejoAdapter.notifyDataSetChanged();
            }
        });
        queue.add(jsonReq);
    }


}