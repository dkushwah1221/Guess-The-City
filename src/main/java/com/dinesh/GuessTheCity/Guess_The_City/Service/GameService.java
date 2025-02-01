package com.dinesh.GuessTheCity.Guess_The_City.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Random;

@Service
public class GameService {
    private static final Logger log = LoggerFactory.getLogger(GameService.class);

    public String randomChoosenWord = null;
    private List<String> city;
    private char[] allCharacters;
    Random random= new Random();
    private final JdbcClient jdbcClient;



    public GameService(JdbcClient jdbcClient){
        this.jdbcClient = jdbcClient;

        city = jdbcClient.sql("SELECT city FROM GussTheCity").query(String.class).list();
        System.out.println("Cities: "+city);
        randomChoosenWord=city.get(random.nextInt(city.size()));
        randomChoosenWord=randomChoosenWord.toLowerCase();
        System.out.println("Random Word "+randomChoosenWord);
        allCharacters=new char[randomChoosenWord.length()];

    }

   public int check(char ch,int attempt){

       int i = randomChoosenWord.indexOf(ch);

       if(i>=0) attempt++;

       while(i >= 0) {
           System.out.println(i);
           Array.setChar(allCharacters, i, ch);
           System.out.println(allCharacters);
           i = randomChoosenWord.indexOf(ch, i+1);
           System.out.println(i);
       }
       attempt--;
       return attempt;

   }

    @Override
    public String toString() {
        String res="";
        for(char c:allCharacters)
        {
            if(c=='\u0000')
            {
                res=res+"_";
            }
            else res = res + c;
            res=res+" ";
        }

        return res;
    }

}
