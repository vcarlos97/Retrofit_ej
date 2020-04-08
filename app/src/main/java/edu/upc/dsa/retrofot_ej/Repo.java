package edu.upc.dsa.retrofot_ej;
public class Repo {
    int id;
    String name;
    String full_name;

    @Override
    public String toString() {
        return id+""+name+""+full_name;

    }
}