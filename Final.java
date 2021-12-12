//import os

import validators

import requests as req

# DELIMITERS is used to split the string based on the spacial characters it contains.

# Update DELIMITERS as per your needs

DELIMITERS = ['.',',','\t','\n']

def remove_word(data, word):

    # this list is used to store the different representations of the word

    representations = []

    for d in data:

        if d.lower() == word:

            if d not in representations:

                representations.append(d)

            data.remove(d)

    return data, representations

def word_stats(data: str):

    # Replace all delimiters with space

    for char in DELIMITERS:

        data = data.replace(char,' ')

    # coverting the string into list of words by splitting the string

    words = data.split()

    words_copy = data.lower()

    total_words = len(words)

    while len(words) != 0:

        word = words[0].lower()

        word_count = words_copy.count(word)

        words, representations = remove_word(words, word)

        print("{0}:{1}%, {2} total occurrences, {3} representations {4}".format(word,(word_count/total_words)*100,word_count, len(representations), str(representations)))

if __name__ == "__main__":

    file_name = input()

    data = None

    if validators.url(file_name):

        data = req.get(file_name).text

    else:

        data = open(file_name, mode = 'r').read()

    

    word_stats(data)


        }
    }
}
