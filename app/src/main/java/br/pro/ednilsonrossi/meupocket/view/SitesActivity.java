package br.pro.ednilsonrossi.meupocket.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.pro.ednilsonrossi.meupocket.R;
import br.pro.ednilsonrossi.meupocket.dao.SiteDao;
import br.pro.ednilsonrossi.meupocket.model.Site;

public class SitesActivity extends AppCompatActivity{

    //Referência para o elemento de RecyclerView
    private RecyclerView sitesRecyclerView;

    //Fonte de dados, essa lista possue os dados que são apresentados
    //na tela dos dispositivo.
    private List<Site> siteList;


    private ItemSiteAdapter siteAdapter;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sites);

        //Recupera a referência do elemento no layout
        sitesRecyclerView = findViewById(R.id.recycler_lista_sites);

        //Ao contrário do ListView um RecyclerView necessita de um LayoutManager (gerenciador de
        // layout) para gerenciar o posicionamento de seus itens. Utilizarei um LinearLayoutManager
        // que fará com que nosso RecyclerView se pareça com um ListView.
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        sitesRecyclerView.setLayoutManager(layoutManager);

        //Carrega a fonte de dados
        siteList = SiteDao.recuperateAll();

        siteAdapter = new ItemSiteAdapter(siteList);

        sitesRecyclerView.setAdapter(siteAdapter);

        siteAdapter.setClickListener(new RecyclerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String url = corrigeEndereco(siteList.get(position).getEndereco());
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        mSharedPreferences = this.getPreferences(MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    @Override
    protected void onStart() {
        Log.i(getString(R.string.tag), "Classe: " + getClass().getSimpleName() + "| Método : onStart()");
        super.onStart();
    }
    @Override
    protected void onRestart() {
        Log.i(getString(R.string.tag), "Classe: " + getClass().getSimpleName() + "| Método : onRestart()");
        super.onRestart();
    }
    @Override
    protected void onResume() {
        Log.i(getString(R.string.tag), "Classe: " + getClass().getSimpleName() + "| Método : onResume()");
        super.onResume();
    }
    @Override
    protected void onPause() {
        Log.i(getString(R.string.tag), "Classe: " + getClass().getSimpleName() + "| Método : onPause()");
        super.onPause();
    }
    @Override
    protected void onStop() {
        Log.i(getString(R.string.tag), "Classe: " + getClass().getSimpleName() + "| Método : onStop()");
        super.onStop();
    }
    @Override
    protected void onDestroy() {
        Log.i(getString(R.string.tag), "Classe: " + getClass().getSimpleName() + "| Método : onDestroy()");
        super.onDestroy();
    }

    private String corrigeEndereco(String endereco) {
        String url = endereco.trim().replace(" ","");
        if (!url.startsWith("http://")) {
            return "http://" + url;
        }
        return url;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_mypocket, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_adicionar:
                //TODO abrir tela para adicionar novo site.
            Intent in = new Intent(this, AdicionarSiteActivity.class);
            startActivity(in);
        }
        return super.onOptionsItemSelected(item);
    }
}
