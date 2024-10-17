import React, { useState } from 'react';
import './EvaluateRule.css'; // Import the CSS file for styling

const EvaluateRule = () => {
  const [age, setAge] = useState('');
  const [department, setDepartment] = useState('');
  const [salary, setSalary] = useState('');
  const [experience, setExperience] = useState('');
  const [result, setResult] = useState(null); // To store the evaluation result
  const [error, setError] = useState(null); // To handle any errors

  const handleSubmit = async () => {
    // Validation checks
    if (!age || !department || !salary || !experience) {
      setError('All fields are required');
      return;
    }

    setError(null); // Clear any previous error

    try {
      // API call to evaluate rule
      const response = await fetch("http://localhost:8080/rule_engine/evaluate_rule", {
        method: "POST", // Use POST for sending data
        headers: {
          "Content-Type": "application/json", // Send as JSON
        },
        body: JSON.stringify({
          age: Number(age), // Convert to Number for the API
          department, // Keep as string
          salary: Number(salary), // Convert to Number for the API
          experience: Number(experience), // Convert to Number for the API
        }),
      });

      if (!response.ok) {
        throw new Error('Failed to evaluate rule');
      }

      const data = await response.json(); // Assuming API returns JSON
      setResult(data); // Save the evaluation result
      
      // Resetting fields after submission
      setAge('');
      setDepartment('');
      setSalary('');
      setExperience('');
      
    } catch (err) {
      setError(err.message);
      setResult(null);
    }
  };

  return (
    <div className="create-rule-container">
      <h2>Evaluate Rule</h2>
      <input
        type="number"
        value={age}
        onChange={(e) => setAge(e.target.value)}
        placeholder="Enter age"
        className="rule-input"
      />
      <input
        type="text"
        value={department}
        onChange={(e) => setDepartment(e.target.value)}
        placeholder="Enter department"
        className="rule-input"
      />
      <input
        type="number"
        value={salary}
        onChange={(e) => setSalary(e.target.value)}
        placeholder="Enter salary"
        className="rule-input"
      />
      <input
        type="number"
        value={experience}
        onChange={(e) => setExperience(e.target.value)}
        placeholder="Enter experience"
        className="rule-input"
      />
      <button onClick={handleSubmit} className="submit-button">
        Evaluate Rule
      </button>

      {error && <p className="error-message">{error}</p>}

      {/* Display the evaluation result */}
      {result !== null && (
        <div className="result-display">
          <h3>Evaluation Result:</h3>
          <p>{result ? "User is valid." : "User is not valid."}</p>
        </div>
      )}
    </div>
  );
};

export default EvaluateRule;
