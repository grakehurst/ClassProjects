# Gavin Akehurst
# Comparative Languages 
# Project 6: Hangman Game
# 28 November 2024


# imports
import sys
import os
import random

# globals
allWords = [] # ordered list of all possible words


# driver function
def main():
       # get all the words from the file
       getWords()

       # starter output
       print("Welcome to Hangman")
       print()

       # main program loop
       keepPlaying = 1
       while keepPlaying:
            print("What word length (3 to n) would you like to play?")
            print()
            maxLength = 0

            while maxLength < 3:
                    # check for invalid input: must be > 2 and an integer
                    try:
                            maxLength = int(input("n = "))
                            if maxLength < 3:
                                print("Please enter an integer greater than 2.")
                    except ValueError:
                            print("Please enter an integer.")
                            maxLength = 0
                    
            gameLoop(maxLength)

            # ask if they will keep playing
            # input validation for a yes or no
            done = 0
            while done == 0:
                answer = input("Would you like to play again? (Yes/No): ")
                print()
                if answer != "Yes" and answer != "No" and answer != "yes" and answer != "no":
                        print("Please answer \"Yes\" or \"No\".")
                else:
                        done = 1

            # keep looping if the answer was yes, display a goodbye if no
            if answer == "No" or answer == "no":
                   keepPlaying = 0
                   print("Thanks for playing!")
                   print()

# end main


# function to fill allWords global
# should run at the beginning of the program
def getWords():

    # check that the file exists
    # if not, exit
    if not os.path.exists("wordlist.txt"):
           print("Error: File does not Exist")
           sys.exit(1)

    # get the items from the word list file and put them into the allWords global
    with open("wordlist.txt", 'r') as file:
            for line in file:
                    # remove the newline characters and add it to the array
                    line = line.replace("\n", "")
                    allWords.append(line)
# end getWords


# function that is one interation of the hangman game
# returns 0 if guessed incorrectly, 1 if correct?
def gameLoop(maxLength):

       # get the word to be used
       # get the array of all possible words based on length
       possibleWords = [] 
       for i in allWords:
              if len(i) <= maxLength:
                     possibleWords.append(i)

       # randomly grab one of those words
       word = random.choice(possibleWords)
       #print("word: ", word)

       # turn it into an array for easier access
       wordArray = list(word)
       numGuesses = (len(wordArray) * 2) - 1

       # array to display the word during guessing, starts as all stars 
       guessArray = [] 

       # display the word covered in stars
       print("\nWord: ", end = " ")
       count = 0
       while count < len(word):
              print("*", end = " ")
              guessArray.append("*")
              count += 1
       print(" ")

       # guessing loop
       done = 0 # 0 if not done, 1 if correct, 2 if incorrect

       while done == 0:
              # take in a guess
              print("You have ", numGuesses, " guesses remaining.")
              guess = input("Type a letter or word guess: ")

              # 1 character is a letter guess, 3+ is a word guess, 2 is invalid
              while len(guess) == 2:
                      guess = input("All words are more than two letters. \nPlease enter a letter or word guess: ")
                
              # if the length of the guess is greater than two, check if its right  
              if len(guess) > 2:
                      if guess == word:
                             done = 1
                      else:
                             print("Sorry the word is not '", guess, "'.")
              else:
                       # otherwise, look for the guessed letter in the word
                       if guess in wordArray:
                              # get the number of instances for that letter
                              rightLetters = 0
                              count = 0
                              for i in wordArray:
                                     if i.lower() == guess.lower():
                                            rightLetters += 1
                                            guessArray[count] = i
                                     count += 1
                              if rightLetters == 1:
                                     print("There is", rightLetters, guess,"!" )
                              else:
                                     print("There are", rightLetters, guess,"'s!" )
                       # if no instances of the guess         
                       else: 
                              print("Sorry, there are no", guess, "'s.")
       
              # either way, reduce the number of guesses and update output
              numGuesses -= 1

              # check if all letters are guessed
              starCount = 0
              for i in guessArray:
                     if i == "*":
                            starCount+=1
              if starCount == 0:
                     done = 1  

              # congratulations if you win
              if done == 1:
                     print("\nCongratulations, you guessed it!") 
              else:
                     print("\n")
                     # display the updated guess array
                     for i in guessArray:
                            print(i, end = " ")
                     print(" ")   
              # if no more guesses, set done to 2 meaning loss
              if numGuesses == 0 and done != 1:
                     print("Sorry, you are out of guesses.")
                     print("The word was",word)
                     print()
                     done = 2
      # end main loop
# end game loop
                     


if __name__ == '__main__':
    main()

