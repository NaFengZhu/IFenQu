package com.ifenqu.app;

import java.util.Collections;
import java.util.LinkedList;

public class Test {

    public static void main(String[]args){
        testLink();
    }

    private static void testLink(){
        LinkedList linkedList = new LinkedList();
        for (int i = 0; i < 5; i++) {
//            if (i == 4){
//                linkedList.offerFirst(i);
//            }else {
//                linkedList.offer(i);
//            }
            linkedList.offer(i);
        }

        for (int i = 5; i < 7; i++) {
            linkedList.offer(i);
        }

        Collections.reverse(linkedList);
        for (int i = 0; i < linkedList.size(); i++) {
            System.out.print("link emelmet - "+linkedList.get(i));
        }
    }
}
