(define (domain LEDPlanner)
	
	(:requirements :strips :typing) 
	
	(:types
		color position -object
	)

	(:predicates
		(LEDStatus ?s - position)
		(LEDCanMove ?f -position ?t - position)
		(colorStatus ?c - color)
		(colorCanChange ?b - color ?e -color)
		
	)

	(:action moveState
		:parameters (?f ?t - position)
		:precondition (and 
			(LEDStatus ?f)
			(LEDCanMove ?f ?t)
		)
		:effect (and 
			(LEDStatus ?t)
			(not (LEDStatus ?f)) 
		)
	)

	(:action changeColor
		:parameters (?from ?to - color)
		:precondition (and
			(colorStatus ?from)
			(colorCanChange ?from ?to)
			
		)
		:effect (and
			(colorStatus ?to )
			(not (colorStatus ?from))
		)
	)



)	