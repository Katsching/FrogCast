(define (problem changeposition)
    
    (:domain LEDPlanner)
    (:objects 
        bot mid top 
        white blue magenta
    )

    (:init 
        (LEDStatus mid)
        (colorStatus white)

        (LEDCanMove bot mid)
        (LEDCanMove mid top)
        (LEDCanMove mid bot)
        (LEDCanMove top mid)

        (colorCanChange white blue)
        (colorCanChange blue magenta)
        (colorCanChange magenta white)

        (colorCanChange magenta blue)
        (colorCanChange blue white)
        (colorCanChange white magenta)
    )

    (:goal (and 
        (LEDStatus top)
        (colorStatus magenta)
    )
    )
  
)