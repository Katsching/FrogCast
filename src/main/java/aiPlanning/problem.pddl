(define (problem changeposition)
    
    (:domain LEDPlanner)
    (:objects 
        bot  mid top -position
        white blue magenta lightBlue -color
    )

    (:init 
		(LEDStatus top)
		(colorStatus magenta)

        (LEDCanMove bot mid)
        (LEDCanMove mid top)
        (LEDCanMove mid bot)
        (LEDCanMove top mid)

        (colorCanChange white blue)
        (colorCanChange blue magenta)
        (colorCanChange magenta lightBlue)
        (colorCanChange lightBlue white)

		(colorCanChange lightBlue magenta)
        (colorCanChange magenta blue)
        (colorCanChange blue white)
        (colorCanChange white lightBlue)
    )

    (:goal (and 
		(LEDStatus top)
		(colorStatus magenta)
    )
    )
  
)
