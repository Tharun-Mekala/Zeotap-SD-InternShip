import React, { useState } from "react";
import './App.css';
import CreateRuleInput from "./Components/CreateRuleInput/CreateRuleInput"; 
import CombineRule from "./Components/CombineRule/CombineRule"; 
import EvaluateRule from "./Components/EvaluateRule/EvaluateRule"; 

const App = () => {
  const [activeComponent, setActiveComponent] = useState(null); 

  const renderComponent = () => {
    switch (activeComponent) {
      case 'create':
        return <CreateRuleInput />;
      case 'combine':
        return <CombineRule />;
      case 'evaluate':
        return <EvaluateRule />;
    }
  };

  return (
    <div className="container">
      <h1>Rule Engine with AST</h1>
      <div className="button-group">
        <button onClick={() => setActiveComponent('create')}>Create Rule</button>
        <button onClick={() => setActiveComponent('combine')}>Combine Rule</button>
        <button onClick={() => setActiveComponent('evaluate')}>Evaluate Rule</button>
      </div>

      <div className="component-view">
        {renderComponent()}
      </div>
    </div>
  );
};

export default App;
