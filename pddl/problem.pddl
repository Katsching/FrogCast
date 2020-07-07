(define (problem changeposition)
    
    (:domain LEDPlanner)
    (:objects 
        bot  mid top -position
        white blue magenta lightblue -color
    )

    (:init 
		(LEDStatus top)
		(colorStatus lightblue)

        (LEDCanMove bot mid)
        (LEDCanMove mid top)
        (LEDCanMove mid bot)
        (LEDCanMove top mid)

        (colorCanChange white blue)
        (colorCanChange blue magenta)
        (colorCanChange magenta lightblue)
        (colorCanChange lightblue white)

		(colorCanChange lightblue magenta)
        (colorCanChange magenta blue)
        (colorCanChange blue white)
        (colorCanChange white lightblue)
    )

    (:goal (and 
		(LEDStatus top)
		(colorStatus lightblue)
    )
    )
  
)
