package com.cszczotka.java16;

import  java.util.Calendar;

public class RecordDemo {

    record Person(int id, String name) {}

    //Canonical constructor demo
    record Invoice(String id, float amount) {
        static String prefix = String.valueOf(Calendar.getInstance().get(Calendar.YEAR))
                +String.valueOf(Calendar.getInstance().get(Calendar.MONTH)+1);

        public Invoice(String id, float amount){
            this.id=prefix+id.trim();
            this.amount=amount;
        }

        // non canonical constructor demo
        public Invoice(String id){
            this(id,0.0f);
        }
    }

    //Canonical compact constructor demo
    record Invoice2(String id, float amount) {
        static String prefix = String.valueOf(Calendar.getInstance().get(Calendar.YEAR))
                +String.valueOf(Calendar.getInstance().get(Calendar.MONTH)+1);
        public Invoice2 {
            id=prefix+id.trim();
            amount=amount;
        }
    }

    public static void main( String[] args )
    {
        Person p1 = new Person(1,"Peter Parker");
        Person p2 = new Person(2,"Spiderman");

        System.out.println(p1.toString());
        System.out.println(p1.equals(p2));
        System.out.println(p1.name());
    }
}
