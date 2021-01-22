/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import entities.Pedidos;
import java.util.Comparator;

/**
 *
 * @author Andrei
 */
public class Comparators {
 
    public static final Comparator<Pedidos> PEDIDOSID = (Pedidos o2, Pedidos o1) -> Integer.compare(o1.getIdpedidos(), o2.getIdpedidos());

}
