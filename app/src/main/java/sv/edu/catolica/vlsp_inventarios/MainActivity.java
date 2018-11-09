package sv.edu.catolica.vlsp_inventarios;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import Clases.ClassListProductsItems;
import Clases.Usuario;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public Usuario user ;

public Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

       user = (Usuario) getIntent().getSerializableExtra("user");

        View hView =  navigationView.getHeaderView(0);
        TextView nav_emp = (TextView)hView.findViewById(R.id.txtEmpresa);
        nav_emp.setText(String.valueOf((user.empresa)));

        TextView nav_user = (TextView)hView.findViewById(R.id.txtUser);
        nav_user.setText(user.nombre);

        FragmentManager fm=getSupportFragmentManager();
        Bundle bundle=new Bundle();
        bundle.putInt("idEmpresa", user.idEmpresa);
        Dashboard fragobj=new Dashboard();
        fragobj.setArguments(bundle);
        fm.beginTransaction().replace(R.id.contenedor, fragobj).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        FragmentManager fm=getSupportFragmentManager();
        Bundle bundle=new Bundle();
        bundle.putInt("idEmpresa", user.idEmpresa);
        int id = item.getItemId();
        fm.beginTransaction().replace(R.id.contenedor,new Configuracion()).commit();
        if (id == R.id.nav_ver_productos) {
            ListarProductos fragobj=new ListarProductos();
            fragobj.setArguments(bundle);
            fm.beginTransaction().replace(R.id.contenedor, fragobj).commit();
        } else if (id == R.id.nav_agregar_productos) {
            AgregarProductos fragobj=new AgregarProductos();
            fragobj.setArguments(bundle);
            fm.beginTransaction().replace(R.id.contenedor,fragobj).commit();
        } else if (id == R.id.nav_configuracion) {
            fm.beginTransaction().replace(R.id.contenedor,new Configuracion()).commit();
        }else if (id == R.id.nav_ver_ventas) {
            ListarVentas fragobj=new ListarVentas();
            fragobj.setArguments(bundle);
            fm.beginTransaction().replace(R.id.contenedor, fragobj).commit();
        }else if (id == R.id.nav_compras) {
            Compras fragobj=new Compras();
            fragobj.setArguments(bundle);
            fm.beginTransaction().replace(R.id.contenedor, fragobj).commit();
        } else if (id == R.id.nav_ventas) {
            Ventas fragobj=new Ventas();
            fragobj.setArguments(bundle);
            fm.beginTransaction().replace(R.id.contenedor,fragobj).commit();
        } else if (id == R.id.nav_usuarios) {
            fm.beginTransaction().replace(R.id.contenedor,new Usuarios()).commit();
        } else if (id == R.id.nav_dashboard) {
            fm.beginTransaction().replace(R.id.contenedor,new Dashboard()).commit();
        } else if (id == R.id.nav_salir) {

            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}


