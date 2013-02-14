(ns N-Grams)

;; Takes some txt files and returns a sequence of all of the words
; Known Issue: For some reason it includes an empty string ("") at the end of every line
; This effects the tri-grams because some of them will look like this: ["Hello" "" "Dave"]
(defn get-word-seq [files] (clojure.string/split (apply str (interpose " " (map #(slurp %) files))) #"\s"))

(defn get-tri-grams [words] 
	(loop [toReturn [], wordsLeft words]
   (if (= (count wordsLeft) 3)
     (conj toReturn (conj [] (first wordsLeft) (second wordsLeft) (nth wordsLeft 2)))
     (recur (conj toReturn (conj [] (first wordsLeft) (second wordsLeft) (nth wordsLeft 2))) (rest wordsLeft))
     )
   )  
  )

;; Takes some text files and returns a map of the number of occurrences of each tri-gram
(defn tri-grams-from-files [files]
  (frequencies (get-tri-grams (get-word-seq files)))
  )