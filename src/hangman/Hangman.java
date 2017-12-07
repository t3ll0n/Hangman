//Authors: Johann Redhead and Tellon Smith
//Hangman Game Object

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
    
    
    //Purpose: start the hangman game
    //Requires: the text file containing the words
    //Returns: none
    protected void initializeHangman(String fileName)
    {
        wordList = readWordList(fileName); //adds the words from the textfile to a list
        random = new Random();
        randomWordIndex = random.nextInt((wordList.size() + 1)-0) + 0; //gets a random word from the list on every new game
        currentWord = wordList.get(randomWordIndex);        
        initializeGameWord();
        initializeAnswer();
        triesLeft = 7;
        score = 0;
        winner = false;
    }
    
    //Purpose: gets all the words from the text file
    //Requires: textfile containng the words
    //Returns: a list containing all the words from the textfile
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
    
    //Purpose: creates a char array containing the word to be guessed with spaces between each character
    //Requires: none
    //Returns: none
    private void initializeGameWord()
    {
        char[] tempWord = new char[(currentWord.length() * 2) - 1]; //creates a char array to hold the number of dashes+spaces to make the word to be guessed
        int j = 0;
        
        for (int i = 0; i < currentWord.length(); i++)
        {
            tempWord[j] = '_'; //add an underscore for the character
            j++;
            if (i != (currentWord.length()-1)) //if not at the end of a word, put a space
                    {
                        tempWord[j] =' ';
                    }            
            j++;
        }
        gameWord = String.copyValueOf(tempWord); //covert char array back to a string 
    }
    
    //Purpose: creates a string that is the word to be guessed
    //Requires: none
    //Returns: none
    private void initializeAnswer()
    {
        char[] tempWord = new char[(currentWord.length() * 2) - 1]; //creates a char array to hold the number of dashes+spaces to make the word to be guessed
        int j = 0;
        
        for (int i = 0; i < currentWord.length(); i++)
        {
            tempWord[j] = currentWord.charAt(i); //add the character at this index to the array
            j++;
            if (i != (currentWord.length()-1)) //if not at the end of the word, add a space between each character
                    {
                        tempWord[j] =' ';
                    }            
            j++;
        }
        answer = String.copyValueOf(tempWord); ////covert char array back to a string
    }
    
    //Purpose: resets the number of tries a user has, starting value is 7
    //Returns: none
    //Requires: none
    protected void resetTriesLeft()
    {
        triesLeft = 7;
    }
    
    //Purpose: decrements the number of tries a user has, called at every incorrect guess
    //Returns: none
    //Requires: none
    protected void decrementChancesLeft()
    {
        triesLeft--;
    }
    
    //Purpose: returns the number of tries a user currently has
    //Returns: none
    //Requires: none
    protected int getTriesLeft()
    {
        return triesLeft;
    }
    
    //Purpose: updates the current score and user best score
    //Returns: none
    //Requires: none
    protected void updateScores()
    {
        if (winner)
        {
            score = (score + 1 + triesLeft); //if word is guessed correctly, increase score + add number of tries available
        }
        else
        {
            score++; //hasn't won yet, so just increase his score, score increases by 1 for each correct guess
        }
        
        if (score > bestScore)
        {
            bestScore = score; //updates user's current best score
        }
    }
    
    //Purpose: returns user's current score in the game
    //Returns: the current score
    //Requires: none
    protected int getScore()
    {
        return score;
    }    
    
    //Purpose: returns user's bestscore in the game session, resets to 0 on program close
    //Returns: the best score
    //Requires: none
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
    
    //Purpose: checks to see if the letter user selected is contained in the word to be guessed
    //Returns: -1 if the letter is not in the word, or returns the index of the letter in the string if it is 
    //Requires: none
    protected boolean checkGuessLetter(char guessLetter)
    {
        return currentWord.indexOf(guessLetter) != -1;
    }
    
    //Purpose: checks to see if the word user selected is contained in the word to be guessed
    //Returns: false if the word does not match, or returns true if  the word matches
    //Requires: none
    protected boolean checkGuessWord(String guessWord)
    {
        return Objects.equals(guessWord, currentWord); //compares the 2 strings for equality
    }
    
    //Purpose: checks to see if the completed word the user entered matches the word to be guessed
    //Returns: false if the word does not match, or returns true if  the word matches
    //Requires: none
    protected boolean checkGameWord()
    {
        return Objects.equals(gameWord, answer);
    }
    
    //Purpose: updates the user guessed string with the correct letter they selected
    //Returns: none
    //Requires: none
    public void updateGameWord(char guessLetter)
    {
        char[] tempWord = gameWord.toCharArray(); //convert string array to char and assign it to tempword
        for (int i = 0; i < currentWord.length(); i++)
        {
            if (currentWord.charAt(i) == guessLetter)
            {
                tempWord[i*2] = guessLetter;
            }
        }
        gameWord = String.copyValueOf(tempWord);
    }
    
    //Purpose: returns the user guessed word
    //Returns: a string, the word user guessed so far 
    //Requires: none
    protected String getGameWord()
    {
        //i.e if looking for bob, gameWord would be equal to the guesses the got correct so far, like 'bo'
        return gameWord;
    }
    
    //Purpose: returns the word the user is trying to match
    //Returns: a string
    //Requires: none
    protected String getAnswer()
    {
        return answer;
    }
    
    //Purpose: checks to see if user won the game (if they guessed the word correctly)
    //Returns: none
    //Requires: none
    protected void setWinner(boolean trueFalse)
    {
        winner = trueFalse;
    }
}