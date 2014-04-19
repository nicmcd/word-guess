import java.util.*;
import java.io.*;


/**
 * This class is a game in which it selects a word from a txt file
 * then allows the user to have 19 guesses to guess the word.
 * It will show which letters are properly placed and which
 * letters are correct but in the wrong place.
 *
 * @author Nic McDonald
 * @version March 05, 2007
 * @TA Will
 */
public class NineteenGame
{
  public static void main(String[] args)
  {
    //before game startes read in file, decide if all words are properly
    //typed, and selected one random word from the list.
    int wordCount = 0;
    String secretWord = "";
    boolean wordsValid = true;
    try
    {
      //scan in file
      File wordsfile = new File("five.txt");
      Scanner fileScan = new Scanner(wordsfile);
      Scanner fileScan2 = new Scanner(wordsfile);
      //decide if each word is valid
      String word = "";
      while (fileScan.hasNext())
      {
        word = fileScan.next();
        if (!(word.length() == 5))
          wordsValid = false;
        int index = 0;
        //check each character of each word for lower case, symbols, and numbers
        while (index < word.length())
        {
          if (!(Character.isLowerCase(word.charAt(index))))
            wordsValid = false;
          index++;
        }
        wordCount++;
      }
      //print out error message if any words were incorrect in file
      if (wordsValid == false)
      {
        System.out.println("**Program loading problems occurred. Contact software developer.**");
        System.out.println("**System problems occured while picking a random word.**");
        return;
      }
      //get random word from list in five.txt
      int count = 0;
      int randomNumber = (int)(Math.random() * wordCount);
      while (count < randomNumber)
      {
        fileScan2.next();
        count++;
      }
      secretWord = fileScan2.next();
      /**
       * secretWord = "radar"; Set word for test
       */
      fileScan.close();
      fileScan2.close();
    }
    //catch error if read in failed
    catch (IOException readInError)
    {
      System.out.println("**Program loading problems occurred. Contact software developer.**");
      System.out.println("**System problems occured while scanning in file.**");
      return;
    }
    /**
     * System.out.println("word count: " + wordCount);//test word count print
     *
     */
    //System.out.println(secretWord); //test print out of word

    //Start the game
    //Print welcome and instructions
    System.out.println("");
    System.out.println("     ~~Welcome to Nineteen Guesses~~");
    System.out.println("     _______________________________");
    System.out.println("\nINSTRUCTIONS: ");
    System.out.println("I have selected a random five letter word and \nyou have 19 guesses to try and guess it.");
    System.out.println("After each guess I'll give you some hints to help you.");
    System.out.println("Do NOT attempt to use copy/paste in answer area.");
    System.out.println("Type 'exit' in any guess area to exit the game.");
    System.out.println("GOOD LUCK!!!");

    Scanner inputScan = new Scanner(System.in);
    int guessCount = 0;
    boolean guessOK = true;
    //start guessing loop
    while (guessCount < 19)
    {
      guessCount++;
      do
      {
        System.out.print("\nEnter guess # " + guessCount + ": ");
        String guess = inputScan.next().toLowerCase();
        int index = 0;
        //exit if user types 'exit'
        if (guess.trim().equals("exit"))
        {
          System.out.println("Thank you for playing.");
          return;
        }
        //check each character for symbols or numbers
        while (index < guess.length())
        {
          if (!(Character.isLowerCase(guess.charAt(index))))
          {
            System.out.println("Your guess cannot contain any numbers or symbols!");
            guessOK = false;
            index = guess.length();
          }
          else
          {
            guessOK = true;
          }
          index++;

        }
        //check length for 5 chars
        if (guess.length() != 5)
        {
          System.out.println("Must must enter a five letter word.");
          guessOK = false;
        }


        //do everything for guess, now that it is deemed proper
        if (guessOK == true)
        {
          ///check if the two words match up
          if (guess.equals(secretWord))
          {
            String guessStr = "";
            if (guessCount == 1)
              guessStr = "guess";
            else
              guessStr = "guesses";
            System.out.println("You Win! You guessed the word in " + guessCount + " " + guessStr + ".");
            return;
          }
          //do these statements if not perfect match
          else
          {
            //tell user that their guess isn't correct
            System.out.println("Your guess is incorrect: " + guess);
            String XGuess = guess;
            String XSecretWord = secretWord;
            //find cross matching letters
            index = 0;
            int correctLetters = 0;
            while (index < XGuess.length())
            {
              if (XGuess.charAt(index) == XSecretWord.charAt(index))
              {
                correctLetters++;
                XGuess = XGuess.substring(0, index) + XGuess.substring(index + 1);
                XSecretWord = XSecretWord.substring(0, index) + XSecretWord.substring(index + 1);
              }
              else
              {
                index++;
              }
            }
            //use if/else for proper grammer of is/are
            String isOrAre = "";
            if (correctLetters == 1)
              isOrAre = "is";
            else
              isOrAre = "are";
            System.out.println(correctLetters + " of your letters " + isOrAre + " correct and in the correct position.");
            /**
             * System.out.println("XSecretWord: " + XSecretWord); //test to print XSecretWord
             * System.out.println("XGuess: " + XGuess); //test to print XGuess
             */

            //find misplaced letters
            index = 0;
            int aIndex = 0;
            int misplacedLetters = 0;
            while (aIndex < XSecretWord.length())
            {
              while (index < XGuess.length())
              {
                if (XSecretWord.charAt(aIndex) == XGuess.charAt(index))
                {
                  misplacedLetters++;
                  XSecretWord = XSecretWord.substring(0, aIndex) + XSecretWord.substring(aIndex + 1);
                  XGuess = XGuess.substring(0, index) + XGuess.substring(index + 1);
                  aIndex = 0;
                  index = 0;
                }
                else
                  index++;

              }
              index = 0;
              aIndex++;
            }
            //use if/else for proper is/are grammer
            if (misplacedLetters == 1)
              isOrAre = "is";
            else
              isOrAre = "are";
            System.out.println(misplacedLetters + " of your letters " + isOrAre + " correct but misplaced.");

          }
        }


      }while (guessOK == false);
    }
    System.out.println("You lose! Your 19 guesses are up. The word is: " + secretWord + ".");
  }
}
