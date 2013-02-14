(ns Parallel-SAT-Evaluation)

(use 'clojure.test)

;; fillValues replaces the values in the expression with true or false, based on the map values
(defn fillValues [expression values]
  (let [fill (fn [keyword] (get values keyword))]
    (pmap #(map fill %) expression)
    )
  )

;; evaluate takes a SAT expression and a map of values and returns True if the values satisfy the expression, False otherwise
;; I use nil? in this function because (some true? [false false]) returns nil. So nil acts the same as false in the later evaluation
(defn evaluate [expression values]
  (if (some nil? (pmap #(some true? %) (fillValues expression values)))
    false
    true
    )   
  )

(defn check-assignment-list [expression mapOfValues]
  (pmap #(evaluate expression %) mapOfValues)
  )

;; Tests
(is (= [[true] [false true]] (fillValues [ [:p] [:q :r] ] {:p true :q false :r true})))

(is (= true (evaluate [ [:p] [:q :r] ] {:p true :q false :r true})))
(is (= false (evaluate [ [:p] [:q :r] ] {:p true :q false :r false})))

(is (= [true false false] (check-assignment-list [ [:p] [:q :r] ] [{:p true, :q false, :r true},
                                                                   {:p true, :q false, :r false},
                                                                   {:p false, :q true, :r true}])))