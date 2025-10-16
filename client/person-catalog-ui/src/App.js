import { Route, BrowserRouter as Router, Routes } from "react-router-dom";
import Home from "./Home";
import PersonForm from "./PersonForm";

function App() {
  return (
    <>
      <Router>
        <Routes>
          <Route path="/" element={<Home/>}/>
          <Route path="/person/add" element={<PersonForm/>}/>
          <Route path="/person/edit/:personId" element={<PersonForm/>}/>
        </Routes>
      </Router>
    </>
  )
  
}

export default App;
