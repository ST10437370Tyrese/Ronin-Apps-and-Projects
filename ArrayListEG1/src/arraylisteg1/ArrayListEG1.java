/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package arraylisteg1;

import java.util.ArrayList;


/**
 *
 * @author lab_services_student
 */
public class ArrayListEG1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Hellow World!");
        
        //Declarations
          ArrayList<String>myArrList = new ArrayList<>(); 
          
          
          
          myArrList.add("Lihle");
          myArrList.add("Tyrese");
          myArrList.add("Lira");
          
          myArrList.add(1, "John");
          
          myArrList.remove(3);
           myArrList.set(0, "Thato");
           
           //Display
           
           for(int i = 0; i < myArrList.size(); i++) {
               System.out.println(myArrList.get(i));
           }
           for(String item : myArrList){
               System.out.println(item);
           }  
           
           
    }
    
}
