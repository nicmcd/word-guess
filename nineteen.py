import random
import five

DEBUG = True

# choose a random secret word
secret_index = random.randint(0, len(five.words))
secret_word = five.words[secret_index]
if DEBUG:
    print "DEBUG: secret word is " + secret_word

# show the game banner
print "   -------------------------------"
print "   - Welcome to Nineteen Guesses -"
print "   -------------------------------"
print ""

# instruct the user how to play
print "I have chosen a random 5 letter word and you have 19 guesses."
print "I'll give you hints along the way. Good Luck!"
print ""

# give the user 19 guesses in a loop
used_guesses = 0
allowed_guesses = 19
user_won = False
while (not user_won) and (used_guesses < allowed_guesses):

    # get the next guess from the user
    print "You have used " + str(used_guesses) + " of " + str(allowed_guesses) + " guesses"
    user_guess = raw_input("What is your next guess? ")

    # validate the user's input
    guess_valid = True
    if len(user_guess) != 5:
        print "*** You must enter a 5 letter word ***"
        guess_valid = False
    if not user_guess.isalpha():
        print "*** You may only enter alphabetical characters ***"
        guess_valid = False

    # if the guess is valid, run the game logic
    if guess_valid:

        # increment the guess counter
        used_guesses += 1

        # determine if the user guessed correctly
        if user_guess == secret_word:
            user_won = True

        # if the user guessed incorrectly, give them some hints
        else:

            # copy both strings so that we can modify them
            secret_list = list(secret_word)
            guess_list = list(user_guess)

            # find the number of correct letters in the right place
            # note: mark the matches as '-'
            matched_letters = 0
            for index in range(0,5):
                if secret_list[index] == guess_list[index]:
                    matched_letters += 1
                    secret_list[index] = '-'
                    guess_list[index] = '-'
                    if DEBUG:
                        print "DEBUG: match at index " + str(index)

            # give the user the hint
            print str(matched_letters) + " of your letters",
            if matched_letters == 1:
                print "is",
            else:
                print "are",
            print "correct and in the correct position."

            # find the number of correct letters that are misplaced
            mislocated_letters = 0
            for guess_index,guess_char in enumerate(guess_list):

                # skip already matched characters which are marked as '-'
                if guess_char != '-':

                    # loop over all characters in the secret
                    for secret_index,secret_char in enumerate(secret_list):

                        # skip already matched characters which are marked as '-'
                        if secret_char != '-':

                            # determine if guess_char is a misplaced match letter
                            if guess_char == secret_char:
                                mislocated_letters += 1
                                guess_list[guess_index] = '-'
                                secret_list[secret_index] = '-'
                                found_mislocated = True
                                if DEBUG:
                                    print "DEBUG: mislocation at secret[" + \
                                        str(secret_index) + "] and guess[" + \
                                        str(guess_index) +"]"
                                break;

            # give the user the hint
            print str(mislocated_letters) + " of your letters",
            if mislocated_letters == 1:
                print "is",
            else:
                print "are",
            print "correct but in the wrong position."

    # create an empty output line to separate each guess
    print ""


# inform the user that the game is over
if user_won:
    print "You won!!!"
    print "You guessed the word '" + secret_word + "' in " + str(used_guesses) + " guesses"
    print "Great Job!"
else:
    print "You lost :("
    print "You have used all of your guesses"
    print "The secret word was '" + secret_word + "'"

print ""
