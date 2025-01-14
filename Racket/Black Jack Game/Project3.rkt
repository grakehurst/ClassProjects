#lang Racket
; Gavin Akehurst
; Project 3: Black Jack
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; The Rules:
; The player and the dealer are both given two cards
; The player goes first
; The player can hit and recieve another card, or stand and stay where they are
; The dealer will then react to the users' cards
; If the user goes over 21 (busts), the dealer wins
; If the user does not bust, the dealer will hit until he wins or busts
; The amount bet will be doubled if the user wins
; The amount bet will be taken away if the user loses

; User interface:
; The user interface is completely in the DrRacket terminal
; I used display statements to output cards and menu options
; The user can input menu options to make decisions including
; playing a new hand, the amount to bet, and stand or hit
; The menu will not allow you to input an incorrect value
; or bet more money than you have
; There are planety of display statements to help the user undertand what is happening

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; Get suit symbols from output
; (newline)
; (display "\u2663 ") ; clubs
; (display "\u2666 ") ; diamonds
; (display "\u2665 ") ; hearts
; (display "\u2660 ") ; spades
; ♣ ♦ ♥ ♠

; GLOBALS
; card faces
(define faces '(2 3 4 5 6 7 8 9 10 J Q K A))
(define suits '(♣ ♦ ♥ ♠))

; shuffled deck of cards
(define shuffled-deck 0)


; function to create a card with a number and suit
(define make-card
  (lambda (face suit)
    ;(display face)(displayln suit)
    (cons face suit)
    ))


; function to create a deck of 52 cards
(define (make-deck)

  ;(displayln deck)
  ; empty list to hold the deck
  (define deck (list ))
  
  ; loop through the suits and make a card with each face
  (letrec [(deck-loop (lambda (face-loop suit-loop)
                        ;(display "in loop")
                        (cond
                          ; if no more suits
                          [(empty? suit-loop) deck]
                          ; if no more faces, pass in faces and rest of suits
                          [(empty? face-loop)(deck-loop faces (cdr suit-loop))]

                          ; otherwise match the first suit with first face
                          ; and pass the rest of the faces
                          (else (and
                                 (set! deck (append deck (list (make-card (car face-loop) (car suit-loop))))))
                                (deck-loop (cdr face-loop) suit-loop)
                          )  
            )))]
    ; call the loop with the global variables
    (deck-loop faces suits)

  ))


; function to evaluate a hand
; can be any number of cards
; Ace is either 1 or 11
(define eval-hand
  (lambda (hand)
    ; keep track of the number of aces and score
    (define numaces 0)
    (define score 0)

    (letrec [(evaluate (lambda (hand)
                         ;(display "hand: ")(displayln hand)(newline)
                         (cond
                           ; check for empty list
                           [(empty? hand) 0]
                           [(empty? (car hand)) 0]
      
                           ; either Ace, number, or face cards
                           [(integer? (caar hand))(+ (caar hand)(evaluate (cdr hand)))]

                           [(equal?   (caar hand) 'A)
                                      (and(set! numaces (+ numaces 1))
                                          (+ 11 (evaluate (cdr hand)))
                                          )]
                           
                           [else      (+ (evaluate (cdr hand)) 10)]
                           )
                         
    ))]
      (set! score (evaluate hand)))

    ; check if the score is over 21 and there are aces
    (if (and (> score 21)(> numaces 0))
        (- score 10)
        (+ score 0))
    ))
      
    


; deal a hand of two cards from the top of a deck
; the deck loses those two cards
(define deal!
  (lambda (deck)

    ; list to hold the hand
    (define hand 0)
    (set! hand (list (car deck)(cadr deck)))

    ; remove those cards from the deck
    (set! shuffled-deck (cddr shuffled-deck))

    ; return if not empty
    (cond
      [(empty? hand)0]
      [else hand]
      )))


; function to add a card to a hand
; passed in the deck to deal from and the hand to deal to
(define hit!
  (lambda (deck hand)
    (set! hand (append hand (list(car deck))))

    ; take that card out of the deck
    (set! shuffled-deck (cdr shuffled-deck))
    
    ; return if not empty
    (cond
      [(empty? hand)0]
      [else hand]
      )
    ))


; function to display a hand
; if it is the dealer, it will only show one card
(define show-hand
  (lambda (hand how message)
    ;display the message
    (display message)
    
    (cond
      ; empty list, stop displaying
      [(empty? hand)(display "")]
      
      ; if only part of the hand is shown, display the first and pass again with Full
      [(equal? how 'Part)(and(display "*****")(show-hand (cdr hand) 'Full ""))]

      ; otherwise display the next card and loop again
      [else (and(display (car hand))(show-hand (cdr hand) 'Full ""))]

      )
    ))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(newline)


(define driver
  (lambda (balance)
    ;main project loop

    ; create a new shuffled deck
    (define thedeck (make-deck))
    (set! shuffled-deck (shuffle thedeck))

    ; display balance
    (newline)
    (display "Balance: $")(displayln balance)(newline)

    ; see if the user wants to keep playing
    (displayln "MENU: ")
    (displayln "1. New Hand")
    (displayln "2. Quit")
    (define answer (read-line (current-input-port) 'any))

    ; if something other than 1 or 2 is input, restart
    (if (not(or (equal? answer "1")(equal? answer "2")))
        (and (displayln "Not a valid menu option.")(driver balance))
        (display ""))

    ; if the user says quit, do nothing else
    ; otherwise... let the game begin!
    (cond
      [(equal? answer "2")
        (displayln "Quitting...")]
        [ else (letrec [(gamestart (lambda (balance)
                              ; get how much the user wants to bet
                              (display "Bet amount: ")
                              (define bet (string->number(read-line (current-input-port) 'any)))(newline)
                              (if (> bet balance)
                                  (and(displayln "Insufficient balance.")(gamestart balance))
                                  (display ""))

                              ; get initial user and dealer hands 
                              (define playerhand (deal! shuffled-deck))
                              (show-hand playerhand 'Full "You have: ")(newline)
                              (display " Value: (")(display (eval-hand playerhand))(display ")")(newline)(newline)
                              (define dealerhand (deal! shuffled-deck)) 
                              (show-hand dealerhand 'Part "The Dealer has: ")(newline)

                              ; user actions (hit or stand)
                              (set! playerhand (useraction playerhand 0))

                              ; get the user's new score
                              (define userscore (eval-hand playerhand))

                              ; decide what the dealer should do
                              (set! dealerhand (dealeraction dealerhand userscore))

                              ; compare to see who wins
                              ; 1 if user wins, 0 if dealer wins, 2 if tie
                              (define dealerscore (eval-hand dealerhand))
                              (define winner 0) ; default dealer wins
                              (cond
                                [(> userscore 21)(set! winner 4)]
                                [(> dealerscore 21)(set! winner 3)]
                                [(= userscore dealerscore)(set! winner 2)] ; tie
                                [(> userscore dealerscore)(set! winner 1)] ; user wins
                                )

                              ; display the dealer's hand
                              (newline)
                              (displayln "Dealer's Turn:")
                              (show-hand dealerhand 'Full "The dealer has: ")
                              (newline)

                              ;display winner message
                              (cond
                                [(= winner 4)(displayln "BUST! DEALER WINS!")]
                                [(= winner 3)(displayln "DEALER BUST! YOU WIN!")]
                                [(= winner 2)(displayln "PUSH!")]
                                [(= winner 1)(displayln "YOU WIN!")]
                                [(= winner 0)(displayln "DEALER WINS!")]
                                
                                )

                              ; change balance based on winner
                              ; loss = lose money bet
                              ; win  = win money bet
                              ; tie = no change
                              (cond
                                [(= winner 4)(set! balance (- balance bet))]
                                [(= winner 3)(set! balance (+ balance bet))]
                                [(= winner 1)(set! balance (+ balance bet))]
                                [(= winner 0)(set! balance (- balance bet))]
                                )
                              (newline)(newline)

                              ; restart with updated balance
                              (driver balance) 
                         ))
                 
                 ; function to loop through the user action menu
                 ; can hit multiple times or stand
                 (useraction (lambda (playerhand repeat)
                               ; player decides what to do if not busted or at 21
                               (define answer 0)

                               ; if you need to redisplay the cards
                               (if (= repeat 1)
                                   (and (show-hand playerhand 'Full "You have: ")
                                        (and(display " Value: (")(and(display (eval-hand playerhand))(displayln ")"))))
                                   (display ""))

                               (newline)
                               
                               (displayln "What will you do?")
                               (displayln "1. Hit")
                               (displayln "2. Stand")
                               (set! answer (string->number(read-line (current-input-port))))
                               (cond
                                 ; if the user stands, return the hand
                                 [(eq? answer 2) playerhand]

                                 ; if the user wants to hit but is at/over 21
                                 [(and(> (eval-hand playerhand) 21) (eq? answer 1))
                                  (and(displayln "Value is too high for another hit.")
                                      (useraction playerhand 1))]

                                 ; if they hit, add a card and run again
                                 [(eq? answer 1)
                                  (useraction (hit! shuffled-deck playerhand) 1)]

                                 ; otherwise it is the wrong input
                                 [ else (and (displayln "Invalid input.")(useraction playerhand 1))]
                                 )

                               ))
                               

                 ; function for the dealer's actions
                 ; basically keeps hitting until win or lose unless opponent busted
                 (dealeraction (lambda (dealerhand userscore)
                                 ; main loop
                                 (cond
                                   ; if user busted, dont do anything
                                   [(> userscore 21) dealerhand]

                                   ; if dealer score is over 21, return
                                   [(> (eval-hand dealerhand) 21) dealerhand]

                                   ; otherwise hit as long as your score is lower than user
                                   [(> (eval-hand dealerhand) userscore) dealerhand]
                                   [ else (dealeraction (hit! shuffled-deck dealerhand) userscore)]

                                   )
                                 


                                 ))]
          ; check if balance is > 0
          (if (> balance 0)
              (gamestart balance)
              (display "Insufficient balance."))
          ;(game balance)
          )])
    ))

(define (main)
  ; starter output
(displayln "Welcome to Black Jack: ")
(driver 500) ; starter balance == 500
  )
(main)

  




; Debugging 
;; ; creating a deck and shuffling
;; (define thedeck (make-deck))
;; (define shuffled-deck (shuffle thedeck))
;; ; display the shuffled deck
;; ;(displayln shuffled-deck)(newline)
;; 
;; ; creating a 3 card hand list and evaluating
;; (define hand 0)
;; (set! hand (list (car shuffled-deck)(cadr shuffled-deck) (caddr shuffled-deck)))
;; (display "hand: ")
;; (display hand)(newline)
;; (eval-hand hand)
;; 
;; ; used the shuffled deck to deal a hand and hit
;; (define playerhand (deal! shuffled-deck))
;; (display "before hit: ")
;; (display playerhand)
;; (eval-hand playerhand)
;; 
;; (display "after hit: ")
;; (set! playerhand (hit! shuffled-deck playerhand))(display playerhand)
;; (eval-hand playerhand)(newline)(newline)
;; 
;; ; show the hand with the function
;; (display playerhand)(newline)(newline)
;; (display (cddr playerhand))(newline)(newline)
;; (show-hand playerhand 'Full)



