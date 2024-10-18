import React, { useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faTrash } from '@fortawesome/free-solid-svg-icons';
 

const CombineRule = () => {
  const [rules, setRules] = useState(['']);   
  const [node, setNode] = useState(null);   
  const [error, setError] = useState(null);   

  const handleInputChange = (index, value) => {
    const newRules = [...rules];
    newRules[index] = value;   
    setRules(newRules);
  };

  const handleAddRule = () => {
    setRules([...rules, '']);   
  };

  const handleDeleteRule = (index) => {
    const newRules = rules.filter((_, i) => i !== index);   
    setRules(newRules);
  };

  const handleSubmit = async () => {
    const hasEmptyRule = rules.some(rule => rule.trim() === '');
    if (hasEmptyRule) {
      setError('Please fill in all rule fields.');
      return;
    }
    setError(null);   

    try {
      // API call to combine rules
      const response = await fetch("http://localhost:8080/rule_engine/combine_rules", {
        method: "POST",
        headers: {
          "Content-Type": "application/json", 
        },
        body: JSON.stringify(rules), 
      });

      if (!response.ok) {
        throw new Error('Failed to combine rules');
      }

      const data = await response.json();   
      setNode(data);   
      setRules([''])
      console.log('Combined Node:', data);   
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
