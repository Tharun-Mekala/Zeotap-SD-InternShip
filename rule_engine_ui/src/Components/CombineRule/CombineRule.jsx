import React, { useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faTrash } from '@fortawesome/free-solid-svg-icons';
import './CombineRule.css'; // Import the CSS file

const CombineRule = () => {
  const [rules, setRules] = useState(['']); // Initialize with one empty rule
  const [node, setNode] = useState(null); // To store the returned Node
  const [error, setError] = useState(null); // To handle any errors

  const handleInputChange = (index, value) => {
    const newRules = [...rules];
    newRules[index] = value; // Update the specific rule input
    setRules(newRules);
  };

  const handleAddRule = () => {
    setRules([...rules, '']); // Add a new empty rule input
  };

  const handleDeleteRule = (index) => {
    const newRules = rules.filter((_, i) => i !== index); // Remove the specific rule
    setRules(newRules);
  };

  const handleSubmit = async () => {
    const hasEmptyRule = rules.some(rule => rule.trim() === '');
    if (hasEmptyRule) {
      setError('Please fill in all rule fields.');
      return;
    }
    setError(null); // Clear any previous error

    try {
      // API call to combine rules
      const response = await fetch("http://localhost:8080/rule_engine/combine_rules", {
        method: "POST",
        headers: {
          "Content-Type": "application/json", // Send as JSON
        },
        body: JSON.stringify(rules), // Convert rules to JSON
      });

      if (!response.ok) {
        throw new Error('Failed to combine rules');
      }

      const data = await response.json(); // Assuming API returns JSON
      setNode(data); // Save the returned Node
      setRules([''])
      console.log('Combined Node:', data); // Print the returned Node in the console
    } catch (err) {
      setError(err.message);
      setNode(null);
    }
  };

  return (
    <div className="create-rule-container">
      <h2>Combine Rules</h2>
      {rules.map((rule, index) => (
        <div key={index} className="rule-input-container">
          <input
            type="text"
            value={rule}
            onChange={(e) => handleInputChange(index, e.target.value)}
            placeholder={`Enter rule string ${index + 1} (e.g., age > 30)`}
            className="rule-input"
          />
          {/* Delete Icon */}
          <button onClick={() => handleDeleteRule(index)} className="delete-button" aria-label="Delete rule">
            <FontAwesomeIcon icon={faTrash} style={{ color: '#d9534f' }} /> {/* Red color for the delete icon */}
          </button>
        </div>
      ))}
      <button onClick={handleAddRule} className="submit-button" style={{ marginRight: '10px' }}>
        Add Another Rule
      </button>
      <button onClick={handleSubmit} className="submit-button">
        Combine Rules
      </button>

      {error && <p className="error-message">{error}</p>}

      {/* Display the returned Node */}
      {node && (
        <div className="node-display">
          <h3>Returned Node:</h3>
          <pre>{JSON.stringify(node, null, 2)}</pre>
        </div>
      )}
    </div>
  );
};

export default CombineRule;
