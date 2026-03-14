// Validate required parameters
if (!partyId || !firstName || !lastName) {
    ec.message.addError("partyId, firstName and lastName are required.")
    return
}

// Check if Party exists in YOUR component
def party = ec.entity.find("party.Party")
        .condition("partyId", partyId)
        .one()

if (!party) {
    ec.message.addError("Party with ID ${partyId} does not exist.")
    return
}

if (party.partyTypeEnumId != 'PERSON') {
    ec.message.addError("Party with ID ${partyId} must have partyTypeEnumId 'PERSON' (found '${party.partyTypeEnumId}').")
    return
}

// Create Person
def person = ec.entity.makeValue("party.Person")

person.partyId = partyId
person.firstName = firstName
person.lastName = lastName

if (dateOfBirth) {
    person.birthDate = dateOfBirth
}

// Save
person.create()

// Response
responseMessage = "Person ${firstName} ${lastName} created successfully!"