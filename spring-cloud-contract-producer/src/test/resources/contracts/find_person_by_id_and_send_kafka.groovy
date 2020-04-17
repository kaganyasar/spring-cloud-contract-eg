package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
	label("trigger")
	input {
		triggeredBy("trigger()")
	}
	outputMessage {
		sentTo("person")
		body(
				id: 4,
				name: "Jack",
				surname: "Black"
		)
	}
}