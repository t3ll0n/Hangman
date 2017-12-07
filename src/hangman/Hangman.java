/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hangman;

import java.util.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.io.*;

/**
 *
 * @author T3L
 */
public class Hangman 
{
    //variables
    private List<String> wordList;
    private String currentWord;
    private String gameWord;
    private String answer;
    private int triesLeft;
    private int score;
    private int bestScore;
    private Random random;
    private int randomWordIndex;
    private boolean winner;
    
    
    //Purpose: 
    //Requires: 
    //Returns: 
    protected void initializeHangman(String fileName)
    {
        wordList = readWordList(fileName);
        random = new Random();
        randomWordIndex = random.nextInt((wordList.size() + 1)-0) + 0;
        currentWord = wordList.get(randomWordIndex);        
        initializeGameWord();
        initializeAnswer();
        triesLeft = 7;
        score = 0;
        winner = false;
    }
    
    //Purpose: 
    //Requires: 
    //Returns: 
    private List<String> readWordList(String fileName)
    { 
        List<String> words = Collections.emptyList();
        try
        {
          words = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
        }

        catch (IOException e)
        {
          //error message?
          e.printStackTrace();
        }
        return words;
    }
    
    private void initializeGameWord()
    {
        char[] tempWord = new char[(currentWord.length() * 2) - 1];
        int j = 0;
        
        for (int i = 0; i < currentWord.length(); i++)
        {
            tempWord[j] = '_';
            j++;
            if (i != (currentWord.length()-1))
                    {
                        tempWord[j] =' ';
                    }            
            j++;
        }
        gameWord = String.copyValueOf(tempWord);
    }
    
    private void initializeAnswer()
    {
        char[] tempWord = new char[(currentWord.length() * 2) - 1];
        int j = 0;
        
        for (int i = 0; i < currentWord.length(); i++)
        {
            tempWord[j] = currentWord.charAt(i);
            j++;
            if (i != (currentWord.length()-1))
                    {
                        tempWord[j] =' ';
                    }            
            j++;
        }
        answer = String.copyValueOf(tempWord);
    }
    
    protected void resetTriesLeft()
    {
        triesLeft = 7;
    }
    
    protected void decrementChancesLeft()
    {
        triesLeft--;
    }
    
    protected int getTriesLeft()
    {
        return triesLeft;
    }
    
    protected void updateScores()
    {
        if (winner)
        {
            score = (score + 1 + triesLeft);
        }
        else
        {
            score++;
        }
        
        if (score > bestScore)
        {
            bestScore = score;
        }
    }
    
    protected int getScore()
    {
        return score;
    }    
    
    protected int getBestScore()
    {
        return bestScore;
    }    
    
    protected String getNextWord()
    {
        randomWordIndex = random.nextInt((wordList.size() + 1)-0) + 0;
        currentWord = wordList.get(randomWordIndex);
        initializeGameWord();
        initializeAnswer();
        return gameWord;
    }
    
    protected boolean checkGuessLetter(char guessLetter)
    {
        return currentWord.indexOf(guessLetter) != -1;
    }
    
    protected boolean checkGuessWord(String guessWord)
    {
        return Objects.equals(guessWord, currentWord);
    }
    
    protected boolean checkGameWord()
    {
        return Objects.equals(gameWord, answer);
    }
    
    public void updateGameWord(char guessLetter)
    {
        char[] tempWord = gameWord.toCharArray(); 
        for (int i = 0; i < currentWord.length(); i++)
        {
            if (currentWord.charAt(i) == guessLetter)
            {
                tempWord[i*2] = guessLetter;
            }
        }
        gameWord = String.copyValueOf(tempWord);
    }
    
    protected String getGameWord()
    {
        return gameWord;
    }
    
    protected String getAnswer()
    {
        return answer;
    }
    
    protected void setWinner(boolean trueFalse)
    {
        winner = trueFalse;
    }
}