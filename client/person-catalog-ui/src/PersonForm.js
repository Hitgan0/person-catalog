import { useEffect, useState } from "react";
import { useNavigate, useParams, Link } from "react-router-dom";

const PERSON_DEFAULT = {
    firstName:"",
    lastName:"",
    dob:"",
    email:"",
    phone:""
};

function PersonForm() {
    // State Variables
    const [person, setPerson] = useState(PERSON_DEFAULT);
    const [errors, setErrors] = useState([]);

    // Hook for navigation
    const navigate = useNavigate();

    // Hook to access URL params
    const { personId } = useParams();

    const url = "http://localhost:8080/api/person";

    useEffect(() => {
        if (personId) {
            const fetchPerson = async () => {
                try {
                    const res = await fetch(`${url}/${personId}`)

                    if (res.status === 200) {
                        const data = await res.json();

                        setPerson(data);
                    } else {
                        console.error("Failed to fetch person", res.status);
                    }
                } catch (error) {
                    console.error(error);
                }
            };
            fetchPerson();
        } else {
            setPerson(PERSON_DEFAULT);
        }
    }, [personId])

    // Function to handle form submission
    const handleSubmit = (event) => {
        event.preventDefault();

        if (personId) { // Id ID exists, we're updating
            updatePerson();
        } else { // Otherwise, we're adding
            addPerson();
        }
    };

    // Function to handle input changes in the form
    const handleChange = (event) => {
        // Create a copy of the current solarPanel state
        const newPerson = { ...person };

        newPerson[event.target.name] = event.target.value;
        
        // Update state with the modified copy
        setPerson(newPerson);
    };

    const addPerson = () => {
        const init = {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(person),
        };

        fetch(url, init)
        .then((response) => {
            if (response.status === 201 || response.status === 400) {
            return response.json();
            } else {
            return Promise.reject(`Unexpected Status Code: ${response.status}`);
            }
        })
        .then((data) => {
            if (data.personId) {
            // Happy path
            navigate('/');
            } else { // unhappy path
            setErrors(data);
            }
        })
        .catch(console.log);
    };

    const updatePerson = () => {
        // Assign the ID
        person.personId = personId;
        const init = {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(person),
        };

        fetch(`${url}/${personId}`, init)
        .then((response) => {
            if (response.status === 204) {
            return null;
            } else if (response.status === 400) {
            return response.json();
            } else {
            Promise.reject(`Unexpected status code: ${response.status}`);
            }
        })
        .then((data) => {
            if (!data) { // Success - navigate to home page
            navigate('/');
            } else {
            // Get the error messages and display them
            setErrors(data);
            }
        })
        .catch(console.log);
    };

    return (
        <>
            <section className="container">
                <h2 className="mb-4">
                {personId > 0 ? "Update Person" : "Add Person"}
                </h2>
                {errors.length > 0 && (
                <div className="alert alert-danger">
                    <p>The following errors were found:</p>
                    <ul>
                    {errors.map((error) => (
                        <li key={error}>{error}</li>
                    ))}
                    </ul>
                </div>
                )}
                <form onSubmit={handleSubmit}>
                <fieldset className="form-group">
                    <label htmlFor="firstName">First Name</label>
                    <input
                    id="firstName"
                    name="firstName"
                    type="text"
                    className="form-control"
                    value={person.firstName}
                    onChange={handleChange}
                    />
                </fieldset>
                <fieldset className="form-group">
                    <label htmlFor="lastName">Last Name</label>
                    <input
                    id="lastName"
                    name="lastName"
                    type="text"
                    className="form-control"
                    value={person.lastName}
                    onChange={handleChange}
                    />
                </fieldset>
                <fieldset className="form-group">
                    <label htmlFor="dob">Date of Birth</label>
                    <input
                    id="dob"
                    name="dob"
                    type="date"
                    className="form-control"
                    value={person.dob}
                    onChange={handleChange}
                    />
                </fieldset>
                <fieldset className="form-group">
                    <label htmlFor="email">Email</label>
                    <input
                    id="email"
                    name="email"
                    type="text"
                    className="form-control"
                    value={person.email}
                    onChange={handleChange}
                    />
                </fieldset>
                <fieldset className="form-group">
                    <label htmlFor="phone">Phone</label>
                    <input
                    id="phone"
                    name="phone"
                    type="text"
                    className="form-control"
                    value={person.phone}
                    onChange={handleChange}
                    />
                </fieldset>
                <div className="mt-4">
                    <button type="submit" className="btn btn-outline-success mr-4 mt-4">
                    {personId > 0 ? "Update" : "Submit"}
                    </button>
                    <Link
                    type="button"
                    to={'/'}
                    className="btn btn-outline-danger mt-4"
                    >
                    Cancel
                    </Link>
                </div>
                </form>
            </section>
        </>
    );

}

export default PersonForm;