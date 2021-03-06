def extract_words(string):
   l = []
   word = ''
   for c in string+' ':
       if c.isalpha():
           word += c
       else:
           if word != '':
               l.append(word.lower())
           word = ''
   return l
def count_words(filename):
   words = {}
   lines = ' '.join(open(filename).readlines())
   for w in extract_words(lines):
       count = words.get(w, 0)
       words[w] = count + 1
   return words
def report_distribution(count):
   wordCounts = []
   total = 0
   for w, c in count.items():
       total += c
       wordCounts.append((c, w))
   s = 'count word' + '\n'
   wordCounts = sorted(wordCounts, reverse=True)
   s += '%5d\n' % total
   for c, w in wordCounts[:20]:
       s += '%5d %s\n' % (c, w)
   return s
def main():
   filename = input('filename?\n')
   print(report_distribution(count_words(filename)))

main()

