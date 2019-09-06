package com.example.carlos.buscacerdos;

import android.R.layout;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.opengl.EGLExt;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayout.LayoutParams;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.GridLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.carlos.buscacerdos.R.drawable;
import com.example.carlos.buscacerdos.R.id;
import com.example.carlos.buscacerdos.R.menu;
import com.example.carlos.buscacerdos.R.string;

import java.util.Arrays;
import java.util.Random;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnLongClickListener {

    String dificultad = "Facil";
    android.support.v7.widget.GridLayout rejilla;
    ConstraintLayout main;
    MemoriaJuego tablero;
    int ganar;
    int icono;
    int botonID[][];
    String Coordenadas[];
    boolean tapada[][];
    Drawable icon;
    int[] iconos = {drawable.cerdicono, drawable.cerdicornio, drawable.cerdieuropeo, drawable.zombiecerdo};
    String[] nombres = {"Cerdito", "Cerdicornio", "Cerdieuropeo", "Zerdo"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        Toolbar toolbar = this.findViewById(id.toolbar);
        this.setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.tablero = new MemoriaJuego();
        this.rejilla = this.findViewById(id.rejilla);
        this.rejilla.removeAllViews();
        this.main = this.findViewById(id.main);
        this.main.removeAllViews();
        this.icono = drawable.cerdogrande;
        Toolbar tool = this.findViewById(id.toolbar);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(getApplicationContext(),drawable.cerdicono));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.Iniciar_juego) {
            if (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN_MR1) {
                this.iniciarJuego(this.dificultad);
            }
        }
        if (id == R.id.Configurar) {
            final String[] dificultades = {"Facil", "Amateur", "Dificil"};
            Builder builder = new Builder(this);
            builder.setTitle("NIVEL DE DIFICULTAD");
            builder.setSingleChoiceItems(dificultades, -1, new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MainActivity.this.dificultad = dificultades[which];
                }
            });
            builder.setPositiveButton("OK", new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        if (id == R.id.Instrucciones) {
            Builder builder = new Builder(this);
            builder.setMessage(string.dialog_message);
            builder.setTitle(string.dialog_title);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        if (id == R.id.Seleccion) {


            adaptadorPersonalizado arrayAdapter = new adaptadorPersonalizado(this, layout.simple_spinner_item, nombres);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setAdapter(arrayAdapter, new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    cambiarIcono(which);
                }
            });
            AlertDialog seleccion = builder.create();
            seleccion.show();
        }
        if (id == R.id.Salir) {
            System.exit(1);
        }

        return super.onOptionsItemSelected(item);
    }

    public class adaptadorPersonalizado extends ArrayAdapter<String> {

        public adaptadorPersonalizado(Context context, int resource, String[] objects) {
            super(context, resource, objects);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return crearFila(position, convertView, parent);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return crearFila(position, convertView, parent);
        }

        public View crearFila(int position, View convertView, ViewGroup parent) {
            View mivista;
            LayoutInflater inflador = getLayoutInflater();
            mivista = inflador.inflate(R.layout.listview, parent, false);
            TextView nombrePer = mivista.findViewById(id.nombrePer);
            nombrePer.setText(nombres[position]);
            ImageView personaje = mivista.findViewById(id.iconoPer);
            personaje.setImageResource(iconos[position]);
            return mivista;
        }
    }


    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    private void cambiarIcono(int nombre) {
        if (nombre == 0) this.icono = drawable.cerdicono;
        if (nombre == 1) this.icono = drawable.cerdicornio;
        if (nombre == 2) this.icono = drawable.cerdieuropeo;
        if (nombre == 3) this.icono = drawable.zombiecerdo;
        Toolbar toolbar = this.findViewById(id.toolbar);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(getApplicationContext(),icono));
    }


    public void onClick(View v) {
        ImageButton boton = (ImageButton) v;
        int id = boton.getId();
        this.descubrirCasilla(id);
        if (this.descubrirCasilla(id)) this.terminarPartida(1);
        this.ganador();
    }

    public void ganador() {
        if (this.ganar == 0) {
            this.terminarPartida(3);
        }
    }

    public boolean descubrirCasilla(int id) {
        ImageButton boton = this.findViewById(id);
        String[] cor = this.Coordenadas[id].split("-");
        int x = Integer.parseInt(cor[0]);
        int y = Integer.parseInt(cor[1]);
        int numero = this.tablero.casillas[x][y];
        if (this.tapada[x][y]) this.tapada[x][y] = false;
        switch (numero) {
            case -1:
                boton.setImageResource(this.icono);
                if (boton.isEnabled()) boton.setEnabled(false);
                return true;
            case 0:
                boton.setImageResource(drawable.cero);
                this.despejarCeros(boton);
                break;
            case 1:
                boton.setImageResource(drawable.uno);
                break;
            case 2:
                boton.setImageResource(drawable.dos);
                break;
            case 3:
                boton.setImageResource(drawable.tres);
                break;
            case 4:
                boton.setImageResource(drawable.cuatro);
                break;
            case 5:
                boton.setImageResource(drawable.cinco);
                break;
            case 6:
                boton.setImageResource(drawable.seis);
                break;
            case 7:
                boton.setImageResource(drawable.siete);
                break;
            case 8:
                boton.setImageResource(drawable.ocho);
                break;
        }
        if (boton.isEnabled()) boton.setEnabled(false);
        return false;
    }


    public void despejarCeros(ImageButton boton) {
        int id = boton.getId();
        String[] coor = this.Coordenadas[id].split("-");
        int x = Integer.parseInt(coor[0]);
        int y = Integer.parseInt(coor[1]);
        int tamaño = this.tablero.getTamaño();
        if ((x - 1 >= 0) && (y - 1 >= 0)  && this.tapada[x - 1][y - 1] == true) {
            this.tapada[x - 1][y - 1] = false;
            this.descubrirCasilla(this.botonID[x - 1][y - 1]);
        }
        if ((x - 1 >= 0)  && this.tapada[x - 1][y] == true) {
            this.tapada[x - 1][y] = false;
            this.descubrirCasilla(this.botonID[x - 1][y]);
        }
        if ((x - 1 >= 0) && (y + 1 < tamaño)  && this.tapada[x - 1][y + 1] == true) {
            this.tapada[x - 1][y + 1] = false;
            this.descubrirCasilla(this.botonID[x - 1][y + 1]);
        }
        if ((y - 1 >= 0) && this.tapada[x][y - 1] == true) {
            this.tapada[x][y - 1] = false;
            this.descubrirCasilla(this.botonID[x][y - 1]);
        }
        if ((y + 1 < tamaño) && this.tapada[x][y + 1] == true) {
            this.tapada[x][y + 1] = false;
            this.descubrirCasilla(this.botonID[x][y + 1]);
        }
        if ((x + 1 < tamaño) && (y - 1 >= 0)  && this.tapada[x + 1][y - 1] == true) {
            this.tapada[x + 1][y - 1] = false;
            this.descubrirCasilla(this.botonID[x + 1][y - 1]);
        }
        if ((x + 1 < tamaño) && this.tapada[x + 1][y] == true) {
            this.tapada[x + 1][y] = false;
            this.descubrirCasilla(this.botonID[x + 1][y]);
        }
        if ((x + 1 < tamaño) && (y + 1 < tamaño)  && this.tapada[x + 1][y + 1] == true) {
            this.tapada[x + 1][y + 1] = false;
            this.descubrirCasilla(this.botonID[x + 1][y + 1]);
        }
    }


    public void terminarPartida(int mensaje) {
        Builder builder = new Builder(this);
        builder.setTitle("PERDISTE");
        if (mensaje == 1) builder.setMessage(string.perderCerdo);
        if (mensaje == 2) builder.setMessage(string.perderNumero);
        if (mensaje == 3) {
            builder.setTitle("GANASTE");
            builder.setMessage(string.ganar);
        }
        AlertDialog alerta = builder.create();
        alerta.show();
        int tamaño = this.tablero.getTamaño();
        for (int fila = 0; fila < tamaño; fila++) {
            for (int columna = 0; columna < tamaño; columna++) {
                int id = this.botonID[fila][columna];
                ImageButton boton = this.findViewById(id);
                this.descubrirCasilla(id);
                if (boton.isEnabled()) boton.setEnabled(false);
            }
        }
    }

    public boolean onLongClick(View v) {
        ImageButton boton = (ImageButton) v;
        this.descubrirCasilla(boton.getId());
        String[] frase = this.Coordenadas[boton.getId()].split("-");
        int fila = Integer.parseInt(frase[0]);
        int columna = Integer.parseInt(frase[1]);
        int valor = this.tablero.casillas[fila][columna];
        if (this.tapada[fila][columna]) this.tapada[fila][columna] = false;
        if (this.descubrirCasilla(boton.getId()) == false) this.terminarPartida(2);
        if (valor == -1) this.ganar--;
        this.ganador();
        return false;
    }


    public void iniciarJuego(String dificultad) {
        int tamaño = 8, cerdosOcultos = 10;
        switch (dificultad) {
            case "Facil":
                tamaño = 8;
                cerdosOcultos = 10;
                this.tablero.setTamaño(tamaño);
                break;
            case "Amateur":
                tamaño = 12;
                cerdosOcultos = 30;
                this.tablero.setTamaño(tamaño);
                break;
            case "Dificil":
                tamaño = 16;
                cerdosOcultos = 60;
                this.tablero.setTamaño(tamaño);
                break;
        }
        this.tablero.setTamaño(tamaño);
        this.tapada = new boolean[tamaño][tamaño];
        this.ganar = cerdosOcultos;
        this.main.removeAllViews();
        this.rejilla.removeAllViews();
        this.rejilla.setColumnCount(tamaño);
        this.rejilla.setRowCount(tamaño);
        int identificador = 0;
        this.botonID = new int[tamaño][tamaño];
        this.Coordenadas = new String[tamaño * tamaño];
        for (int fila = 0; fila < tamaño; fila++) {
            for (int columna = 0; columna < tamaño; columna++) {
                ImageButton boton = new ImageButton(this);
                LayoutParams param = new LayoutParams();
                param.height = (this.main.getHeight()) / tamaño;
                param.width = (this.main.getWidth()) / tamaño;
                boton.setAdjustViewBounds(true);
                boton.setId(identificador);
                this.botonID[fila][columna] = boton.getId();
                this.Coordenadas[identificador] = "" + fila + "-" + columna;
                param.setGravity(Gravity.NO_GRAVITY);
                param.setGravity(77);
                param.setMargins(0,0,0,0);
                this.tapada[fila][columna] = true;
                boton.setPadding(0, 0, 0, 0);
                boton.setLayoutParams(param);
                boton.setOnClickListener(this);
                boton.setOnLongClickListener(this);
                this.rejilla.addView(boton);
                identificador++;
            }//for b
        }//for a
        this.main.addView(this.rejilla);
        this.tablero.nuevoTablero(tamaño);
        this.tablero.ponerCerditos(cerdosOcultos, tamaño);
        this.tablero.rellenarNumeros();
    }


}
