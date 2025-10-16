import { useState, useEffect } from "react";
import { Link } from "react-router-dom";

function PersonList() {
    // State Variables
    const [persons, setPersons] = useState([]);
    const url = 'http://localhost:8080/api/person';

    useEffect(() => {
        const fetchPersons = async () => {
            try {
                const response = await fetch(url);

                if (!response.ok) {
                    throw new Error("Failed to fetch persons");
                }

                const data = await response.json();
                setPersons(data);
            } catch (error) {
                console.log("Error fetching persons: ", error);
            }
        };

        fetchPersons();
    }, [])

    const handleDeletePerson = (personId) => {
        const person = persons.find(p => p.personId === personId);
        if(window.confirm(`Delete Person: ${person.firstName} ${person.lastName}`)){
            const init = {
                method: 'DELETE'
            };
            fetch(`${url}/${personId}`, init)
                .then(response => {
                    if(response.status === 204){
                        // Create a copy of the array
                        // remove the person
                        const newPersons = persons.filter(p => p.personId !== personId);
                        // Update the persons state
                        setPersons(newPersons);
                    } else {
                        return Promise.reject(`Unexpected Status Code: ${response.status}`);
                    }
                })
                .catch(console.log);
        }
    };

    return (
        <>
            <section className="container">
                <h2 className="mb-4">Persons</h2>
                <Link
                    className="btn btn-outline-success mb-4 ml-2"
                    to={'/person/add'}
                >
                Add Person
                </Link>
                <table className="table table-striped table-hover">
                    <thead className="thead-dark">
                        <tr>
                            <th>Full Name</th>
                            <th>DOB</th>
                            <th>Email</th>
                            <th>Phone</th>
                            <th>&nbsp;</th>
                        </tr>
                    </thead>
                    <tbody>
                        {persons.map((person) => (
                        <tr key={person.personId}>
                            <td>{person.firstName} {person.lastName}</td>
                            <td>{person.dob !== null ? person.dob : "N/A"}</td>
                            <td>{person.email}</td>
                            <td>{person.phone}</td>
                            <td>
                                <button
                                    className="btn btn-outline-danger mr-4"
                                    onClick={() => handleDeletePerson(person.personId)}
                                >
                                    Delete
                                </button>
                                <Link
                                    className="btn btn-outline-warning mr-4"
                                    to={`/person/edit/${person.personId}`}
                                >
                                    Edit
                                </Link>
                            </td>
                        </tr>
                        ))}
                    </tbody>
                </table>
            </section>
        </>
    )
}

export default PersonList;