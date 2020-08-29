package br.pro.ednilsonrossi.meupocket.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.pro.ednilsonrossi.meupocket.R;
import br.pro.ednilsonrossi.meupocket.dao.SiteDao;
import br.pro.ednilsonrossi.meupocket.model.Site;

public class AdicionarSiteActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText novoTituloEditText;
    private EditText novoEnderecoEditText;
    private Button salvarButton;
    private Site mSite;
    private List<Site> siteList = null;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_site);
        novoTituloEditText = findViewById(R.id.edittext_novo_site);
        novoEnderecoEditText = findViewById(R.id.edittext_novo_endereco);
        salvarButton = findViewById(R.id.button_salvar);
        salvarButton.setOnClickListener(this);
        mSharedPreferences = this.getSharedPreferences(getString(R.string.file_sites), MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        recuperaSites();
    }

    private void recuperaSites(){
        String sites = mSharedPreferences.getString(getString(R.string.key_sites_db), "");
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray;
        siteList = new ArrayList<>();
        try{
            jsonArray = new JSONArray(sites);
            for(int i=0; i<jsonArray.length(); i++){
                jsonObject = jsonArray.getJSONObject(i);
                mSite = new Site(jsonObject.getString("titulo"),
                jsonObject.getString("endereco"));
                siteList.add(mSite);
            }
        } catch (JSONException ex){
            siteList = null;
        }
        if (siteList != null) {
            for (Site s : siteList) {
                Log.i(getString(R.string.tag), s.toString());
            }
        }
    }

    @Override
    public void onClick(View view) {
        if(view == salvarButton){
            String titulo;
            String endereco;
            titulo = novoTituloEditText.getText().toString();
            endereco = novoEnderecoEditText.getText().toString();
            mSite = new Site(titulo, endereco);
            adicionarBD();
            finish();
        }
    }

    private void adicionarBD(){
        JSONObject jsonObject;
        JSONArray jsonArray = new JSONArray();
        if(siteList == null){
            siteList = new ArrayList<>(1);
        }
        siteList.add(mSite);
        Toast.makeText(this, "Site adicionado com sucesso!", Toast.LENGTH_SHORT);
        for(Site s : siteList){
            jsonObject = new JSONObject();
            try {
                jsonObject.put("titulo", s.getTitulo());
                jsonObject.put("endereco", s.getEndereco());
                jsonArray.put(jsonObject);
            }catch (JSONException e){
                Log.e(getString(R.string.tag), getString(R.string.erro_recupera_lista));
            }
        }
        String sites = jsonArray.toString();
        mEditor.putString(getString(R.string.key_sites_db), sites);
        mEditor.commit();
    }
}