package contracts;

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should return person by id=4"

    request {
        url "/person/4"
        method GET()
    }

    response {
        status OK()
        headers {
            contentType applicationJson()
        }
        body(
                id: 4,
                name: "Jack",
                surname: "Black"
        )
    }
}