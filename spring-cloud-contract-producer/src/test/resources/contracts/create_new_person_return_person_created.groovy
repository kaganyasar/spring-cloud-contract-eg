package contracts;

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "create new person and return person created message in groovy model"

    request {
        url "/person/createNewPerson2"
        method POST()
        headers {
            contentType applicationJson()
        }
        body(id: 8,name: "ExampleUser2",surname: "ExampleUser2")
    }

    response {
        status 200
        headers {
            contentType applicationJson()
        }
        body(
                "Person Created"
        )
    }
}