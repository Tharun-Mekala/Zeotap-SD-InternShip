import React, { useState } from "react";

const CreateRuleInput = () => {
  const [ruleString, setRuleString] = useState("");
  const [node, setNode] = useState(null);   
  const [error, setError] = useState(null);   

  const handleInputChange = (e) => {
    setRuleString(e.target.value);
  };

  const handleSubmit = async () => {
    if (ruleString) {
      try {
        console.log("Submitting rule:", ruleString);
        const response = await fetch("http://localhost:8080/rule_engine/create_rule", {
          method: "POST",
          headers: {
            "Content-Type": "application/x-www-form-urlencoded",
          },
          body: new URLSearchParams({
            rule: ruleString,
          }),
        });

          
        if (!response.ok) {
          const errorMessage = await response.text();
          console.error("Error:", errorMessage);
          throw new Error(`Failed to create rule: ${response.status} - ${errorMessage}`);
        }

        const data = await response.json();   
        setNode(data);   
        setRuleString("");   
        setError(null);   
      } catch (err) {
        console.error("Error occurred:", err);
        setError(err.message);
        setNode(null);
      }
    }
  };

  return (
    <div className="create-rule-container">
      <h2>Create Rule</h2>
      <input
        type="text"
        value={ruleString}
        onChange={handleInputChange}
        placeholder="Enter rule string (e.g., age > 30 AND department = 'Sales')"
        className="rule-input"
      />
      <button onClick={handleSubmit} className="submit-button">
        Add Rule
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

export default CreateRuleInput;
