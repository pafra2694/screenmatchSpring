package com.aluracursos.screenmatch.models;

public enum Categoria {
    ACCION("Action","Acción"),
    ROMANCE("Romance", "Romance"),
    COMEDIA("Comedy" , "Comedia"),
    DRAMA("Drama", "Drama"),
    CRIMEN("Crime", "Crimen");

    private String categoriaOmdb;
    private String categoriaEspanol;

    Categoria (String categoriaOmdb, String categoriaEspanol){
        this.categoriaOmdb = categoriaOmdb;
        this.categoriaEspanol = categoriaEspanol;
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

    public static Categoria fromEsppanol(String text){
        for (Categoria categoria: Categoria.values()) {
            if(categoria.categoriaEspanol.equalsIgnoreCase(text)){
                return categoria;
            }
        }
        throw new IllegalArgumentException("Ninguna categoria encontrada: " + text);
    }
}
