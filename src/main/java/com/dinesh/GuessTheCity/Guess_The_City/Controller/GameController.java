package com.dinesh.GuessTheCity.Guess_The_City.Controller;

import com.dinesh.GuessTheCity.Guess_The_City.Service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class GameController {

    @Autowired
    GameService gameService;
    String error, success, lost, empty="", attemptRamaining="Attempts remaining:";
    String randomWord;
    int attempt=5;
    @GetMapping("/home")
    public String showGameHomePage(Model model){

        randomWord=gameService.toString();
        model.addAttribute("wordToDisplay",randomWord);
        model.addAttribute("attempt",attempt);
        model.addAttribute("empty", attemptRamaining);
        return "game-page";
    }
    @GetMapping("/gameon")
    public String showGameHomePage(@RequestParam String guessChar, Model model) {

        if (attempt > 0) {
            if (guessChar.length() == 1 && Character.isAlphabetic(guessChar.charAt(0))) {
                char firstChar = guessChar.charAt(0);

                System.out.println("Guessed Char " + firstChar);
                attempt=gameService.check(firstChar,attempt);
                model.addAttribute("attempt",attempt);
            } else {
                error = "Wrong Input";
                System.out.println(error);
                model.addAttribute("choice", error);
                model.addAttribute("attempt",attempt);
            }

            randomWord = gameService.toString();

            model.addAttribute("wordToDisplay", randomWord);
            model.addAttribute("empty", attemptRamaining);
            if (!randomWord.contains("_")) {
                success = "You Won";
                model.addAttribute("empty", empty);
                model.addAttribute("attempt", empty);
                model.addAttribute("choice", success);
            }
            if(attempt==0){
                lost = "You lost! ";
                model.addAttribute("empty", empty);
                model.addAttribute("attempt", empty);
                model.addAttribute("wordToDisplay", randomWord);
                model.addAttribute("choice", lost);
            }

        }
        return "game-page";


    }
}
