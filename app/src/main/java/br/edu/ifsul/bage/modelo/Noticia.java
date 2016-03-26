package br.edu.ifsul.bage.modelo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by emilio on 10/03/16.
 */
public class Noticia implements Serializable{

    private int id;
    private Date data;
    private String hora;
    private String noticia;

    public Noticia(){
        this.data = new Date();
        this.hora = new SimpleDateFormat("hh:mm").format(this.data);
    }

    public Noticia(String noticia) {
        this();
        this.noticia = noticia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getNoticia() {
        return this.noticia;
    }

    public void setNoticia(String noticia) {
        this.noticia = noticia;
    }

    @Override
    public String toString() {
        return new SimpleDateFormat("dd/MM/yyyy").format(this.data) + " - " + this.hora + "\n" + this.noticia;
    }
}
