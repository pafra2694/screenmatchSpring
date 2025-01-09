package com.aluracursos.screenmatch.models;

public enum Categoria {
    ACCION("Action"),
    ROMANCE("Romance"),
    COMEDIA("Comedy"),
    DRAMA("Drama"),
    CRIMEN("Crime");

    private String categoriaOmdb;

    Categoria (String categoriaOmdb){
        this.categoriaOmdb = categoriaOmdb;
    }

    //Metodo que convertira de un string a un tipo de dato Categoria
    public static Categoria fromString(String text){
        for (Categoria categoria: Categoria.values()) {
            if(categoria.categoriaOmdb.equalsIgnoreCase(text)){
                return categoria;
            }
        }
        throw new IllegalArgumentException("Ninguna categoria encontrada: " + text);
    }
}
