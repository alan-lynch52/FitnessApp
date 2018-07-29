/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fitnessapp;

import java.util.LinkedList;

/**
 *
 * @author Alan
 */
public class MyList<E> {
    private LinkedList<E> list;
    MyList(){}
    public void add(E o){
        list.add(o);
    }
    public void remove(E o){
        list.remove(o);
    }
    public boolean contains(E o){
        return list.contains(o);
    }
}
